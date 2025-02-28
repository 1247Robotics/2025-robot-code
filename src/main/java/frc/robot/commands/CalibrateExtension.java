package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmExtension;

// limit should end up as around 47 cm

public class CalibrateExtension extends Command {
  private final ArmExtension extension;
  private boolean foundBottom = false;
  private boolean foundTop = false;
  private boolean previouslyMoving = false;
  private Timer actionTimer = new Timer();

  public CalibrateExtension(ArmExtension extension) {
    this.extension = extension;
    addRequirements(extension);
  }

  @Override
  public void initialize() {
    actionTimer.start();
    
  }

  @Override
  public void execute() {
    if (!foundBottom) {
      // extension.setEffort(-0.25);
      extension.setBrakes(false);

      if (!previouslyMoving && actionTimer.get() < 0.25) {
        previouslyMoving = extension.getVelocity() < 0;
        actionTimer.reset();
        return;
      }

      if (extension.getVelocity() > 0.05 || actionTimer.get() > 10) {
        extension.atExtensionLimit();
        foundBottom = true;
        previouslyMoving = false;
        actionTimer.reset();
        return;
      }

      return;
    }

    if (!foundTop) {
      extension.setBrakes(true);
      extension.setEffort(0.4);

      if (!previouslyMoving && actionTimer.get() < 10) {
        previouslyMoving = extension.getVelocity() > 0;
        actionTimer.reset();
        return;
      }

      if (extension.getVelocity() < 0.05 || actionTimer.get() > 10) {
        extension.atRetractionLimit();
        foundTop = true;
        previouslyMoving = false;
        actionTimer.stop();
        actionTimer = null;
        return;
      }

      return;
    }
  }

  @Override
  public boolean isFinished() {
    extension.setPosition(0);
    return foundTop;
  }
}
