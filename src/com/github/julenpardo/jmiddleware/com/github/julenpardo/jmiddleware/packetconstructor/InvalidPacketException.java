package com.github.julenpardo.jmiddleware.com.github.julenpardo.jmiddleware.packetconstructor;

public class InvalidPacketException extends Exception {

  public static final String DEFAULT_MESSAGE = "Invalid packet provided";

  /**
   * InvalidPacketException constructor.
   */
  public InvalidPacketException() {
    super(DEFAULT_MESSAGE);
  }
}
