package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmExtension extends SingleMotorBase {

  private static final double gearRatio = 1.0 / 16.0;
  private static final double winchDiameter = 7.85;
  private static final double circumference = CalculateCircumference(winchDiameter);

  public ArmExtension() {
    super(22, gearRatio * circumference, "Arm Extension");
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Arm Extension Voltage", motor.getBusVoltage());
  }

  public void atExtensionLimit() {
    setForwardLimit();
  }

  public void atRetractionLimit() {
    resetPosition();
    setReverseLimit();
  }
}
