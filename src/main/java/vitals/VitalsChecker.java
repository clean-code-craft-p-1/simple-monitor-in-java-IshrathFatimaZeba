package vitals;


public abstract class VitalsChecker {
  private static final float MIN_TEMPERATURE = 95f;
  private static final float MAX_TEMPERATURE = 102f;
  private static final float MIN_PULSE_RATE = 60f;
  private static final float MAX_PULSE_RATE = 100f;
  private static final float MIN_SPO2 = 90f;
  private static final int DEFAULT_ALERT_BLINKS = 6;
  private static final long DEFAULT_ALERT_DELAY_MILLIS = 1000;

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
    if (isOutOfRange(temperature, MIN_TEMPERATURE, MAX_TEMPERATURE)) {
      return "Temperature is critical!";
    }
    if (isOutOfRange(pulseRate, MIN_PULSE_RATE, MAX_PULSE_RATE)) {
      return "Pulse Rate is out of range!";
    }
    if (spo2 < MIN_SPO2) {
      return "Oxygen Saturation out of range!";
    }
    return null;
  }

  private static boolean isOutOfRange(float value, float min, float max) {
    return value < min || value > max;
  }

  private static void triggerAlert(String alertMessage, int blinkCount, long delayMillis)
      throws InterruptedException {
    System.out.println(alertMessage);
    for (int i = 0; i < blinkCount; i++) {
      System.out.print("\r* ");
      Thread.sleep(delayMillis);
      System.out.print("\r *");
      Thread.sleep(delayMillis);
    }
  }
}
