package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Wrist extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 1.0;

  public Wrist() {
    super(23, gearRatio, "Wrist Pivot");
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Wrist Voltage", motor.getBusVoltage());
  }

  public void atStart() {
    resetPosition();
    setReverseLimit();
  }

  public void atEnd() {
    setForwardLimit();
  }
}
