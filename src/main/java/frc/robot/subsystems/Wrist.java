package frc.robot.subsystems;

public class Wrist extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 1.0;

  public Wrist() {
    super(23, gearRatio, "Wrist Pivot");
  }
}
