package com.github.julenpardo.jmiddleware.packetconstructor;

import com.github.julenpardo.jmiddleware.packetconstructor.InvalidPacketException;
import com.github.julenpardo.jmiddleware.packetconstructor.PacketConstructor;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PacketConstructorTest {

  @Test
  public void testCreatePacket() {
    ByteBuffer byteBuffer;
    byte socketMode = 1;
    byte userMode = 1;
    int topic = 10;
    byte[] data = "testing create packet".getBytes();
    int dataLength = data.length;
    int totalPacketLength = 1 + 1 + 4 + 4 + dataLength;

    byteBuffer = ByteBuffer.allocate(totalPacketLength);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    byteBuffer.put(socketMode);
    byteBuffer.put(userMode);
    byteBuffer.putInt(dataLength);
    byteBuffer.putInt(topic);
    byteBuffer.put(data);

    byte[] expected = byteBuffer.array();
    byte[] actual = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    assertEquals(new String(expected), new String(actual));
  }

  @Test
  public void testCreatePacketNullData() {
    ByteBuffer byteBuffer;
    byte socketMode = 1;
    byte userMode = 1;
    int topic = 10;
    byte[] data = null;
    int dataLength = 0;
    int totalPacketLength = 1 + 1 + 4 + 4 + dataLength;

    byteBuffer = ByteBuffer.allocate(totalPacketLength);
    byteBuffer.order(ByteOrder.BIG_ENDIAN);
    byteBuffer.put(socketMode);
    byteBuffer.put(userMode);
    byteBuffer.putInt(dataLength);
    byteBuffer.putInt(topic);

    byte[] expected = byteBuffer.array();
    byte[] actual = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    assertEquals(new String(expected), new String(actual));
  }

  @Test
  public void testIsPacketValidTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    byte socketMode = 1;
    byte userMode = 2;
    int topic = 500;
    byte[] data = "testing is packet valid".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    Method method = PacketConstructor.class.getDeclaredMethod("isPacketValid", byte[].class);
    method.setAccessible(true);
    boolean expectedTrue = (boolean) method.invoke(null, inputPacket);

    assertTrue(expectedTrue);
  }

  @Test
  public void testIsPacketValidFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    byte[] inputInvalidPacket = "invalid".getBytes();

    Method method = PacketConstructor.class.getDeclaredMethod("isPacketValid", byte[].class);
    method.setAccessible(true);
    boolean expectedFalse = (boolean) method.invoke(null, inputInvalidPacket);

    assertFalse(expectedFalse);
  }

  @Test
  public void testGetSocketMode() {
    byte socketMode = 1;
    byte userMode = 2;
    int topic = 1540;
    byte[] data = "testing get socket mode".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    byte expected = socketMode;

    try {
      byte actual = PacketConstructor.getSocketMode(inputPacket);

      assertEquals(expected, actual);
    } catch (InvalidPacketException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test
  public void testGetUserMode() {
    byte socketMode = 1;
    byte userMode = 1;
    int topic = 1540;
    byte[] data = "testing get user mode".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    byte expected = userMode;

    try {
      byte actual = PacketConstructor.getUserMode(inputPacket);

      assertEquals(expected, actual);
    } catch (InvalidPacketException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test
  public void testGetDataLength() {
    byte socketMode = 1;
    byte userMode = 1;
    int topic = 1540;
    byte[] data = "testing get data length".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    int expected = data.length;

    try {
      int actual = PacketConstructor.getDataLength(inputPacket);

      assertEquals(expected, actual);
    } catch (InvalidPacketException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test
  public void testGetTopic() {
    byte socketMode = 1;
    byte userMode = 2;
    int topic = 413;
    byte[] data = "testing get topic".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    int expected = topic;

    try {
      int actual = PacketConstructor.getTopic(inputPacket);

      assertEquals(expected, actual);
    } catch (InvalidPacketException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test
  public void testGetData() {
    byte socketMode = 1;
    byte userMode = 2;
    int topic = 651;
    byte[] data = "testing get data".getBytes();

    byte[] inputPacket = PacketConstructor.createPacket(socketMode, userMode, topic, data);

    byte[] expected = data;

    try {
      byte[] actual = PacketConstructor.getData(inputPacket);

      assertEquals(new String(expected), new String(actual));
    } catch (InvalidPacketException e) {
      fail("No exception should be thrown.");
    }
  }
}
