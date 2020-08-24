package com.lazydev.stksongbook.webapp.service.mailer;

import com.lazydev.stksongbook.webapp.data.model.User;

public interface MailerService {

  void sendActivationMail(User user);
  void sendPasswordResetMail(User user);
}
