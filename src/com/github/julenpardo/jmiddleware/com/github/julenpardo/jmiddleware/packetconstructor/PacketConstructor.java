package com.github.julenpardo.jmiddleware.com.github.julenpardo.jmiddleware.packetconstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketConstructor {

  private static final byte SOCKET_MODE_LENGTH_IN_BYTES = 1;
  private static final byte USER_MODE_LENGTH_IN_BYTES = 1;
  private static final byte DATA_LENGTH_IN_BYTES = 4;
  private static final byte TOPIC_LENGTH_IN_BYTES = 4;

  private static final byte SOCKET_MODE_POSITION = 0;
  private static final byte USER_MODE_POSITION = 1;
  private static final byte DATA_LENGTH_POSITION = 2;
  private static final byte TOPIC_POSITION = 6;
  private static final byte DATA_POSITION = 10;

  /**
   * Creates a packet to send it.
   *
   * @param socketMode The mode the packet will be sent through.
   * @param userMode The user mode.
   * @param topic The topic the packet belongs to.
   * @param data The data itself of the message.
   * @return Byte array of the created packet to send.
   */
  public static byte[] createPacket(byte socketMode, byte userMode, int topic, byte[] data) {
    int dataLength;
    int totalPacketLength;
    ByteBuffer byteBuffer;

    if (data == null) {
      dataLength = 0;
      data = "".getBytes();
    } else {
      dataLength = data.length;
    }

    totalPacketLength = USER_MODE_LENGTH_IN_BYTES + SOCKET_MODE_LENGTH_IN_BYTES
            + DATA_LENGTH_IN_BYTES + TOPIC_LENGTH_IN_BYTES + dataLength;

    byteBuffer = ByteBuffer.allocate(totalPacketLength);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    byteBuffer.put(socketMode);
    byteBuffer.put(userMode);
    byteBuffer.putInt(dataLength);
    byteBuffer.putInt(topic);
    byteBuffer.put(data);

    return byteBuffer.array();
  }

  /**
   * Method that determines if a packet is valid.
   *
   * @param packet The packet to check if is valid.
   * @return true if the packet is valid; false if it is not.
   */
  private static boolean isPacketValid(byte[] packet) {
    int packetLength;
    int minimumRequiredLength;
    boolean validPacket;

    packetLength = packet.length;
    minimumRequiredLength = USER_MODE_LENGTH_IN_BYTES + SOCKET_MODE_LENGTH_IN_BYTES
            + DATA_LENGTH_IN_BYTES + TOPIC_LENGTH_IN_BYTES;

    validPacket = (packetLength < minimumRequiredLength) ? false : true;

    return validPacket;
  }

  /**
   * Retrieves the socket mode defined in the packet.
   *
   * @param packet The constructed packet.
   * @return The socket mode defined in the packet.
   * @throws InvalidPacketException If the given packet is invalid.
   */
  public static byte getSocketMode(byte[] packet) throws InvalidPacketException {
    if (!isPacketValid(packet)) {
      throw new InvalidPacketException();
    }

    return packet[SOCKET_MODE_POSITION];
  }

  /**
   * Retrieves the user mode defined in the packet.
   *
   * @param packet The constructed packet.
   * @return The user mode defined in the packet.
   * @throws InvalidPacketException If the given packet is invalid.
   */
  public static byte getUserMode(byte[] packet) throws InvalidPacketException {
    if (!isPacketValid(packet)) {
      throw new InvalidPacketException();
    }

    return packet[USER_MODE_POSITION];
  }

  /**
   * Retrieves the socket data length of the packet.
   *
   * @param packet The constructed packet.
   * @return The data length of the packet.
   * @throws InvalidPacketException If the given packet is invalid.
   */
  public static byte getDataLength(byte[] packet) throws InvalidPacketException {
    if (!isPacketValid(packet)) {
      throw new InvalidPacketException();
    }

    return packet[DATA_LENGTH_POSITION];
  }

  /**
   * Retrieves the topic defined in the packet.
   *
   * @param packet The constructed packet.
   * @return The topic defined in the packet.
   * @throws InvalidPacketException If the given packet is invalid.
   */
  public static byte getTopic(byte[] packet) throws InvalidPacketException {
    if (!isPacketValid(packet)) {
      throw new InvalidPacketException();
    }

    return packet[TOPIC_POSITION];
  }

  /**
   * Retrieves the data of the packet.
   *
   * @param packet The constructed packet.
   * @return The data of the packet.
   * @throws InvalidPacketException If the given packet is invalid.
   */
  public static byte getData(byte[] packet) throws InvalidPacketException {
    if (!isPacketValid(packet)) {
      throw new InvalidPacketException();
    }

    return packet[DATA_POSITION];
  }

}
