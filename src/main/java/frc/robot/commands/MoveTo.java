package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

/**
 * Move the robot to X Y relative to last reset.
 */
public class MoveTo extends Command {
  protected final Drivetrain drivetrain;
  protected double X;
  protected double Y;
  private final double P = 0.025;
  private final double I = 0.0024;
  private final double D = 0.0043;
  private final PIDController forwardPID = new PIDController(P, I, D);
  private final PIDController anglePID = new PIDController(P, I, D);
  private final double forwardLimit;
  private final double turningLimit;
  private boolean inverse = false;

  public MoveTo(double X, double Y, Drivetrain drivetrain, double forwardSpeed, double turningSpeed) {
    this.drivetrain = drivetrain;
    this.X = X;
    this.Y = Y;
    forwardLimit = forwardSpeed;
    turningLimit = turningSpeed;
    addRequirements(drivetrain);
  }

  public MoveTo(double X, double Y, Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    this.X = X;
    this.Y = Y;
    forwardLimit = 0.5;
    turningLimit = 0.6;
    addRequirements(drivetrain);
  }

  public MoveTo(double X, double Y, Drivetrain drivetrain, double speed) {
    this.drivetrain = drivetrain;
    this.X = X;
    this.Y = Y;
    forwardLimit = speed;
    turningLimit = speed;
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
    final double multiplier = getTurnMultiplier();
    final double turnAmount = -(getOffAngle() / (2*Math.PI)) * 360;
    final double distance = getDistance() * 40;

    final double turn = -clamp(anglePID.calculate(turnAmount * 2,  0), -turningLimit, turningLimit);
    // final double turn = clamp(turnAmount / 180, -0.6, 0.6);
    final double move = clamp(forwardPID.calculate(distance * 1, 0) * multiplier, -forwardLimit, forwardLimit) * 0.8;
    // final double move = -clamp(distance, -0.4, 0.4) * multiplier;

    drivetrain.arcadeDrive(move, turn);
  }

  private double XDist() {
    return X - drivetrain.getXDisplacement();
  }

  private double YDist() {
    return Y - drivetrain.getYDisplacement();
  }

  private double leastSignificant(double a, double b) {
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

  private double closerToZero(double a, double b) {
    final double A = Math.abs(a);
    final double B = Math.abs(b);
    if (A < B) {
      inverse = false;
      return a;
    }
    if (A > B) {
      inverse = true;
      return b;
    }
    return a;
  }

  private double getOffAngle() {
    final double target = -(Math.atan2(YDist(), XDist()) - 0.5*Math.PI);
    final double target2 = target - (2 * Math.PI);
    final double current = drivetrain.getRadianHeading();
    final double forward = leastSignificant(current - target, current - target2);
    final double backward1 = (current + Math.PI) - target;
    final double backward2 = backward1 - (2 * Math.PI);
    final double backward = leastSignificant(backward1, backward2);
    
    return closerToZero(forward, backward);
  }

  private double getDistance() {
    final double offX = XDist();
    final double offY = YDist();
    return Math.sqrt(offX * offX + offY * offY);
  }

  private double getTurnMultiplier() {
    final double offAngle = getOffAngle() / Math.PI;
    return clamp(-(Math.abs(offAngle) - 1), -1, 1) * (inverse ? -1 : 1);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    final double distance = getDistance();
    final boolean finished = distance < 0.1;
    if (finished) {
      drivetrain.arcadeDrive(0, 0);
    }

    return finished;
  }
}
