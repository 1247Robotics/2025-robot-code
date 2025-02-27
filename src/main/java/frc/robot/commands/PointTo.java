package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class PointTo extends Command {
  protected final Drivetrain drivetrain;
  protected double heading;
  private final double P = 0.025;
  private final double I = 0.0024;
  private final double D = 0.0048;
  private final PIDController anglePID = new PIDController(P, I, D);
  private final double turningLimit;

  public PointTo(double heading, Drivetrain drivetrain, double speed) {
    this.drivetrain = drivetrain;
    this.heading = heading;
    turningLimit = speed;
    addRequirements(drivetrain);
  }

  public PointTo(double heading, Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    this.heading = heading;
    turningLimit = 0.6;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    // :(
  }

  public double clamp(double x, double min, double max) {
    return Math.min(max, Math.max(min, x));
  }

  @Override
  public void execute() {
    final double turnAmount = -(getOffAngle() / (2*Math.PI)) * 360;

    final double turn = -clamp(anglePID.calculate(turnAmount * 2,  0), -turningLimit, turningLimit);

    drivetrain.arcadeDrive(0, turn);
  }

  private double closerToZero(double a, double b) {
    final double A = Math.abs(a);
    final double B = Math.abs(b);
    if (A < B) {
      return a;
    }
    if (A > B) {
      return b;
    }
    return a;
  }

  private double getOffAngle() {
    final double targetAngle = heading;
    final double targetAngle2 = heading - 360;
    final double currentAngle = drivetrain.getHeading();
    return closerToZero(currentAngle - targetAngle, currentAngle - targetAngle2);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    final double error = getOffAngle();
    final boolean finished = error < 0.05;
    if (finished) {
      drivetrain.arcadeDrive(0, 0);
      System.out.println("Facing " + heading);
    }

    return finished;
  }
}
