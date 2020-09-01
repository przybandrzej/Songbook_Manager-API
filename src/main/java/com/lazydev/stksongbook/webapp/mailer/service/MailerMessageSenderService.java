package com.lazydev.stksongbook.webapp.mailer.service;

import com.lazydev.stksongbook.webapp.mailer.model.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailerMessageSenderService {

  private static final Logger log = LoggerFactory.getLogger(MailerMessageSenderService.class);

  private final RabbitTemplate rabbitTemplate;

  @Value("${queue.mails}")
  private String queueName;

  public MailerMessageSenderService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(EmailMessage emailMessage) {
    log.debug("Sending message... - {}", emailMessage);
    rabbitTemplate.convertAndSend(queueName, emailMessage);
  }
}
