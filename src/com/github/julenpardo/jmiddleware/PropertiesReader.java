package com.github.julenpardo.jmiddleware;

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
  private byte mode;
  private int port;
  private InetAddress multicastIp;

  public PropertiesReader() throws FileNotFoundException {
    this.properties = new Properties();
    this.input = new FileInputStream(this.PROPERTIES_FILENAME);
  }

  public void readProperties() throws IOException {
    String mode, port, multicastIp;
    this.properties.load(this.input);

    mode = this.properties.getProperty("mode");
    port = this.properties.getProperty("port");
    multicastIp = this.properties.getProperty("multicastIp");

    this.mode = Byte.parseByte(mode);
    this.port = Integer.parseInt(port);
    this.multicastIp = InetAddress.getByName(multicastIp);
  }

}
