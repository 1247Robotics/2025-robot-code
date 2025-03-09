package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmBasePivot extends SingleMotorBase {
  protected final double positionP = 0.3;
  protected final double positionI = 0.001;
  protected final double positionD = 0.01;
  private static final double gearRatio = 1.0 / 60.0;

  public ArmBasePivot() {
    super(20, gearRatio, "Arm Base Pivot", false);
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Arm Pivot Voltage", motor.getBusVoltage());
  }

  public void atFrontLimit() {
    setForwardLimit();
  }

  public void atRearLimit() {
    resetPosition();
    setReverseLimit();
  }
}
