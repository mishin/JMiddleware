package com.github.julenpardo.jmiddleware.socket;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractSocketTest {

  private class ConcreteSocket extends AbstractSocket {

    public ConcreteSocket(byte userType, byte socketType, ArrayList<Integer> topics, int port,
                          InetAddress multicastIp) throws IOException {
      super(userType, socketType, topics, port, multicastIp);
    }

    @Override
    public void sendData(int topic, byte[] data) throws NotSubscribedToTopicException,
            IOException {

    }

    @Override
    public byte[] receiveData() {
      return null;
    }
  }

  @Test
  public void testIsSubscribedToTopicTrue() throws IOException {
    ArrayList<Integer> topics = new ArrayList<Integer>();
    ConcreteSocket abstractSocket;

    topics.add(1234);
    topics.add(45);
    topics.add(5641);
    topics.add(451);
    topics.add(65);
    topics.add(7);

    int intputTopic = topics.get(3);

    abstractSocket = new ConcreteSocket((byte)0, (byte)0, topics, 0, InetAddress.getByName("224.0.0.0"));
    boolean actual = abstractSocket.isSubscribedToTopic(intputTopic);

    assertTrue(actual);
  }

  @Test
  public void testIsSubscribedToTopicFalse() throws IOException {
    ArrayList<Integer> topics = new ArrayList<Integer>();
    ConcreteSocket abstractSocket;

    topics.add(1234);
    topics.add(45);
    topics.add(5641);
    topics.add(451);
    topics.add(65);
    topics.add(7);

    int intputTopic = 9999;

    abstractSocket = new ConcreteSocket((byte)0, (byte)0, topics, 0, InetAddress.getByName("224.0.0.0"));
    boolean actual = abstractSocket.isSubscribedToTopic(intputTopic);

    assertFalse(actual);
  }
}
