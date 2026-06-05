package vitals;

import java.io.PrintStream;


public final class VitalsChecker {
  private static final float MIN_TEMPERATURE = 95f;
  private static final float MAX_TEMPERATURE = 102f;
  private static final float MIN_PULSE_RATE = 60f;
  private static final float MAX_PULSE_RATE = 100f;
  private static final float MIN_SPO2 = 90f;
  private static final float MAX_SPO2 = 100f;
  private static final int DEFAULT_ALERT_BLINKS = 6;
  private static final long DEFAULT_ALERT_DELAY_MILLIS = 1000;
  private static final PrintStream DEFAULT_ALERT_OUTPUT = System.out;
  private static final VitalRule[] VITAL_RULES = new VitalRule[] {
      new VitalRule(MIN_TEMPERATURE, MAX_TEMPERATURE, "Temperature is critical!"),
      new VitalRule(MIN_PULSE_RATE, MAX_PULSE_RATE, "Pulse Rate is out of range!"),
      new VitalRule(MIN_SPO2, MAX_SPO2, "Oxygen Saturation out of range!")};

  private VitalsChecker() {
  }

  static boolean vitalsOk(float temperature, float pulseRate, float spo2) 
      throws InterruptedException {
    return vitalsOk(temperature, pulseRate, spo2, DEFAULT_ALERT_BLINKS, DEFAULT_ALERT_DELAY_MILLIS);
  }

  static boolean vitalsOk(
      float temperature,
      float pulseRate,
      float spo2,
      int alertBlinkCount,
      long alertDelayMillis) throws InterruptedException {
    String criticalVitalMessage = firstCriticalVitalMessage(temperature, pulseRate, spo2);
    if (criticalVitalMessage != null) {
      triggerAlert(criticalVitalMessage, alertBlinkCount, alertDelayMillis);
      return false;
    }
    return true;
  }

  static String firstCriticalVitalMessage(float temperature, float pulseRate, float spo2) {
    float[] vitalReadings = new float[] {temperature, pulseRate, spo2};
    for (int i = 0; i < VITAL_RULES.length; i++) {
      String outOfRangeMessage = VITAL_RULES[i].outOfRangeMessageFor(vitalReadings[i]);
      if (outOfRangeMessage != null) {
        return outOfRangeMessage;
      }
    }
    return null;
  }

  private static boolean isOutOfRange(float value, float min, float max) {
    return value < min || value > max;
  }

  private static void triggerAlert(String alertMessage, int blinkCount, long delayMillis)
      throws InterruptedException {
    DEFAULT_ALERT_OUTPUT.println(alertMessage);
    for (int i = 0; i < blinkCount; i++) {
      DEFAULT_ALERT_OUTPUT.print("\r* ");
      Thread.sleep(delayMillis);
      DEFAULT_ALERT_OUTPUT.print("\r *");
      Thread.sleep(delayMillis);
    }
  }

  private static final class VitalRule {
    private final float min;
    private final float max;
    private final String message;

    private VitalRule(float min, float max, String message) {
      this.min = min;
      this.max = max;
      this.message = message;
    }

    private String outOfRangeMessageFor(float value) {
      if (isOutOfRange(value, min, max)) {
        return message;
      }
      return null;
    }
  }
}
