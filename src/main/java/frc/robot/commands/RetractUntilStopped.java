package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extension;

// limit should end up as around 47 cm

public class RetractUntilStopped extends Command {
  private final Extension extension;
  private Timer actionTimer = new Timer();

  public RetractUntilStopped(Extension extension) {
    this.extension = extension;
    addRequirements(extension);
  }

  @Override
  public void initialize() {
    actionTimer.reset();
    extension.disableForwardLimit();
    extension.disableReverseLimit();
    actionTimer.start();
    
  }

  @Override
  public void execute() {
    extension.setEffort(-0.25);
  }

  @Override
  public boolean isFinished() {
    if (actionTimer.get() < 0.75) return false;
    if (Math.abs(extension.getVelocity()) < 250) {
      extension.atRetractionLimit();
      return true;
    }
    return false;
  }
}
