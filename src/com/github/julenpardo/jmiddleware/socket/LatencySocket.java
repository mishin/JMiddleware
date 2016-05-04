package com.github.julenpardo.jmiddleware.socket;

import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;

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
   * @throws IOException Thrown by super MulticastSocket constructor.
   */
  public LatencySocket(byte userType, byte socketType, ArrayList<Integer> topics, int port,
                       InetAddress multicastIp) throws IOException {
    super(userType, socketType, topics, port, multicastIp);
  }

  /**
   * Publishes the given data for the given topic.
   *
   * @param topic The topic the data will be published for.
   * @param data  The data to send.
   */
  @Override
  public void sendData(int topic, byte[] data) throws NotSubscribedToTopicException, IOException {
    boolean subscribedToTopic;
    byte[] packetData;
    DatagramPacket datagramPacket;

    subscribedToTopic = super.isSubscribedToTopic(topic);

    if (!subscribedToTopic) {
      throw new NotSubscribedToTopicException(NotSubscribedToTopicException.SENDING);
    }

    packetData = PacketConstructor.createPacket(super.socketType, super.userType, topic, data);
    datagramPacket = new DatagramPacket(packetData, packetData.length, super.multicastIp,
            super.port);

    super.send(datagramPacket);
  }

  /**
   * The data received for the subscribed topics.
   *
   * @param data Received data.
   */
  @Override
  public void receiveData(DatagramPacket data) {

  }
}
