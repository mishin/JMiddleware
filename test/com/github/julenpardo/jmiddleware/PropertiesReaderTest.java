package com.github.julenpardo.jmiddleware;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

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

    properties.put("mode", "1");
    properties.put("port", "5000");
    properties.put("multicastIp", "233.0.0.0");

    try {
      generator.createPropertiesFile(filename, properties);
      propertiesReader = new PropertiesReader();
      propertiesReader.readProperties();
    } catch (FileNotFoundException e) {
      fail("No exception should be thrown.");
    } catch (IOException e) {
      fail("No exception should be thrown.");
    }
  }

}
