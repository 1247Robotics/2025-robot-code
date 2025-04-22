package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Wrist;

public class CalibrateArm extends SequentialCommandGroup {
  public CalibrateArm(ArmBasePivot pivot, Extension extension, Wrist wrist, CommandXboxController controller) {
    addCommands(
      new RetractUntilStopped(extension),
      new CalibrateWrist(wrist, controller),
      new MoveMotor(wrist, 0),
      new LowerArmUntilStopped(pivot),
      new MoveArm(pivot, 1),
      Commands.waitSeconds(0.25),
      Commands.deadline(
        Commands.sequence(
          Commands.waitSeconds(0.25),
          new MoveExtension(extension, 2)
        ),
        new HoldMotor(pivot))
    );
  }
  
}
