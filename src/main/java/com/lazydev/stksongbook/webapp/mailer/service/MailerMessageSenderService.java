package com.lazydev.stksongbook.webapp.mailer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.stksongbook.webapp.mailer.model.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Component
public class MailerMessageSenderService {

  private static final Logger log = LoggerFactory.getLogger(MailerMessageSenderService.class);

  private final RabbitTemplate rabbitTemplate;

  @Value("${queue.mails}")
  private String queueName;

  public MailerMessageSenderService(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage() throws JsonProcessingException {
    final EmailMessage message = new EmailMessage("Hello there!", new Random().nextInt(50), false);
    log.debug("Sending message...");
    rabbitTemplate.convertAndSend(queueName, new ObjectMapper().writeValueAsBytes(message));
  }
}
