package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class MoveForward extends Command {
  private final Drivetrain drivetrain;
  private final double speed;

  public MoveForward(Drivetrain drivetrain, double speed) {
    this.drivetrain = drivetrain;
    this.speed = speed;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    // :(
  }

  @Override
  public void execute() {
    drivetrain.arcadeDrive(speed, 0);
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
