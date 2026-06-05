package vitals;

import static org.junit.Assert.assertNull;
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

  @Test
  public void testPulseRateAboveUpperLimitIsOutOfRange() {
    assertEquals("Pulse Rate is out of range!", VitalsChecker.firstCriticalVitalMessage(98.6f, 100.1f, 98));
  }

  @Test
  public void testSpo2BelowLowerLimitIsOutOfRange() {
    assertEquals("Oxygen Saturation out of range!", VitalsChecker.firstCriticalVitalMessage(98.6f, 70, 89.9f));
  }

  @Test
  public void testLowerBoundariesAreAccepted() {
    assertNull(VitalsChecker.firstCriticalVitalMessage(95f, 60f, 90f));
  }

  @Test
  public void testUpperBoundariesAreAccepted() {
    assertNull(VitalsChecker.firstCriticalVitalMessage(102f, 100f, 100f));
  }

  @Test
  public void testNoVitalOutOfRangeReturnsNoMessage() {
    assertNull(VitalsChecker.firstCriticalVitalMessage(98.6f, 75f, 97f));
  }

  @Test
  public void testFailureWhenAnyVitalOutOfRange() {
    assertTrue(VitalsChecker.firstCriticalVitalMessage(98.6f, 70f, 89f) != null);
  }
}
