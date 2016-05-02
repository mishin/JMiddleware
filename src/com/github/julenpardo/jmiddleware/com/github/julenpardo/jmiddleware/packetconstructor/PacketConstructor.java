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
  private static final byte TOPIC_LENGTH_POSITION = 6;
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

}
