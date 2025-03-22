package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extension;

public class MoveExtension extends Command {
  private Extension motor;
  private double targetPosition;

  public MoveExtension(Extension extension, double targetPosition) {
    this.motor = extension;
    this.targetPosition = targetPosition;
    addRequirements(extension);
  }

  @Override
  public void execute() {
    motor.setPosition(targetPosition);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(motor.getPosition() - targetPosition) < 0.15;
  }
}
