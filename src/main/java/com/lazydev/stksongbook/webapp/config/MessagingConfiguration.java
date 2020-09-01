package com.lazydev.stksongbook.webapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessagingConfiguration {

  @Value("${queue.mails-results}")
  private String resultsQueue;

  @Autowired
  private ConnectionFactory connectionFactory;

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(this.connectionFactory);
    factory.setMessageConverter(messageConverter());
    return factory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate() {
    final RabbitTemplate template = new RabbitTemplate(this.connectionFactory);
    template.setMessageConverter(messageConverter());
    return template;
  }

  @Bean
  public Queue mailResultsQueue() {
    return new Queue(resultsQueue);
  }
}
