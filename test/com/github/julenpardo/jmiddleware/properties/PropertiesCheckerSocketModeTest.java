package com.github.julenpardo.jmiddleware.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PropertiesCheckerSocketModeTest {

  private byte input;

  public PropertiesCheckerSocketModeTest(byte input) {
    this.input = input;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] { {(byte)0}, {(byte)2}, {(byte)-150}, {(byte)150} };

    return Arrays.asList(data);
  }

  @Test
  public void checkSocketTest() {
    byte input = 1;

    try {
      PropertiesChecker.checkSocketMode(input);
    } catch (InvalidPropertiesException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test(expected = InvalidPropertiesException.class)
  public void checkSocketModeTestInvalidPropertiesException() throws InvalidPropertiesException {
    PropertiesChecker.checkSocketMode(this.input);
  }
}
