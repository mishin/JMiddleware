package com.github.julenpardo.jmiddleware.socketfactory;

import com.github.julenpardo.jmiddleware.properties.Configuration;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.socket.AbstractSocket;
import com.github.julenpardo.jmiddleware.socket.LatencySocket;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class SocketFactoryTest {

  @Test
  public void testCreateSocket() throws IOException, InvalidPropertiesException {
    byte socketType = SocketTypes.LATENCY;
    byte userType = 1;
    ArrayList<Integer> topics  = new ArrayList<Integer>();
    int port = 6000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");
    Configuration configuration;

    configuration = new Configuration(userType, socketType, topics, port, multicastIp);

    AbstractSocket actualSocket = SocketFactory.createSocket(configuration);
    boolean isLatencySocket = actualSocket instanceof LatencySocket;

    assertTrue(isLatencySocket);
  }

  @Test(expected = InvalidPropertiesException.class)
  public void testCreateSocketInvalidPropertiesException() throws IOException, InvalidPropertiesException {
    byte socketType = SocketTypes.LATENCY + (byte)100;
    byte userType = 1;
    ArrayList<Integer> topics  = new ArrayList<Integer>();
    int port = 6000;
    InetAddress multicastIp = InetAddress.getByName("224.0.0.0");
    Configuration configuration;

    configuration = new Configuration(userType, socketType, topics, port, multicastIp);

    SocketFactory socketFactory = new SocketFactory();
    AbstractSocket actualSocket = SocketFactory.createSocket(configuration);
  }
}
