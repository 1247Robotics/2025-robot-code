package frc.robot.subsystems;

public class Elevator extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 4.0;
  
  private static final double sprocketDiameter = 3.75;
  private static final double sprocketRadius = sprocketDiameter / 2;
  private static final double sprocketCircumference = Math.PI * sprocketRadius * sprocketRadius;

  public Elevator() {
    super(21, gearRatio * sprocketCircumference);
  }  
}
