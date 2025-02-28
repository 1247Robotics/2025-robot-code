package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.Wrist;

public class CalibrateWrist extends Command {
  private final Wrist wrist;
  private final ArmBasePivot pivot;
  private boolean foundBottom = false;
  private boolean foundTop = false;
  private boolean previouslyMoving = false;
  private Timer startedAt = new Timer();

  public CalibrateWrist(Wrist wrist, ArmBasePivot pivot) {
    this.wrist = wrist;
    this.pivot = pivot;
    addRequirements(wrist, pivot);
  }

  @Override
  public void initialize() {
    startedAt.start();
    
  }

  @Override
  public void execute() {
    pivot.setPosition( 0.5);
    if (!foundBottom) {
      wrist.setEffort(-0.25);

      if (!previouslyMoving && startedAt.get() < 0.25) {
        previouslyMoving = wrist.getVelocity() < 0;
        return;
      }

      if (wrist.getVelocity() > 0.05) {
        wrist.atStart();
        foundBottom = true;
        previouslyMoving = false;
        return;
      }

      return;
    }

    if (!foundTop) {
      wrist.setEffort(0.25);

      if (!previouslyMoving) {
        previouslyMoving = wrist.getVelocity() > 0;
        return;
      }

      if (wrist.getVelocity() < 0.05) {
        wrist.atEnd();
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
