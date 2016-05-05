package com.github.julenpardo.jmiddleware.socket;

public class NotSubscribedToTopicException extends Exception {

  public static final String DEFAULT_MESSAGE = "Trying to % a packet of topic you are not "
          + "subscribed to.";
  public static final String SENDING = DEFAULT_MESSAGE.replace("%", "send");
  public static final String RECEIVING = DEFAULT_MESSAGE.replace("%", "receive");

  public NotSubscribedToTopicException(String message) {
    super(message);
  }
}
