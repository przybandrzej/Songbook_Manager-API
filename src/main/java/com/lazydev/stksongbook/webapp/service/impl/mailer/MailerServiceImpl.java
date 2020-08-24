package com.lazydev.stksongbook.webapp.service.impl.mailer;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.mailer.MailerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailerServiceImpl implements MailerService {



  @Override
  public void sendActivationMail(User user) {

  }

  @Override
  public void sendPasswordResetMail(User user) {

  }
}
