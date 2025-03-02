package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class RaiseElevatorUntilStopped extends Command {
  private Elevator elevator;
  private Timer timeSinceStart = new Timer();

  public RaiseElevatorUntilStopped(Elevator elevator) {
    this.elevator = elevator;
    addRequirements(elevator);
  }

  @Override
  public void initialize() {
    timeSinceStart.start();
  }

  @Override
  public void execute() {
    elevator.setVelocity(0.5);
  }

  @Override
  public boolean isFinished() {
    if (timeSinceStart.get() < 0.5) return false;
    boolean atTop = elevator.getVelocity() < 0.1;

    if (atTop) {
      elevator.atTop();
    }

    return atTop;
  }
}
