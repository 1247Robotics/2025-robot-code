package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Extension extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 4.0;

  private static final double sprocketDiameter = 1.9177;
  // private static final double circumference = CalculateCircumference(sprocketDiameter);

  @Override
  protected double getPositionP() {
    return 10;
  }

  @Override
  protected double getPositionI() {
    return 0.01;
  }

  @Override
  protected double getPositionD() {
    return 0;
  }

  @Override
  protected double getPIDLimits() {
    return 1;
  }

  public Extension() {
    super(21, gearRatio * sprocketDiameter, "Extension Position", true);
    // setForwardLimit(38);
    SmartDashboard.setDefaultNumber("Extension Position", getPosition());
    SmartDashboard.setDefaultNumber("Extension Distance", getExtensionDistance());
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Extension Position", this.getPosition());
    SmartDashboard.putNumber("Extension Distance", getPosition());
    SmartDashboard.putNumber("Extension Velocity", getVelocity());
  }

  public void logPos() {
    onTick();
  }

  public double getExtensionDistance() {
    return getPosition();
  }

  public void setExtensionDistance(double target) {
    setPosition(target);
  }

  public void atRetractionLimit() {
    resetPosition();
    
    setReverseLimit(0.2);
    setForwardLimit(10);
  }
}
