package com.lazydev.stksongbook.webapp.mailer.service;

import com.lazydev.stksongbook.webapp.mailer.model.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MailerMessageListenerService {

  private static final Logger log = LoggerFactory.getLogger(MailerMessageListenerService.class);

  @RabbitListener(queues = "#{mailResultsQueue.getName()}")
  public void receiveMessage(final Message message) {
    log.info("Received message as generic: {}", message);
  }

  @RabbitListener(queues = "#{mailResultsQueue.getName()}")
  public void receiveMessage(final EmailMessage customMessage) {
    log.info("Received message as specific class: {}", customMessage);
  }
}
