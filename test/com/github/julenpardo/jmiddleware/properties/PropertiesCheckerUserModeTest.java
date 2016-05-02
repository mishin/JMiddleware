package com.github.julenpardo.jmiddleware.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PropertiesCheckerUserModeTest {

  private byte input;

  public PropertiesCheckerUserModeTest(byte input) {
    this.input = input;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] { {(byte)0}, {(byte)2}, {(byte)-150}, {(byte)150} };

    return Arrays.asList(data);
  }

  @Test
  public void checkModeTest() {
    byte input = 1;

    try {
      PropertiesChecker.checkUserMode(input);
    } catch (InvalidPropertiesException e) {
      fail("No exception should be thrown.");
    }
  }

  @Test(expected = InvalidPropertiesException.class)
  public void checkModeTestInvalidPropertiesException() throws InvalidPropertiesException {
    PropertiesChecker.checkUserMode(this.input);
  }
}
