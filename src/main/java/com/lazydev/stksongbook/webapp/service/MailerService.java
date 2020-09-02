package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.mailer.model.EmailMessage;
import com.lazydev.stksongbook.webapp.mailer.service.MailerMessageSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailerService {

  private static final Logger log = LoggerFactory.getLogger(MailerService.class);
  private static final String USER = "user";
  private static final String BASE_URL = "baseUrl";

  @Value("${application.gui-base-url}")
  private String guiUrl;

  private final MailerMessageSenderService sender;
  private final SpringTemplateEngine templateEngine;

  public MailerService(MailerMessageSenderService sender, SpringTemplateEngine templateEngine) {
    this.sender = sender;
    this.templateEngine = templateEngine;
  }

  public void sendActivationEmail(User user) {
    log.debug("Sending activation email to '{}'", user.getEmail());
    String htmlContent = getEmailTemplate(user, "mail/activationEmail");
    String textContent = "Your activation key: " + user.getActivationKey();
    String subject = "Account activation";
    EmailMessage emailMessage = new EmailMessage();
    emailMessage.setTo(user.getEmail());
    emailMessage.setSubject(subject);
    emailMessage.setText(textContent);
    emailMessage.setHtml(htmlContent);
    sendEmail(emailMessage);
  }

  public void sendEmail(EmailMessage emailMessage) {
    log.debug("Send email: {}", emailMessage);
    sender.sendMessage(emailMessage);
  }

  public String getEmailTemplate(User user, String templateName) {
    //Locale locale = Locale.forLanguageTag(user.getLangKey());
    Context context = new Context(/*locale*/);
    context.setVariable(USER, user);
    context.setVariable(BASE_URL, guiUrl);
    return templateEngine.process(templateName, context);
  }
}
