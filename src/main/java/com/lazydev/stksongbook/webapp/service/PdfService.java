package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class PdfService {

  private static final String IMAGE_RESOURCE_DIRECTORY = "img";
  private static final String RESOURCES_DIR = "src/main/resources";
  private static final String STK_LOGO_IMAGE = "stk_logo.png";
  private static final String FILE_AUTHOR_PREFIX = "STK AZS UEP";
  private static final String DOCUMENT_SUBJECT = "STK AZS UEP Songbook";
  private static final String DOCUMENT_APPLICATION = "STK Songbook";

  private static String FILE_PARSED = "{playlistId}_{playlistName}_{time}.pdf";

  private FileSystemStorageService fileSystemStorageService;
  private PDFont regularLato;
  private PDFont boldLato;
  private PDFont italicsLato;

  public PdfService(FileSystemStorageService service) {
    this.fileSystemStorageService = service;
  }

  public String createPdfFromPlaylist(Playlist playlist) {
    try (PDDocument document = new PDDocument()) {
      regularLato = PDType0Font.load(document, new File(RESOURCES_DIR + "/fonts/Lato2OFL/Lato-Regular.ttf"));
      boldLato = PDType0Font.load(document, new File(RESOURCES_DIR + "/fonts/Lato2OFL/Lato-Bold.ttf"));
      italicsLato = PDType0Font.load(document, new File(RESOURCES_DIR + "/fonts/Lato2OFL/Lato-Italic.ttf"));
      document.addPage(getTitlePage(document, playlist));
      document.addPage(getBlankPage());
      createSongsPages(document, playlist);
      setFileProperties(document, playlist);
      File file = getFileSave(playlist);
      document.save(file);
      return file.getName();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  protected File getFileSave(Playlist playlist) {
    return new File(fileSystemStorageService.getLocation().toAbsolutePath().toString(), FILE_PARSED
        .replace("{playlistId}", String.valueOf(playlist.getId()))
        .replace("{playlistName}", playlist.getName())
        .replace("{time}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))));
  }

  protected void setFileProperties(PDDocument document, Playlist playlist) {
    PDDocumentInformation info = document.getDocumentInformation();
    info.setAuthor(getFileAuthor(playlist));
    info.setCreationDate(getCreated());
    info.setCreator(FILE_AUTHOR_PREFIX);
    info.setProducer(DOCUMENT_APPLICATION);
    info.setSubject(DOCUMENT_SUBJECT);
    info.setTitle(getTitle(playlist));
    document.setDocumentInformation(info);
  }

  protected PDPage getBlankPage() {
    return new PDPage();
  }

  protected PDPage getTitlePage(PDDocument document, Playlist playlist) throws IOException {
    PDPage page = new PDPage();
    Path path = Paths.get(RESOURCES_DIR + "/" + IMAGE_RESOURCE_DIRECTORY + "/" + STK_LOGO_IMAGE);
    PDImageXObject image = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
    PDPageContentStream contentStream = new PDPageContentStream(document, page);

    float margin = 30f;
    PDRectangle mediaBox = page.getMediaBox();

    Point2D.Float imageStart = addCenteredImage(image, contentStream, page, new Point2D.Float(0, -margin), 0.75f);

    float fontSize = 25f;
    float authorFontSize = 10f;
    String authorString = "Author: " + getFileAuthor(playlist);

    contentStream.beginText();
    Point2D.Float titleOffset = new Point2D.Float(0, imageStart.y - getPageCenter(page).y - 3 * margin);
    Point2D.Float titlePoint = addCenteredText(getTitle(playlist), boldLato, (int) fontSize, contentStream, page, titleOffset);

    float authorOffsetX = mediaBox.getWidth() - titlePoint.x
        - (getStringWidth(authorString, regularLato, (int) authorFontSize) + margin/2);
    float authorOffsetY = margin - titlePoint.y;
    contentStream.setFont(regularLato, authorFontSize);
    contentStream.newLineAtOffset(authorOffsetX, authorOffsetY);
    contentStream.showText(authorString);
    contentStream.endText();
    contentStream.close();
    return page;
  }

  protected Point2D.Float addCenteredImage(PDImageXObject image, PDPageContentStream contentStream, PDPage page, Point2D.Float offset, float scale) throws IOException {
    PDRectangle mediaBox = page.getMediaBox();
    float scaledWidth = image.getWidth() * scale;
    float scaledHeight = image.getHeight() * scale;
    float imageStartX = ((mediaBox.getWidth() - scaledWidth) / 2) + offset.x;
    float imageStartY = mediaBox.getHeight() - scaledHeight + offset.y;
    contentStream.drawImage(image, imageStartX, imageStartY, scaledWidth, scaledHeight);
    return new Point2D.Float(imageStartX, imageStartY);
  }

  protected Point2D.Float addCenteredText(String text, PDFont font, int fontSize, PDPageContentStream content, PDPage page, Point2D.Float offset) throws IOException {
    content.setFont(font, fontSize);
    Point2D.Float pageCenter = getPageCenter(page);
    float stringWidth = getStringWidth(text, font, fontSize);
    float textX = pageCenter.x - stringWidth / 2F + offset.x;
    float textY = pageCenter.y + offset.y;
    content.setTextMatrix(Matrix.getTranslateInstance(textX, textY));
    content.showText(text);
    return new Point2D.Float(textX, textY);
  }

  protected float getStringWidth(String text, PDFont font, int fontSize) throws IOException {
    return font.getStringWidth(text) * fontSize / 1000F;
  }

  protected Point2D.Float getPageCenter(PDPage page) {
    PDRectangle pageSize = page.getMediaBox();
    return new Point2D.Float(pageSize.getWidth() / 2F, pageSize.getHeight() / 2F);
  }

  protected void createSongsPages(PDDocument document, Playlist playlist) {
    playlist.getSongs().forEach(song -> {
      PDPage page = new PDPage();
      document.addPage(page);
      try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

        PDRectangle mediabox = page.getMediaBox();
        float margin = 72;
        float width = mediabox.getWidth() - 2 * margin;
        float startX = mediabox.getLowerLeftX() + margin;
        float startY = mediabox.getUpperRightY() - margin;

        float titleFontSize = 25;
        float titleLeading = 1.5f * titleFontSize;
        float authorFontSize = 18;
        float authorLeading = 1.5f * authorFontSize;
        float textFontSize = 11;
        float textLeading = 1.5f * textFontSize;

        String[] separatedLines = song.getLyrics().split(System.lineSeparator());
        List<String> lines = new ArrayList<>();

        for (String text : separatedLines) {
          int lastSpace = -1;
          while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0) {
              spaceIndex = text.length();
            }
            String subString = text.substring(0, spaceIndex);
            float size = 0;

            size = textFontSize * regularLato.getStringWidth(subString) / 1000;

            if (size > width) {
              if (lastSpace < 0) {
                lastSpace = spaceIndex;
              }
              subString = text.substring(0, lastSpace);
              lines.add(subString);
              text = text.substring(lastSpace).trim();
              lastSpace = -1;
            } else if (spaceIndex == text.length()) {
              lines.add(text);
              text = "";
            } else {
              lastSpace = spaceIndex;
            }
          }
        }
        contentStream.beginText();

        contentStream.setFont(boldLato, titleFontSize);
        contentStream.newLineAtOffset(startX, startY);
        contentStream.showText(song.getTitle());
        contentStream.newLineAtOffset(0, -titleLeading);

        contentStream.setFont(italicsLato, authorFontSize);
        contentStream.showText(song.getAuthor().getName());
        contentStream.newLineAtOffset(0, -authorLeading);

        contentStream.setFont(regularLato, textFontSize);
        contentStream.newLineAtOffset(0, -textLeading);

        for (String line : lines) {
          contentStream.showText(line);
          contentStream.newLineAtOffset(0, -textLeading);
        }
        contentStream.endText();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  protected String getFileAuthor(Playlist playlist) {
    StringBuilder name = new StringBuilder(FILE_AUTHOR_PREFIX + " & " + playlist.getOwner().getUsername());
    if (playlist.getOwner().getFirstName() != null) {
      name.append(" [").append(playlist.getOwner().getFirstName());
    }
    if (playlist.getOwner().getLastName() != null) {
      name.append(" ").append(playlist.getOwner().getLastName()).append("]");
    }
    return name.toString();
  }

  protected Calendar getCreated() {
    var date = LocalDateTime.now();
    Calendar cal = new GregorianCalendar();
    cal.set(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), date.getHour(), date.getMinute(), date.getSecond());
    return cal;
  }

  protected String getTitle(Playlist playlist) {
    return playlist.getName();
  }
}
