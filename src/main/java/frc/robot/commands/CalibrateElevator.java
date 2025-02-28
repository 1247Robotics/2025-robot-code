package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

// limit should end up as around 47 cm

public class CalibrateElevator extends Command {
  private final Elevator elevator;
  private boolean foundBottom = false;
  private boolean foundTop = false;
  private boolean previouslyMoving = false;
  private Timer startedAt = new Timer();

  public CalibrateElevator(Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    startedAt.start();
    
  }

  @Override
  public void execute() {
    if (!foundBottom) {
      elevator.setEffort(-0.25);

      if (!previouslyMoving && startedAt.get() < 0.25) {
        previouslyMoving = elevator.getVelocity() < 0;
        return;
      }

      if (elevator.getVelocity() > 0.05) {
        elevator.atBottom();
        foundBottom = true;
        previouslyMoving = false;
        return;
      }

      return;
    }

    if (!foundTop) {
      elevator.setEffort(0.25);

      if (!previouslyMoving) {
        previouslyMoving = elevator.getVelocity() > 0;
        return;
      }

      if (elevator.getVelocity() < 0.05) {
        elevator.atTop();
        foundTop = true;
        previouslyMoving = false;
        return;
      }

      return;
    }
  }

  @Override
  public boolean isFinished() {
    elevator.setPosition(0);
    return foundTop;
  }
}
