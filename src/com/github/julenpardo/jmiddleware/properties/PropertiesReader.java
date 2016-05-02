package com.github.julenpardo.jmiddleware.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;

public class PropertiesReader {

  public final static String PROPERTIES_FILENAME = "config.properties";
  public final static String PROPERTIES_USER_MODE = "userMode";
  public final static String PROPERTIES_SOCKET_MODE = "socketMode";
  public final static String PROPERTIES_PORT = "port";
  public final static String PROPERTIES_MULTICAST_IP = "multicastIp";

  private Properties properties;
  private InputStream input;

  public PropertiesReader() throws FileNotFoundException {
    this.properties = new Properties();
    this.input = new FileInputStream(this.PROPERTIES_FILENAME);
  }

  /**
   * Reads the properties file, looking for the following properties:
   *  - User mode (subscriber/publisher).
   *  - Socket mode (default only for the moment).
   *  - Port.
   *  - Multicast IP.
   *
   * Then, every property is checked to ensure that they are correctly defined. And, if yes,
   * a Configuration object that holds the read information is created and returned.
   *
   * @return Configuration The configuration object with the read properties.
   * @throws IOException If an exception occurs reading the properties file.
   * @throws InvalidPropertiesException If any of the properties is not correctly defined.
   */
  public Configuration readProperties() throws IOException, InvalidPropertiesException {
    Configuration configuration;
    byte userMode, socketMode;
    int port;
    InetAddress multicastIp;

    this.properties.load(this.input);

    userMode = Byte.parseByte(this.properties.getProperty(this.PROPERTIES_USER_MODE));
    socketMode = Byte.parseByte(this.properties.getProperty(this.PROPERTIES_SOCKET_MODE));
    port = Integer.parseInt(this.properties.getProperty(this.PROPERTIES_PORT));
    multicastIp = InetAddress.getByName(this.properties.getProperty(this.PROPERTIES_MULTICAST_IP));

    PropertiesChecker.checkUserMode(userMode);
    PropertiesChecker.checkPort(port);
    PropertiesChecker.checkMulticastIp(multicastIp.getHostName());

    configuration = new Configuration(userMode, socketMode, port, multicastIp);

    return configuration;
  }

}
