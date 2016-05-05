package com.github.julenpardo.jmiddleware.publisher;

import com.github.julenpardo.jmiddleware.Generator;
import com.github.julenpardo.jmiddleware.packetconstructor.InvalidPacketException;
import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.properties.PropertiesReader;
import com.github.julenpardo.jmiddleware.socket.NotSubscribedToTopicException;
import com.github.julenpardo.jmiddleware.socketfactory.SocketTypes;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

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

  @Test(timeout = 35000)
  public void testPublish() throws IOException, InvalidPropertiesException, InterruptedException,
          NotSubscribedToTopicException, InvalidPacketException {
    Generator generator = new Generator();
    String filename = PropertiesReader.PROPERTIES_FILENAME;
    HashMap<String, String> properties = new HashMap<String, String>();

    String inputMessage = "Testing publish method of publisher!";

    properties.put(PropertiesReader.PROPERTIES_SOCKET_MODE, String.valueOf(SocketTypes.LATENCY));
    properties.put(PropertiesReader.PROPERTIES_USER_MODE, "1");
    properties.put(PropertiesReader.PROPERTIES_TOPICS, "1,10,200");
    properties.put(PropertiesReader.PROPERTIES_PORT, "60000");
    properties.put(PropertiesReader.PROPERTIES_MULTICAST_IP, "224.0.0.0");

    generator.createPropertiesFile(filename, properties);

    Publisher publisher = new Publisher();

    ListeningSocket listeningSocket = new ListeningSocket(60000,
            InetAddress.getByName("224.0.0.0"), 25000);
    listeningSocket.start();

    publisher.publish(1, inputMessage.getBytes());

    listeningSocket.join();

    String expectedMessage = inputMessage;

    // We retrieve the actual values...
    DatagramPacket actualPacket = listeningSocket.getPacket();
    byte[] actualPacketData = actualPacket.getData();

    String actualMessage = new String(PacketConstructor.getData(actualPacketData));

    assertEquals(expectedMessage, actualMessage);

    generator.deletePropertiesFile(filename);
  }

  private class ListeningSocket extends Thread {
    private MulticastSocket socket;
    private int timeout;
    private byte[] buffer;
    private DatagramPacket packet;

    public ListeningSocket(int port, InetAddress multicastIp, int timeout) {
      try {
        this.socket = new MulticastSocket(port);
        this.timeout = timeout;
        socket.joinGroup(multicastIp);
        this.buffer = new byte[256];
        this.packet = new DatagramPacket(this.buffer, this.buffer.length);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public DatagramPacket getPacket() {
      return packet;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(this.timeout);
        socket.receive(this.packet);

      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
