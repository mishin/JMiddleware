package com.github.julenpardo.jmiddleware.socketfactory;

import com.github.julenpardo.jmiddleware.properties.Configuration;
import com.github.julenpardo.jmiddleware.properties.InvalidPropertiesException;
import com.github.julenpardo.jmiddleware.socket.AbstractSocket;
import com.github.julenpardo.jmiddleware.socket.LatencySocket;

import java.io.IOException;

public class SocketFactory {

  /**
   * Creates the socket instance from the configuration read from the properties file.
   *
   * @param configuration The configuration object read from properties file.
   * @return The specified socket instance in the properties.
   * @throws IOException If an exception occurs creating the socket.
   * @throws InvalidPropertiesException If the specified socket type does not match with any of the
   * defined socket types.
   */
  public static AbstractSocket createSocket(Configuration configuration) throws IOException, InvalidPropertiesException {
    byte socketType = configuration.getSocketMode();
    AbstractSocket socket;

    switch (socketType) {
      case SocketTypes.LATENCY:
        socket = new LatencySocket(configuration);
        break;

      default:
        throw new InvalidPropertiesException("socket");
    }

    return socket;
  }
}
