package com.github.julenpardo.jmiddleware;

import com.github.julenpardo.jmiddleware.socket.AbstractSocket;
import com.github.julenpardo.jmiddleware.socket.LatencySocket;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class ListeningJMiddlewareSocket extends Thread {

  private int timeout;
  private byte[] receivedData;
  private int topic;
  private AbstractSocket socket;

  public ListeningJMiddlewareSocket(AbstractSocket socketInstance, int topic, int timeout) {
    this.socket = socketInstance;
    this.topic = topic;
    this.timeout = timeout;
    this.topic = topic;
  }

  public byte[] getReceivedData() {
    return receivedData;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(this.timeout);
      this.receivedData = this.socket.receiveData(topic);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
