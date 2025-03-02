package frc.robot.subsystems;

import com.revrobotics.spark.SparkAbsoluteEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 20.0;

  private static final double sprocketDiameter = 9.525;
  private static final double circumference = CalculateCircumference(sprocketDiameter);

  private double topMeasurement = -1;

  public Elevator() {
    super(21, gearRatio * circumference, "Elevator Position", false);
    SmartDashboard.setDefaultNumber("Elevator Position", getPosition());
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Elevator Volatage", motor.getBusVoltage());
  }

  public void atTop() {
    SparkAbsoluteEncoder encoder = motor.getAbsoluteEncoder();
    if (topMeasurement == -1) {
      topMeasurement = encoder.getPosition();
    } else {
      topMeasurement = (topMeasurement + encoder.getPosition()) / 2;
    }
    setForwardLimit(topMeasurement);
  }

  public void atBottom() {
    resetPosition();
    setReverseLimit();
  }

  public double getLimit() {
    return topMeasurement;
  }
}
