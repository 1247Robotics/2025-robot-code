package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;

public class LowerArmUntilStopped extends Command {
  private ArmBasePivot motor;
  private Timer timeSinceStart = new Timer();

  public LowerArmUntilStopped(ArmBasePivot elevator) {
    this.motor = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timeSinceStart.reset();
    motor.disableForwardLimit();
    motor.disableReverseLimit();
    timeSinceStart.start();
  }

  @Override
  public void execute() {
    motor.setEffort(-0.1);
  }

  @Override
  public boolean isFinished() {
    if (timeSinceStart.get() < 0.5) return false;
    boolean atTop = Math.abs(motor.getVelocity()) < 4.5;

    if (atTop) {
      motor.atRearLimit();
      System.out.println("Reached end of arm base");
    }

    return atTop;
  }
}
