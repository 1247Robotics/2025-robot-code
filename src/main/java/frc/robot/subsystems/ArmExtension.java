package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmExtension extends SingleMotorBase {

  private static final double gearRatio = 1.0 / 16.0;
  private static final double winchDiameter = 7.85;
  private static final double circumference = CalculateCircumference(winchDiameter);
  private final ArmBasePivot pivot;
  private final double edgeOffsetFromOrigin = 3.8354;
  private final double motorOffsetX = 4.6482;
  private final double motorOffsetDistance = 9.1694;
  private final double motorAngle = Math.asin(motorOffsetX / motorOffsetDistance);
  // private final double otherAngle = Math.PI - (0.5 * Math.PI + motorAngle);
  private final double motorOffsetY = Math.cos(motorAngle) * motorOffsetDistance;

  public ArmExtension(ArmBasePivot pivot) {
    super(22, gearRatio * circumference, "Arm Extension");
    this.pivot = pivot;
  }

  private double getXOfEdge() {
    return Math.cos(pivot.getPosition()) * edgeOffsetFromOrigin;
  }

  private double getYOfEdge() {
    return Math.sin(pivot.getPosition()) * edgeOffsetFromOrigin;
  }

  private double getDistanceBetweenMotorAndEdge() {
    double X = motorOffsetX - getXOfEdge();
    double Y = motorOffsetY - getYOfEdge();
    return Math.sqrt((X*X) + (Y*Y)); 
  }

  @Override
  protected void onTick() {
    double position = pivot.getPosition();
    double inRadians = position * 2 * Math.PI;
    double AAAAAAAAAAAAAAAAAAA = inRadians + Math.PI;
  }

  public void atExtensionLimit() {
    setForwardLimit();
  }

  public void atRetractionLimit() {
    resetPosition();
    setReverseLimit();
  }
}
