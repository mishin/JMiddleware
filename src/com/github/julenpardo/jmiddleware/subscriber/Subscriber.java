package com.github.julenpardo.jmiddleware.subscriber;

import com.github.julenpardo.jmiddleware.properties.Configuration;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.properties.PropertiesReader;
import com.github.julenpardo.jmiddleware.socket.AbstractSocket;
import com.github.julenpardo.jmiddleware.socketfactory.SocketFactory;

import java.io.IOException;

public class Subscriber {

  private AbstractSocket socket;
  private Configuration configuration;

  /**
   * Publisher constructor.
   *
   * @throws IOException If an error occurs instantiating the socket.
   * @throws InvalidPropertiesException If the properties file has some error.
   */
  public Subscriber(String configurationFilePath) throws IOException, InvalidPropertiesException {
    this.initialize(configurationFilePath);
  }

  /**
   * Initializes the publisher, reading the configuration file and instantiating the socket.
   *
   * @throws IOException If an error occurs instantiating the socket.
   * @throws InvalidPropertiesException If the properties file has some error.
   */
  private void initialize(String configurationFilePath) throws IOException, InvalidPropertiesException {
    PropertiesReader propertiesReader = new PropertiesReader(configurationFilePath);

    this.configuration = propertiesReader.readProperties();

    this.socket = SocketFactory.createSocket(this.configuration);
  }

  /**
   * Gets the last received sample of the given topic. This means that it will return the content
   * of the last received packet. If no packet has been received before, it will be blocked until
   * one is received.
   *
   * @param topic The topic to get the last sample of.
   * @return The data belonging to the given topic.
   * @throws IOException If an error occurs with the socket.
   */
  public byte[] getLastSample(int topic) throws IOException {
    byte[] data;

    data = this.socket.receiveData(topic);

    return data;
  }

}
