package vitals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class VitalsCheckerTest {

  @Test
  public void testVitalsOkWhenAllValuesAreWithinRange() throws InterruptedException {
    assertTrue(VitalsChecker.vitalsOk(98.1f, 70, 98));
  }

  @Test
  public void testTemperatureBelowLowerLimitIsCritical() {
    assertEquals("Temperature is critical!", VitalsChecker.firstCriticalVitalMessage(94.9f, 70, 98));
  }

  @Test
  public void testTemperatureAboveUpperLimitIsCritical() {
    assertEquals("Temperature is critical!", VitalsChecker.firstCriticalVitalMessage(102.1f, 70, 98));
  }

  @Test
  public void testPulseRateBelowLowerLimitIsOutOfRange() {
    assertEquals("Pulse Rate is out of range!", VitalsChecker.firstCriticalVitalMessage(98.6f, 59.9f, 98));
  }
}
