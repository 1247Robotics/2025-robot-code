package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class MoveElevator extends Command {
  private Elevator elevator;
  private double position;
  private boolean absolute;

  public MoveElevator(Elevator elevator, double position, boolean absolute) {
    this.elevator = elevator;
    this.position = position;
    this.absolute = absolute;
    addRequirements(elevator);
  }

  public MoveElevator(Elevator elevator, double position) {
    this(elevator, position, false);
  }

  @Override
  public void initialize() {
    if (absolute) position = elevator.getPosition() + position;

    if (position > elevator.getLimit()) position = elevator.getLimit();
    if (position < 0) position = 0;
  }

  @Override
  public void execute() {
    elevator.setPosition(position);
  }

  @Override
  public boolean isFinished() {
    return Math.abs(elevator.getPosition() - position) < 0.1;
  }
}
