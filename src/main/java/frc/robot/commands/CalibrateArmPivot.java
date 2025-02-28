package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;

public class CalibrateArmPivot extends Command {
  private final ArmBasePivot pivot;
  private boolean foundBottom = false;
  private boolean foundTop = false;
  private boolean previouslyMoving = false;
  private Timer startedAt = new Timer();

  public CalibrateArmPivot(ArmBasePivot pivot) {
    this.pivot = pivot;
    addRequirements(pivot);
  }

  @Override
  public void initialize() {
    startedAt.start();
    
  }

  @Override
  public void execute() {
    if (!foundBottom) {
      pivot.setEffort(-0.25);

      if (!previouslyMoving && startedAt.get() < 0.25) {
        previouslyMoving = pivot.getVelocity() < 0;
        return;
      }

      if (pivot.getVelocity() > 0.05) {
        pivot.atRearLimit();
        foundBottom = true;
        previouslyMoving = false;
        return;
      }

      return;
    }

    if (!foundTop) {
      pivot.setEffort(0.25);

      if (!previouslyMoving) {
        previouslyMoving = pivot.getVelocity() > 0;
        return;
      }

      if (pivot.getVelocity() < 0.05) {
        pivot.atFrontLimit();
        foundTop = true;
        previouslyMoving = false;
        return;
      }

      return;
    }
  }

  @Override
  public boolean isFinished() {
    return foundTop;
  }
}
