package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Wrist;

public class CalibrateWrist extends Command {
  private Wrist motor;
  private Timer timeSinceStart = new Timer();

  public CalibrateWrist(Wrist wrist) {
    this.motor = wrist;
    addRequirements(wrist);
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
    motor.setEffort(-0.75);
  }

  @Override
  public boolean isFinished() {
    return motor.isHome();
  }
}
