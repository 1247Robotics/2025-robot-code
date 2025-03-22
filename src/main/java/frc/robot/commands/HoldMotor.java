package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SingleMotorBase;

public class HoldMotor extends Command {
  protected SingleMotorBase motor;
  protected double holdPosition = 0;

  public HoldMotor(SingleMotorBase motor) {
    this.motor = motor;
    addRequirements(motor);
  }

  @Override
  public void initialize() {
    holdPosition = motor.getPosition();
  }

  @Override
  public void execute() {
    motor.setPosition(holdPosition);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
