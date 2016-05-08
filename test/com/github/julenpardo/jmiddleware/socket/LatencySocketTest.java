package com.github.julenpardo.jmiddleware.socket;

import com.github.julenpardo.jmiddleware.ListeningMulticastSocket;
import com.github.julenpardo.jmiddleware.packetconstructor.InvalidPacketException;
import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LatencySocketTest {

  @Test(timeout = 35000)
  public void testSendData() throws IOException, NotSubscribedToTopicException,
          InterruptedException, InvalidPacketException {
    byte userType = 1;
    byte socketType = 1;
    ArrayList<Integer> topics = new ArrayList<Integer>() ;
    int port = 60000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");
    LatencySocket latencySocket;

    topics.add(1);
    topics.add(10);
    topics.add(100);

    int inputTopic = topics.get(1);
    byte[] inputData = "Testing send data".getBytes();

    latencySocket = new LatencySocket(userType, socketType, topics, port, multicastIp);

    ListeningMulticastSocket listeningSocket = new ListeningMulticastSocket(port, multicastIp, 25000);
    listeningSocket.start();

    latencySocket.sendData(inputTopic, inputData);

    listeningSocket.join();

    // We set the expected values...
    byte expectedUserType = userType;
    byte expectedSocketType = socketType;
    int expectedTopic = inputTopic;
    int expectedDataLength = inputData.length;
    byte[] expectedData = inputData;

    // We retrieve the actual values...
    DatagramPacket actualPacket = listeningSocket.getPacket();
    byte[] actualPacketData = actualPacket.getData();

    byte actualUserType = PacketConstructor.getUserMode(actualPacketData);
    byte actualSocketType = PacketConstructor.getSocketMode(actualPacketData);
    int actualTopic = PacketConstructor.getTopic(actualPacketData);
    int actualDataLength = PacketConstructor.getDataLength(actualPacketData);
    byte[] actualData = PacketConstructor.getData(actualPacketData);

    // And we do the assertions.
    assertEquals(expectedUserType, actualUserType);
    assertEquals(expectedSocketType, actualSocketType);
    assertEquals(expectedTopic, actualTopic);
    assertEquals(expectedDataLength, actualDataLength);
    assertEquals(new String(expectedData), new String(actualData));
  }

  @Test(timeout = 35000, expected = NotSubscribedToTopicException.class)
  public void testSendDataNotSubscribedToTopicException() throws IOException,
          NotSubscribedToTopicException, InterruptedException, InvalidPacketException {
    byte userType = 1;
    byte socketType = 1;
    ArrayList<Integer> topics = new ArrayList<Integer>() ;
    int port = 60000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");
    LatencySocket latencySocket;

    topics.add(1);
    topics.add(10);
    topics.add(100);

    // We need a topic that is not in the subscription list, in order to fire the exception.
    int inputTopic = -999;
    byte[] inputData = "Testing send data with exception".getBytes();

    latencySocket = new LatencySocket(userType, socketType, topics, port, multicastIp);

    latencySocket.sendData(inputTopic, inputData);
  }

  @Test(timeout = 35000)
  public void testReceiveData() throws IOException, InterruptedException {
    byte userType = 1;
    byte socketType = 1;
    ArrayList<Integer> topics = new ArrayList<Integer>() ;
    int port = 60000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");
    MulticastSocket multicastSocket;

    multicastSocket = new MulticastSocket(port);
    multicastSocket.joinGroup(multicastIp);

    topics.add(65);
    topics.add(155410);
    topics.add(7555);

    int inputTopic = topics.get(1);
    byte[] messageData = "Testing receive data".getBytes();
    byte[] data = PacketConstructor.createPacket(socketType, userType, inputTopic, messageData);
    DatagramPacket packet = new DatagramPacket(data, data.length, multicastIp, port);

    ListeningLatencySocket listeningLatencySocket = new ListeningLatencySocket(userType,
            socketType, topics, port, multicastIp, 25000, inputTopic);
    listeningLatencySocket.start();

    multicastSocket.send(packet);

    listeningLatencySocket.join();

    // We set the expected values...
    byte[] expectedData = messageData;

    // We retrieve the actual received data...
    byte[] actualData = listeningLatencySocket.getReceivedData();

    // And we do the assertion.
    assertEquals(new String(expectedData), new String(actualData));
  }

  private class ListeningLatencySocket extends Thread {
    private LatencySocket latencySocket;
    private int timeout;
    private byte[] receivedData;
    private int topic;

    public ListeningLatencySocket(byte userType, byte socketType, ArrayList<Integer> topics,
                                        int port, InetAddress multicastIp, int timeout, int topic) {
      try {
        this.latencySocket = new LatencySocket(userType, socketType, topics, port, multicastIp);
        this.timeout = timeout;
        this.topic = topic;
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public byte[] getReceivedData() {
      return receivedData;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(this.timeout);
        this.receivedData = latencySocket.receiveData(topic);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
