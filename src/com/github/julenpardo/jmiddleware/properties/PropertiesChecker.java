package com.github.julenpardo.jmiddleware.properties;

public class PropertiesChecker {

  final public static int MINIMUM_PORT = 1025;
  final public static int MAXIMUM_PORT = 65535;
  final public static int MINIMUM_MULTICAST_FIRST_OCTECT = 224;
  final public static int MAXIMUM_MULTICAST_FIRST_OCTECT = 240;

  public static void checkMode(byte mode) throws InvalidPropertiesException {
    if (mode != 1) {
      throw new InvalidPropertiesException("Mode");
    }
  }

  public static void checkPort(int port) throws InvalidPropertiesException {
    boolean invalidPort;

    invalidPort = port < MINIMUM_PORT || port > MAXIMUM_PORT;

    if (invalidPort) {
      throw new InvalidPropertiesException("Port");
    }
  }

  public static void checkMulticastIp(String multicastIp) throws InvalidPropertiesException {
    boolean invalidMulticastIp;
    String[] octects;
    String octect;
    int octectNumber;

    octects = multicastIp.split(".");

    invalidMulticastIp = octects.length != 4;
    if (invalidMulticastIp) {
      throw new InvalidPropertiesException("Multicast IP");
    }

    for (int index = 0; index < octects.length; index++) {
      octect = octects[index];
      octectNumber = index + 1;

      invalidMulticastIp = invalidOctect(octect, octectNumber);

      if (invalidMulticastIp) {
        throw new InvalidPropertiesException("Multicast IP");
      }
    }
  }

  private static boolean invalidOctect(String octect, int octectNumber) {
    boolean invalidOctect = false;
    int octectInteger;

    octectInteger = Integer.parseInt(octect);

    if (octectNumber == 1) {
      invalidOctect = octectInteger < MINIMUM_MULTICAST_FIRST_OCTECT || octectInteger > MAXIMUM_MULTICAST_FIRST_OCTECT;
    } else {
      invalidOctect = octectInteger < 0 || octectInteger > 255;
    }

    return invalidOctect;
  }

}
