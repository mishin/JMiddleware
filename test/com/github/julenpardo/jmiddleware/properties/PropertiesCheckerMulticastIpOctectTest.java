package com.github.julenpardo.jmiddleware.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class PropertiesCheckerMulticastIpOctectTest {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {
            {PropertiesChecker.MINIMUM_MULTICAST_FIRST_OCTECT, 1, false},
            {PropertiesChecker.MINIMUM_MULTICAST_FIRST_OCTECT + 1, 1, false},
            {(PropertiesChecker.MINIMUM_MULTICAST_FIRST_OCTECT +
                    PropertiesChecker.MAXIMUM_MULTICAST_FIRST_OCTECT) / 2, 1, false},
            {PropertiesChecker.MAXIMUM_MULTICAST_FIRST_OCTECT - 1, 1, false},
            {PropertiesChecker.MAXIMUM_MULTICAST_FIRST_OCTECT, 1, false},
            {PropertiesChecker.MINIMUM_MULTICAST_FIRST_OCTECT - 1, 1, true},
            {PropertiesChecker.MINIMUM_MULTICAST_FIRST_OCTECT - 100, 1, true},
            {PropertiesChecker.MAXIMUM_MULTICAST_FIRST_OCTECT + 1, 1, true},
            {PropertiesChecker.MAXIMUM_MULTICAST_FIRST_OCTECT + 100, 1, true},
            {0, 2, false},
            {1, 2, false},
            {128, 2, false},
            {254, 2, false},
            {255, 2, false},
            {-1, 2, true},
            {-100, 2, true},
            {256, 2, true},
            {355, 2, true}
    };

    return Arrays.asList(data);
  }

  private String octetInput;
  private int octectNumberInput;
  private boolean expected;

  public PropertiesCheckerMulticastIpOctectTest(int octetInput, int octectNumberInput, boolean expected) {
    this.octetInput = String.valueOf(octetInput);
    this.octectNumberInput = octectNumberInput;
    this.expected = expected;
  }

  @Test
  public void invalidOctectTest() {
    boolean actual;

    try {
      Method method = PropertiesChecker.class.getDeclaredMethod("invalidOctect", String.class, int.class);
      method.setAccessible(true);

      actual = (boolean) method.invoke(null, this.octetInput, this.octectNumberInput);

      assertEquals(this.expected, actual);
    } catch (NoSuchMethodException e) {
      fail("No exception should be thrown.");
    } catch (InvocationTargetException e) {
      fail("No exception should be thrown.");
    } catch (IllegalAccessException e) {
      fail("No exception should be thrown.");
    }
  }

}
