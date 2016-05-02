package com.github.julenpardo.jmiddleware.properties;

/**
 * Created by julen on 28/04/16.
 */
public class InvalidPropertiesException extends Exception {

  public static final String DEFAULT_MESSAGE = "Invalid property defined for: ";

  public InvalidPropertiesException(String invalidProperty) {
    super(DEFAULT_MESSAGE + invalidProperty);
  }
}
