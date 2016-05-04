package com.github.julenpardo.jmiddleware.socket;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractSocket extends MulticastSocket {

  protected static final int BUFFER_SIZE = 10240;

  protected byte userType;
  protected byte socketType;
  protected ArrayList<Integer> topics;
  protected int port;
  protected InetAddress multicastIp;

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
   * Checks if the provided topic is in the list of subscribed topics, to avoid sending/receiving
   * packets belonging to topics that the user is not subscribed to.
   *
   * @param topic The topic to check the subscription.
   * @return True if it the provided topic is in the subscription topic list; false if it is not.
   */
  protected boolean isSubscribedToTopic(int topic) {
    Iterator<Integer> iterator = this.topics.iterator();
    int topicInList;
    boolean subscribed = false;

    while (iterator.hasNext()) {
      topicInList = iterator.next();

      if (topicInList == topic) {
        subscribed = true;
        break;
      }
    }

    return subscribed;
  }

  /**
   * Publishes the given data for the given topic.
   *
   * @param topic The topic the data will be published for.
   * @param data The data to send.
   */
  public abstract void sendData(int topic, byte[] data) throws NotSubscribedToTopicException, IOException;

  /**
   * The data received for the subscribed topics.
   *
   * @return The byte array with recived data.
   */
  public abstract byte[] receiveData() throws IOException;

}
