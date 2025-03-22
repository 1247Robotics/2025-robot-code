package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SingleMotorBase;

public class MoveMotor extends Command {
  private SingleMotorBase motor;
  private double targetPosition;

  public MoveMotor(SingleMotorBase extension, double targetPosition) {
    this.motor = extension;
    this.targetPosition = targetPosition;
    addRequirements(extension);
  }

  @Override
  public void execute() {
    motor.setRadians(targetPosition);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(motor.getRadians() - targetPosition) < 0.05;
  }
}
