package com.github.julenpardo.jmiddleware.properties;

import java.net.InetAddress;
import java.util.ArrayList;

public class Configuration {

  private byte userMode;
  private byte socketMode;
  private ArrayList<Integer> topics;
  private int port;
  private InetAddress multicastIp;

  /**
   * Configuration empty constructor.
   */
  public Configuration() {
    this.userMode = 0;
    this.socketMode = 0;
    this.topics = new ArrayList<Integer>();
    this.port = 0;
    this.multicastIp = null;
  }

  /**
   * Configuration constructor.
   *
   * @param userMode The user mode read from properties.
   * @param socketMode The socket mode read from properties.
   * @param topics The topic list read from properties.
   * @param port The port read from properties.
   * @param multicastIp The multicast IP read from properties.
   */
  public Configuration(byte userMode, byte socketMode, ArrayList<Integer> topics, int port,
                       InetAddress multicastIp) {
    this.userMode = userMode;
    this.socketMode = socketMode;
    this.topics = topics;
    this.port = port;
    this.multicastIp = multicastIp;
  }

  public byte getUserMode() {
    return userMode;
  }

  public void setUserMode(byte userMode) {
    this.userMode = userMode;
  }

  public byte getSocketMode() {
    return socketMode;
  }

  public void setSocketMode(byte socketMode) {
    this.socketMode = socketMode;
  }

  public ArrayList<Integer> getTopics() {
    return topics;
  }

  public void setTopics(ArrayList<Integer> topics) {
    this.topics = topics;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public InetAddress getMulticastIp() {
    return multicastIp;
  }

  public void setMulticastIp(InetAddress multicastIp) {
    this.multicastIp = multicastIp;
  }

}
