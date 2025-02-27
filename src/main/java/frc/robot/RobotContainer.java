// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.AirCompressor;
import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.ArmExtension;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.EddieSpaghetti;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Wrist;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain = new Drivetrain(m_driverController);
  private final AirCompressor airCompressor = new AirCompressor();
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final EddieSpaghetti launcherPistonThing = new EddieSpaghetti();
  private boolean pistonGo = false;
  private final ArmBasePivot armBase = new ArmBasePivot();
  private final Elevator elevator = new Elevator();
  private final ArmExtension armExtension = new ArmExtension();
  private final Wrist wrist = new Wrist();


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    drivetrain.setBrakes(true);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    //     .onTrue(new ExampleCommand(m_exampleSubsystem));

    airCompressor.setDefaultCommand(new RunCommand(() -> airCompressor.stop(), airCompressor));
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.arcadeDrive(m_driverController.getLeftY() * 0.75, m_driverController.getRightX() * 0.75),
      drivetrain)
    );
    m_driverController.a().toggleOnTrue(Commands.run(() -> {
      pistonGo = !pistonGo;
      launcherPistonThing.setActuation(pistonGo);
    }));
    armBase.setDefaultCommand(new RunCommand(() -> armBase.followValueFromSmartDashboard(), armBase));
    elevator.setDefaultCommand(new RunCommand(() -> elevator.followValueFromSmartDashboard(), elevator));
    wrist.setDefaultCommand(new RunCommand(() -> wrist.followValueFromSmartDashboard(), wrist));
    armExtension.setDefaultCommand(new RunCommand(() -> armExtension.followValueFromSmartDashboard(), armExtension));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(m_exampleSubsystem);
    drivetrain.resetDisplacement();
    return Autos.imuAuto(drivetrain);
  }
}
