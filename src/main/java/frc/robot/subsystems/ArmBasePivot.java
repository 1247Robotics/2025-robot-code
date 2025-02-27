package frc.robot.subsystems;

public class ArmBasePivot extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 16.0;

  public ArmBasePivot() {
    super(20, gearRatio, "Arm Base Pivot", false);
  }
}
