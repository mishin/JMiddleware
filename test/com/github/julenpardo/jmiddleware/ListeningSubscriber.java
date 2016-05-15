package com.github.julenpardo.jmiddleware;

import com.github.julenpardo.jmiddleware.subscriber.Subscriber;

import java.io.IOException;

public class ListeningSubscriber extends Thread {

  private int timeout;
  private Subscriber subscriber;
  private int topic;
  private byte[] receivedData;

  public ListeningSubscriber(Subscriber subscriber, int topic, int timeout) {
    this.subscriber = subscriber;
    this.topic = topic;
    this.timeout = timeout;
  }

  public byte[] getReceivedData() {
    return receivedData;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(this.timeout);
      this.receivedData = this.subscriber.getLastSample(topic);

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
