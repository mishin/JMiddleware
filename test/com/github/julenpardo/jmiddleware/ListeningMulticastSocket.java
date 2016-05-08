package com.github.julenpardo.jmiddleware;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ListeningMulticastSocket extends Thread {

  public static final int BUFFER_SIZE = 256;
  private MulticastSocket socket;
  private int timeout;
  private byte[] buffer;
  private DatagramPacket packet;

  public ListeningMulticastSocket(int port, InetAddress multicastIp, int timeout) {
    try {
      this.socket = new MulticastSocket(port);
      this.timeout = timeout;
      socket.joinGroup(multicastIp);
      this.buffer = new byte[BUFFER_SIZE];
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