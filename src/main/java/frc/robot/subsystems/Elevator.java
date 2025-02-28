package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 4.0;

  private static final double sprocketDiameter = 9.525;
  private static final double circumference = CalculateCircumference(sprocketDiameter);

  public Elevator() {
    super(21, gearRatio * circumference, "Elevator Position", false);
    SmartDashboard.setDefaultNumber("Elevator Position", 0.0);
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Elevator Volatage", motor.getBusVoltage());
  }

  public void atTop() {
    setForwardLimit();
  }

  public void atBottom() {
    resetPosition();
    setReverseLimit();
  }
}
