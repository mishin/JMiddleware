package com.github.julenpardo.jmiddleware.subscriber;

import com.github.julenpardo.jmiddleware.Generator;
import com.github.julenpardo.jmiddleware.ListeningSubscriber;
import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.properties.PropertiesReader;
import com.github.julenpardo.jmiddleware.publisher.Publisher;
import com.github.julenpardo.jmiddleware.socketfactory.SocketTypes;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class SubscriberTest {

  @Test
  public void testInitialize() throws IOException, InvalidPropertiesException {
    Generator generator = new Generator();
    String filename = "config.properites";
    HashMap<String, String> properties = new HashMap<String, String>();

    properties.put(PropertiesReader.PROPERTIES_SOCKET_MODE, String.valueOf(SocketTypes.LATENCY));
    properties.put(PropertiesReader.PROPERTIES_USER_MODE, "1");
    properties.put(PropertiesReader.PROPERTIES_TOPICS, "1,10,200");
    properties.put(PropertiesReader.PROPERTIES_PORT, "60000");
    properties.put(PropertiesReader.PROPERTIES_MULTICAST_IP, "224.0.0.0");

    generator.createPropertiesFile(filename, properties);

    Subscriber subscriber = new Subscriber(filename);

    generator.deletePropertiesFile(filename);
  }

  @Test(expected = InvalidPropertiesException.class)
  public void testInitializeInvalidPropertiesException() throws IOException,
          InvalidPropertiesException {
    new Subscriber("this file does not exist");
  }

  @Test(timeout = 35000)
  public void testGetLastSample() throws IOException, InvalidPropertiesException, InterruptedException {
    Generator generator = new Generator();
    String filename = "config.properties";
    HashMap<String, String> properties = new HashMap<String, String>();

    String inputMessage = "Testing get last sample method of subscriber!";

    properties.put(PropertiesReader.PROPERTIES_SOCKET_MODE, String.valueOf(SocketTypes.LATENCY));
    properties.put(PropertiesReader.PROPERTIES_USER_MODE, "1");
    properties.put(PropertiesReader.PROPERTIES_TOPICS, "1,10,200");
    properties.put(PropertiesReader.PROPERTIES_PORT, "60000");
    properties.put(PropertiesReader.PROPERTIES_MULTICAST_IP, "224.0.0.0");

    generator.createPropertiesFile(filename, properties);

    Subscriber subscriber = new Subscriber(filename);

    int inputTopic = 200;
    int port = 60000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");

    MulticastSocket multicastSocket = new MulticastSocket(port);
    multicastSocket.joinGroup(multicastIp);

    byte[] messageData = PacketConstructor.createPacket(SocketTypes.LATENCY, (byte) 1, inputTopic,
            inputMessage.getBytes());

    DatagramPacket packet = new DatagramPacket(messageData, messageData.length,
            multicastIp, port);

    ListeningSubscriber listeningSubscriber = new ListeningSubscriber(subscriber, inputTopic, 25000);

    listeningSubscriber.start();

    multicastSocket.send(packet);

    listeningSubscriber.join();

    byte[] actualData = listeningSubscriber.getReceivedData();
    byte[] expectedData = inputMessage.getBytes();

    assertEquals(new String(expectedData), new String(actualData));

    generator.deletePropertiesFile(filename);
  }
}
