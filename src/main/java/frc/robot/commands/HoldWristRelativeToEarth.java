package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.Wrist;

public class HoldWristRelativeToEarth extends Command {
  protected final ArmBasePivot pivot;
  protected final Wrist wrist;
  protected double wristPosition;
  protected double pivotPosition;

  public HoldWristRelativeToEarth(ArmBasePivot pivot, Wrist wrist) {
    this.pivot = pivot;
    this.wrist = wrist;
    addRequirements(wrist);
  }

  @Override
  public void initialize() {
    wristPosition = wrist.getPosition();
    pivotPosition = pivot.getPosition();
  }

  @Override
  public void execute() {
    double pivotDifference = pivotPosition - pivot.getPosition();
    double holdPosition = wristPosition - pivotDifference;
    wrist.setPosition(holdPosition);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
