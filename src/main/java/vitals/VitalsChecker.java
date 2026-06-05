package vitals;


public abstract class VitalsChecker {
  private static final float MIN_TEMPERATURE = 95f;
  private static final float MAX_TEMPERATURE = 102f;
  private static final float MIN_PULSE_RATE = 60f;
  private static final float MAX_PULSE_RATE = 100f;
  private static final float MIN_SPO2 = 90f;

  static boolean vitalsOk(float temperature, float pulseRate, float spo2) 
      throws InterruptedException {
    String criticalVitalMessage = firstCriticalVitalMessage(temperature, pulseRate, spo2);
    if (criticalVitalMessage != null) {
      triggerAlert(criticalVitalMessage);
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

  private static void triggerAlert(String alertMessage) throws InterruptedException {
    System.out.println(alertMessage);
    for (int i = 0; i < 6; i++) {
      System.out.print("\r* ");
      Thread.sleep(1000);
      System.out.print("\r *");
      Thread.sleep(1000);
    }
  }
}
