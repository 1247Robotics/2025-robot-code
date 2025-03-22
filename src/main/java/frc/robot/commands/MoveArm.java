package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;

public class MoveArm extends Command {
  private ArmBasePivot motor;
  private double targetPosition;

  public MoveArm(ArmBasePivot elevator, double targetPosition) {
    this.motor = elevator;
    this.targetPosition = targetPosition;
    addRequirements(elevator);
  }

  @Override
  public void execute() {
    motor.setRadians(targetPosition);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(motor.getRadians() - targetPosition) < 0.2;
  }
}
