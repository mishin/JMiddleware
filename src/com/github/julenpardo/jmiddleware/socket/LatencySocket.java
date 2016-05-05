package com.github.julenpardo.jmiddleware.socket;

import com.github.julenpardo.jmiddleware.packetconstructor.InvalidPacketException;
import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;
import com.github.julenpardo.jmiddleware.properties.Configuration;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class LatencySocket extends AbstractSocket {

  /**
   * AbstractSocket constructor.
   *
   * @param userType    User type (publisher, subscriber) read from properties.
   * @param socketType  Socket type read from properties.
   * @param topics      Topic list read from properties.
   * @param port        Listening port read from properties.
   * @param multicastIp Multicast IP read from properties.
   * @throws IOException If an exception occurs calling super constructor, or joining multicast.
   */
  public LatencySocket(byte userType, byte socketType, ArrayList<Integer> topics, int port,
                       InetAddress multicastIp) throws IOException {
    super(userType, socketType, topics, port, multicastIp);
  }

  /**
   * AbstractSocket constructor, receiving the configuratoin object instead of all the properties
   * one by one.
   *
   * @param configuration The configuration object with the properties read from properties file.
   * @throws IOException If an exception occurs calling super constructor, or joining multicast.
   */
  public LatencySocket(Configuration configuration) throws IOException {
    super(configuration);
  }

  /**
   * Publishes the given data for the given topic.
   *
   * @param topic The topic the data will be published for.
   * @param data  The data to send.
   */
  @Override
  public void sendData(int topic, byte[] data) throws NotSubscribedToTopicException, IOException {
    byte[] packetData;
    DatagramPacket datagramPacket;

    if (!super.isSubscribedToTopic(topic)) {
      throw new NotSubscribedToTopicException(NotSubscribedToTopicException.SENDING);
    }

    packetData = PacketConstructor.createPacket(super.socketType, super.userType, topic, data);
    datagramPacket = new DatagramPacket(packetData, packetData.length, super.multicastIp,
            super.port);

    super.send(datagramPacket);
  }

  /**
   * Receives the data from the multicast socket. The receiving is blocking; the flow won't
   * continue until a packet is received.
   * The received packet must belong to one of the topics the subscriber receiving the data is
   * subscribed to. If a message of a non-subscribed topic is received, it will be ignored and
   * the loop will continue as if nothing happened.
   *
   * @return The byte array with received data.
   * @throws IOException If an exception occurs receiving the packet.
   */
  @Override
  public byte[] receiveData() throws IOException {
    byte[] buffer = new byte[super.BUFFER_SIZE];
    DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
    byte[] receivedData = null;
    int topic;
    boolean isSubscribedTopic = false;

    do {
      super.receive(receivedPacket);

      try {
        topic = PacketConstructor.getTopic(receivedPacket.getData());

        isSubscribedTopic = super.isSubscribedToTopic(topic);

        if (isSubscribedTopic) {
          receivedData = PacketConstructor.getData(receivedPacket.getData());
        }

      } catch (InvalidPacketException exception) {
        isSubscribedTopic = false;
      }
    } while (!isSubscribedTopic);

    return receivedData;
  }

}
