package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Wrist;

public class CalibrateWrist extends Command {
  private Wrist motor;
  private Timer timeSinceStart = new Timer();
  private boolean lastAbort = false;
  private boolean abort = false;
  private CommandXboxController controller;

  public CalibrateWrist(Wrist wrist, CommandXboxController controller) {
    this.motor = wrist;
    this.controller = controller;
    addRequirements(wrist);
  }

  @Override
  public void initialize() {
    timeSinceStart.reset();
    motor.disableForwardLimit();
    motor.disableReverseLimit();
    timeSinceStart.start();
    lastAbort = controller.x().getAsBoolean();
  }

  @Override
  public void execute() {
    motor.setEffort(-0.75);
  }

  @Override
  public boolean isFinished() {
    if (abort) return true;
    if (controller.x().getAsBoolean() && !lastAbort) {
      abort = true;
      return true;
    }
    lastAbort = controller.x().getAsBoolean();
    
    if (motor.isHome()) {
      motor.atStart();
    }
    return motor.isHome();
  }
}
