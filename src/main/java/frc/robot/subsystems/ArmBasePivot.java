package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmBasePivot extends SingleMotorBase {
  protected final double positionP = 16;
  protected final double positionI = 20;
  protected final double positionD = 20;

  @Override
  protected double getPositionP() {
    return 20;
  }

  @Override
  protected double getPositionI() {
    return 0.0005;
  }

  @Override
  protected double getPositionD() {
    return 0;
  }

  @Override
  protected double getPIDLimits() {
    return 0.25;
  }
  // protected final double positionF = 0;
  private static final double gearRatio = 1.0 / 60.0;
  private static final double bigBeltGear = 42.0;
  private static final double smallBeltGear = 24.0;
  private static final double beltGearRatio = smallBeltGear / bigBeltGear;

  public ArmBasePivot() {
    super(20, gearRatio * beltGearRatio, "Arm Position", false);

  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Arm Pivot", getRadians());
    SmartDashboard.putNumber("Arm Velocity", getVelocity());
  }

  public void atRearLimit() {
    resetPosition(-0.38 / (2 * Math.PI));
    setReverseLimit(0.05 / (2 * Math.PI));
    setForwardLimit(2.05 / (2 * Math.PI));
  }
}
