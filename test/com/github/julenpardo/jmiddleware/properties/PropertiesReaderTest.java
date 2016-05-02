package com.github.julenpardo.jmiddleware.properties;

import com.github.julenpardo.jmiddleware.Generator;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
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
      byte expectedUserMode = 1;
      byte expectedSocketMode = 1;
      String topics = "1,2,3,4";
      ArrayList<Integer> expectedTopics = new ArrayList<Integer>();
      int expectedPort = 5000;
      InetAddress expectedMulticastIp = InetAddress.getByName("233.0.0.0");

      properties.put(PropertiesReader.PROPERTIES_USER_MODE, String.valueOf(expectedUserMode));
      properties.put(PropertiesReader.PROPERTIES_SOCKET_MODE, String.valueOf(expectedSocketMode));
      properties.put(PropertiesReader.PROPERTIES_TOPICS, topics);
      properties.put(PropertiesReader.PROPERTIES_PORT, String.valueOf(expectedPort));
      properties.put(PropertiesReader.PROPERTIES_MULTICAST_IP, expectedMulticastIp.getHostName());

      generator.createPropertiesFile(filename, properties);
      propertiesReader = new PropertiesReader();
      configuration = propertiesReader.readProperties();

      // We construct the expected topic array list from the input string delimited by commas...
      String[] topicsArray = topics.split(PropertiesReader.TOPICS_DELIMITER);
      for (String topic : topicsArray) {
        expectedTopics.add(Integer.valueOf(topic));
      }

      byte actualUserMode = configuration.getUserMode();
      byte actualSocketMode = configuration.getSocketMode();
      ArrayList<Integer> actualTopics = configuration.getTopics();
      int actualPort = configuration.getPort();
      InetAddress actualMulticastIp = configuration.getMulticastIp();

      assertEquals(expectedUserMode, actualUserMode );
      assertEquals(expectedSocketMode, actualSocketMode);
      assertArrayEquals(expectedTopics.toArray(), actualTopics.toArray());
      assertEquals(expectedPort, actualPort);
      assertEquals(expectedMulticastIp.getHostName(), actualMulticastIp.getHostName());
    } catch (FileNotFoundException e) {
      fail("No exception should be thrown.");
    } catch (IOException e) {
      fail("No exception should be thrown.");
    } catch (InvalidPropertiesException e) {
      fail("No exception should be thrown.");
    }
  }

}
