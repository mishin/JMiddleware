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

  public Configuration readProperties() throws IOException {
    Configuration configuration;
    byte mode;
    int port;
    InetAddress multicastIp;

    this.properties.load(this.input);

    mode = Byte.parseByte(this.properties.getProperty("mode"));
    port = Integer.parseInt(this.properties.getProperty("port"));
    multicastIp = InetAddress.getByName(this.properties.getProperty("multicastIp"));

    configuration = new Configuration(mode, port, multicastIp);

    return configuration;
  }

}
