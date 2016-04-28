package com.github.julenpardo.jmiddleware.properties;

import com.github.julenpardo.jmiddleware.Generator;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PropertiesReaderTest {

  private Generator generator;

  @Before
  public void setUp() {
    this.generator = new Generator();
  }

  @Test
  public void testReadProperties() {
    String filename = PropertiesReader.PROPERTIES_FILENAME;
    PropertiesReader propertiesReader;
    HashMap<String, String> properties = new HashMap<String, String>();
    Configuration configuration;

    try {
      byte expectedMode = 1;
      int expectedPort = 5000;
      InetAddress expectedMulticastIp = InetAddress.getByName("233.0.0.0");

      properties.put("mode", String.valueOf(expectedMode));
      properties.put("port", String.valueOf(expectedPort));
      properties.put("multicastIp", expectedMulticastIp.getHostName());

      generator.createPropertiesFile(filename, properties);
      propertiesReader = new PropertiesReader();
      configuration = propertiesReader.readProperties();

      byte actualMode = configuration.getMode();
      int actualPort = configuration.getPort();
      InetAddress actualMulticastIp = configuration.getMulticastIp();

      assertEquals(expectedMode, actualMode);
      assertEquals(expectedPort, actualPort);
      assertEquals(expectedMulticastIp.getHostName(), actualMulticastIp.getHostName());
    } catch (FileNotFoundException e) {
      fail("No exception should be thrown.");
    } catch (IOException e) {
      fail("No exception should be thrown.");
    }
  }

}
