package com.github.julenpardo.jmiddleware.publisher;

import com.github.julenpardo.jmiddleware.Generator;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.properties.PropertiesReader;
import com.github.julenpardo.jmiddleware.socketfactory.SocketTypes;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by julen on 5/05/16.
 */
public class PublisherTest {

  @Test
  public void testInitialize() throws IOException, InvalidPropertiesException {
    Generator generator = new Generator();
    String filename = PropertiesReader.PROPERTIES_FILENAME;
    HashMap<String, String> properties = new HashMap<String, String>();

    properties.put(PropertiesReader.PROPERTIES_SOCKET_MODE, String.valueOf(SocketTypes.LATENCY));
    properties.put(PropertiesReader.PROPERTIES_USER_MODE, "1");
    properties.put(PropertiesReader.PROPERTIES_TOPICS, "1,10,200");
    properties.put(PropertiesReader.PROPERTIES_PORT, "60000");
    properties.put(PropertiesReader.PROPERTIES_MULTICAST_IP, "224.0.0.0");

    generator.createPropertiesFile(filename, properties);

    Publisher publisher = new Publisher();

    generator.deletePropertiesFile(filename);
  }

  @Test(expected = InvalidPropertiesException.class)
  public void testInitializeInvalidPropertiesException() throws IOException,
          InvalidPropertiesException {
    new Publisher();
  }

  @Test
  public void testPublish() {

  }
}
