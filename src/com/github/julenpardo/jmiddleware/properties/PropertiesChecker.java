package com.github.julenpardo.jmiddleware.properties;

import java.util.regex.Pattern;

public class PropertiesChecker {

  final public static int MINIMUM_PORT = 1025;
  final public static int MAXIMUM_PORT = 65535;
  final public static int MINIMUM_MULTICAST_FIRST_OCTECT = 224;
  final public static int MAXIMUM_MULTICAST_FIRST_OCTECT = 239;

  /**
   * Checks the middleware mode provided in the properties file.
   * @param mode The mode read in the properties.
   * @throws InvalidPropertiesException If the mode of the properties does not match any valid modes.
   */
  public static void checkMode(byte mode) throws InvalidPropertiesException {
    if (mode != 1) {
      throw new InvalidPropertiesException("Mode");
    }
  }

  /**
   * Checks the port number provided in the properties file. To be considered as valid, the port
   * must be in the following range: [1025, 65535]; from the first port not defined by IANA, to the
   * maximum port available.
   * @param port The port read in the properties.
   * @throws InvalidPropertiesException If the port of the properties is not between the valid port range.
   */
  public static void checkPort(int port) throws InvalidPropertiesException {
    boolean invalidPort;

    invalidPort = port < MINIMUM_PORT || port > MAXIMUM_PORT;

    if (invalidPort) {
      throw new InvalidPropertiesException("Port");
    }
  }

  /**
   * Checks the multicast IP provided in the properties files. To be considered as valid, the IP
   * must be in the defined port range for Multicast: [224.0.0.0, 239.255.255.254].
   *
   * The first obvious step is to check that the provided IP has four octects, splitting the IP
   * by dot (must be escaped, because the "split" method takes it as regular expression pattern).
   *
   * Then, the most significant octet must be checked, because is the one with the smallest valid
   * range. The remaining three octects don't need any special check.
   *
   * @param multicastIp The IP read in the properties.
   * @throws InvalidPropertiesException If the IP of the properties is not a valid multicast IP.
   */
  public static void checkMulticastIp(String multicastIp) throws InvalidPropertiesException {
    boolean invalidMulticastIp;
    String[] octects;
    String octect;
    int octectNumber;

    octects = multicastIp.split(Pattern.quote("."));

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

  /**
   * Makes the check of the octects of the multicast IP read in the properties file. The first octect
   * needs different check since its range is smaller, but the remaining three do not need any special
   * check.
   *
   * @param octect The octect of the IP.
   * @param octectNumber The number of the octect [1, 4].
   * @return boolean True if the octect is invalid; false if it is not.
   */
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
