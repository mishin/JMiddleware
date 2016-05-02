package com.github.julenpardo.jmiddleware.socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public abstract class AbstractSocket extends MulticastSocket {

  private byte userType;
  private byte socketType;
  private ArrayList<Integer> topics;
  private int port;
  private InetAddress multicastIp;

  /**
   * AbstractSocket constructor.
   *
   * @param userType User type (publisher, subscriber) read from properties.
   * @param socketType Socket type read from properties.
   * @param topics Topic list read from properties.
   * @param port Listening port read from properties.
   * @param multicastIp Multicast IP read from properties.
   * @throws IOException Thrown by super MulticastSocket constructor.
   */
  public AbstractSocket(byte userType, byte socketType, ArrayList<Integer> topics, int port,
                        InetAddress multicastIp) throws IOException {
    super(port);
    this.userType = userType;
    this.socketType = socketType;
    this.topics = topics;
    this.port = port;
    this.multicastIp = multicastIp;
  }

  /**
   * Publishes the given data for the given topic.
   *
   * @param topic The topic the data will be published for.
   * @param data The data to send.
   */
  public abstract void sendData(int topic, byte[] data);

  /**
   * The data received for the subscribed topics.
   *
   * @param data Received data.
   */
  public abstract void receiveData(DatagramPacket data);

}
