package com.github.julenpardo.jmiddleware.properties;

import java.net.InetAddress;

public class Configuration {

  private byte mode;
  private int port;
  private InetAddress multicastIp;

  public Configuration() {
    this.mode = 0;
    this.port = 0;
    this.multicastIp = null;
  }

  public Configuration(byte mode, int port, InetAddress multicastIp) {
    this.mode = mode;
    this.port = port;
    this.multicastIp = multicastIp;
  }

  public byte getMode() {
    return mode;
  }

  public void setMode(byte mode) {
    this.mode = mode;
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
