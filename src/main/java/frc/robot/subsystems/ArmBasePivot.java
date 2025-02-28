package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmBasePivot extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 16.0;

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
