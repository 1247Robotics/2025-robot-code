package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;

public class RaiseArmUntilStopped extends Command {
  private ArmBasePivot motor;
  private Timer timeSinceStart = new Timer();

  public RaiseArmUntilStopped(ArmBasePivot elevator) {
    this.motor = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timeSinceStart.start();
  }

  @Override
  public void execute() {
    motor.setEffort(0.2);
  }

  @Override
  public boolean isFinished() {
    if (timeSinceStart.get() < 1) return false;
    boolean atTop = Math.abs(motor.getVelocity()) < 1200;

    if (atTop) {
      motor.atFrontLimit();
    }

    return atTop;
  }
}
