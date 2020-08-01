package com.lazydev.stksongbook.webapp.util;

public class Constants {

  public Constants() { }

  public static final Long DEFAULT_ID = 0L;

  public static final Long CONST_SUPERUSER_ID = 1L;
  public static final Long CONST_ADMIN_ID = 2L;
  public static final Long CONST_MODERATOR_ID = 3L;
  public static final Long CONST_USER_ID = 4L;

  public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

  public static final String NAME_REGEX_SHORT = "^[a-zA-Z 0-9]{2,20}$";
  public static final String USERNAME_REGEX = "^[a-zA-Z0-9 _-]{4,15}$";
  public static final String EMAIL_REGEX = "^(([^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+(.[^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+)*)|(\".+\"))@(([^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]+.)+[^\\<\\>\\(\\)\\[\\]\\.,;:\\s@\"]{2,})$";
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,40}$";
  public static final String NAME_REGEX = "^[a-z A-Z]{2,15}";

  public static final String NAME_SHORT_INVALID_MESSAGE = "The name must consist of alphanumeric characters and be 2-20 characters long.";
  public static final String TITLE_INVALID_MESSAGE = "The title must consist of alphanumeric characters and be 2-20 characters long.";
  public static final String USERNAME_INVALID_MESSAGE = "The username can consist of alphanumeric characters, "
      + "spaces, underscores, dashes and be 3-15 characters long.";
  public static final String EMAIL_INVALID_MESSAGE = "Email invalid.";
  public static final String PASSWORD_INVALID_MESSAGE = "Password must consist of alphanumeric characters, "
      + "minimum one uppercase letter, one lowercase letter and one number. The length must be minimum 6 and maximum 20 characters.";
  public static final String NAME_INVALID_MESSAGE = "The name must consist of letters and spaces and be 2-15 characters long.";

  public static final String _Function_Music_Polish = "'muzyka'";
  public static final String _Function_Text_Polish = "'tekst'";
}
