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
  private static final String SUPPORT_MAIL = "supportMail";

  @Value("${application.gui-base-url}")
  private String guiUrl;
  @Value("${application.support-mail}")
  private String supportMail;

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

  public void sendPasswordResetMail(User user) {
    log.debug("Sending password reset email to '{}'", user.getEmail());
    String htmlContent = getEmailTemplate(user, "mail/passwordResetEmail");
    String textContent = "Your reset key: " + user.getResetKey();
    String subject = "Account password reset";
    EmailMessage emailMessage = new EmailMessage();
    emailMessage.setTo(user.getEmail());
    emailMessage.setSubject(subject);
    emailMessage.setText(textContent);
    emailMessage.setHtml(htmlContent);
    sendEmail(emailMessage);
  }

  public void sendUserEmailChangedEmail(User user) {
    log.debug("Sending email has changed email to '{}'", user.getEmail());
    Context context = new Context(/*locale*/);
    context.setVariable(USER, user);
    context.setVariable(BASE_URL, guiUrl);
    context.setVariable(SUPPORT_MAIL, supportMail);
    String htmlContent = templateEngine.process("mail/emailChangedEmail", context);
    String textContent = "Your account's email has been changed.";
    String subject = "Account email changed";
    EmailMessage emailMessage = new EmailMessage();
    emailMessage.setTo(user.getEmail());
    emailMessage.setSubject(subject);
    emailMessage.setText(textContent);
    emailMessage.setHtml(htmlContent);
    sendEmail(emailMessage);
  }
}
