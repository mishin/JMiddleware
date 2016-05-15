package com.github.julenpardo.jmiddleware.publisher;

import com.github.julenpardo.jmiddleware.properties.Configuration;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.properties.PropertiesReader;
import com.github.julenpardo.jmiddleware.socket.AbstractSocket;
import com.github.julenpardo.jmiddleware.socket.NotSubscribedToTopicException;
import com.github.julenpardo.jmiddleware.socketfactory.SocketFactory;

import java.io.IOException;

public class Publisher {

  private AbstractSocket socket;
  private Configuration configuration;

  /**
   * Publisher constructor.
   *
   * @throws IOException If an error occurs instantiating the socket.
   * @throws InvalidPropertiesException If the properties file has some error.
   */
  public Publisher() throws IOException, InvalidPropertiesException {
    this.initialize();
  }

  /**
   * Initializes the publisher, reading the configuration file and instantiating the socket.
   *
   * @throws IOException If an error occurs instantiating the socket.
   * @throws InvalidPropertiesException If the properties file has some error.
   */
  private void initialize() throws IOException, InvalidPropertiesException {
    PropertiesReader propertiesReader = new PropertiesReader();

    this.configuration = propertiesReader.readProperties();

    this.socket = SocketFactory.createSocket(this.configuration);
  }

  /**
   * Sends the data through the instantiated socket for the given topic.
   *
   * @param topic The topic the message belongs to.
   * @param message The message to publish.
   * @throws IOException If an error occurs with the socket.
   * @throws NotSubscribedToTopicException If the given topic is not in the list of topics
   *     subscribed by the publisher.
   */
  public void publish(int topic, byte[] message) throws IOException,
          NotSubscribedToTopicException {
    this.socket.sendData(topic, message);
  }
}
