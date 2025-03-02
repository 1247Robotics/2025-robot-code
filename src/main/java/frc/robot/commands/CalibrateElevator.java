package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Elevator;

// limit should end up as around 47 cm

public class CalibrateElevator extends SequentialCommandGroup {

  public CalibrateElevator(Elevator elevator) {
    addCommands(
      new RaiseElevatorUntilStopped(elevator),
      new MoveElevator(elevator, -2, false),
      new RaiseElevatorUntilStopped(elevator),
      new MoveElevator(elevator, -2),
      new RaiseElevatorUntilStopped(elevator),
      new MoveElevator(elevator, elevator.getLimit() / 2)
    );
  }
}
