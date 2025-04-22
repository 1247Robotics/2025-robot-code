// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class Autos {
  /** Example static factory for an autonomous command. */
  // public static Command exampleAuto(ExampleSubsystem subsystem) {
    // return Commands.sequence(subsystem.exampleMethodCommand(), new ExampleCommand(subsystem));
  // }

  public static Command basicAuto(Drivetrain drivetrain) {
    return Commands.run(() -> drivetrain.arcadeDrive(-0.3, 0), drivetrain).withTimeout(2);
  }

  public static Command imuAuto(
    Drivetrain drivetrain
    // ArmBasePivot armBase,
    // Extension extension,
    // Wrist wrist,
    // CommandXboxController controller
    // ClimbPiston climber
    ) {
    return Commands.sequence(
      Commands.runOnce(() -> drivetrain.resetDisplacement(), drivetrain),
      new MoveTo(0, 7, drivetrain),
      // new MoveTo(0, -7, drivetrain),
      new MoveTo(0, 0, drivetrain)
    );
    // return new MoveTo(0, 2.5, drivetrain);

  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
