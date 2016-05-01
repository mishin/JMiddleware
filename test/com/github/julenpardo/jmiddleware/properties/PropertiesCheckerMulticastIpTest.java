package com.github.julenpardo.jmiddleware.properties;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PropertiesCheckerMulticastIpTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {
            {"224.0.0.0", false},
            {"224.0.0.1", false},
            {"230.0.0.0", false},
            {"239.255.255.253", false},
            {"239.255.255.254", false},
            {"223.255.255.255", true},
            {"220.0.0.0", true},
            {"239.255.255.255", true},
            {"250.0.0.0", true},
    };

    return Arrays.asList(data);
  }

  private String input;
  private boolean exceptionExpected;

  public PropertiesCheckerMulticastIpTest(String input, boolean exceptionExpected) {
    this.input = input;
    this.exceptionExpected = exceptionExpected;
  }

  @Test
  public void checkMulticastIpTest() {
    if (!this.exceptionExpected) {
      try {
        PropertiesChecker.checkMulticastIp(this.input);
      } catch (InvalidPropertiesException e) {
        fail("No exception should be thrown.");
      }
    }
  }

  @Test
  public void checkMulticastIpTestInvalidPropertiesException() {
    if (this.exceptionExpected) {
      try {
        PropertiesChecker.checkMulticastIp(this.input);
        fail("Exception should be thrown.");
      } catch (InvalidPropertiesException e) { }
    }
  }

}
