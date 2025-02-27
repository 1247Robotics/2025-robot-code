package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmExtension extends SingleMotorBase {

  private static final double winchDiameter = 7.85;
  private static final double circumference = CalculateCircumference(winchDiameter);

  public ArmExtension() {
    super(22, circumference, "Arm Extension");
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Arm Extension Voltage", motor.getBusVoltage());
  }
}
