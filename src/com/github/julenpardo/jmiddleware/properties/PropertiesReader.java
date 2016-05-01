package com.github.julenpardo.jmiddleware.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Properties;

public class PropertiesReader {

  public final static String PROPERTIES_FILENAME = "config.properties";

  private Properties properties;
  private InputStream input;

  public PropertiesReader() throws FileNotFoundException {
    this.properties = new Properties();
    this.input = new FileInputStream(this.PROPERTIES_FILENAME);
  }

  /**
   * Reads the properties file, looking for the following properties:
   *  - Middleware mode.
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
    byte mode;
    int port;
    InetAddress multicastIp;

    this.properties.load(this.input);

    mode = Byte.parseByte(this.properties.getProperty("mode"));
    port = Integer.parseInt(this.properties.getProperty("port"));
    multicastIp = InetAddress.getByName(this.properties.getProperty("multicastIp"));

    PropertiesChecker.checkMode(mode);
    PropertiesChecker.checkPort(port);
    PropertiesChecker.checkMulticastIp(multicastIp.getHostName());

    configuration = new Configuration(mode, port, multicastIp);

    return configuration;
  }

}
