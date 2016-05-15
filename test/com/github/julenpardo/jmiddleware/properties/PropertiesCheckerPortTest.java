package com.github.julenpardo.jmiddleware.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PropertiesCheckerPortTest {

  private int input;
  private boolean exceptionExpected;

  public PropertiesCheckerPortTest(int input, boolean exceptionExpected) {
    this.input = input;
    this.exceptionExpected = exceptionExpected;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] { {PropertiesChecker.MINIMUM_PORT, false},
            {PropertiesChecker.MINIMUM_PORT + 1, false},
            {PropertiesChecker.MAXIMUM_PORT, false},
            {PropertiesChecker.MAXIMUM_PORT - 1, false},
            {(PropertiesChecker.MINIMUM_PORT + PropertiesChecker.MAXIMUM_PORT) / 2, false},
            {PropertiesChecker.MINIMUM_PORT - 1, true},
            {PropertiesChecker.MINIMUM_PORT - 1000, true},
            {PropertiesChecker.MAXIMUM_PORT + 1, true},
            {PropertiesChecker.MAXIMUM_PORT + 1000, true},
    };

    return Arrays.asList(data);
  }

  @Test
  public void checkPortTest() {
    if (!this.exceptionExpected) {
      try {
        PropertiesChecker.checkPort(this.input);
      } catch (InvalidPropertiesException e) {
        fail("No exception should be thrown.");
      }
    }
  }

  public void checkPortTestInvalidPropertiesException() {
    if (this.exceptionExpected) {
      try {
        PropertiesChecker.checkPort(this.input);
        fail("Exception should be thrown.");
      } catch (InvalidPropertiesException e) {

      }
    }
  }
}
