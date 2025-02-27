package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

public class MoveRelative extends MoveTo {
    public MoveRelative(double X, double Y, Drivetrain drivetrain, double forwardSpeed, double turningSpeed) {
        super(
            X + drivetrain.getXDisplacement(),
            Y + drivetrain.getYDisplacement(),
            drivetrain,
            forwardSpeed,
            turningSpeed
        );
  }

  public MoveRelative(double X, double Y, Drivetrain drivetrain) {
    super(
        X + drivetrain.getXDisplacement(),
        Y + drivetrain.getYDisplacement(),
        drivetrain
    );
  }

  public MoveRelative(double X, double Y, Drivetrain drivetrain, double speed) {
    super(
        X + drivetrain.getXDisplacement(),
        Y + drivetrain.getYDisplacement(),
        drivetrain,
        speed
    );
  }

  @Override
  public void initialize() {
    X += drivetrain.getXDisplacement();
    Y += drivetrain.getYDisplacement();
  }
}
