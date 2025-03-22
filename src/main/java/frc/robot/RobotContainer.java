// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.CalibrateArm;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveExtension;
import frc.robot.commands.ControlledMotor;
import frc.robot.commands.HoldWristRelativeToEarth;
import frc.robot.subsystems.AirCompressor;
import frc.robot.subsystems.ArmBasePivot;
import frc.robot.subsystems.ControllerVibration;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Wrist;
import frc.robot.subsystems.ClimbPiston;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  //#region Controllers
  private final CommandXboxController m_driverController       = new CommandXboxController(0);
  private final ControllerVibration   driverVibration          = new ControllerVibration(m_driverController);

  private final CommandXboxController m_armController          = new CommandXboxController(1);
  private final ControllerVibration   m_armControllerVibration = new ControllerVibration(m_armController);
  //#endregion

  //#region Drivetrain
  private final Drivetrain    drivetrain          = new Drivetrain();
  //#endregion

  //#region Pneumatics
  private final AirCompressor airCompressor       = new AirCompressor();
  private final ClimbPiston   launcherPistonThing = new ClimbPiston();
  //#endregion

  //#region Arm
  private final ArmBasePivot armBase  = new ArmBasePivot();
  private final Extension    extension = new Extension();
  private final Wrist        wrist    = new Wrist();
  //#endregion
  
  //#region Variables
  private boolean pistonGo = false;
  // private boolean pumpFailed = false;
  //#endregion

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    // drivetrain.setBrakes(true);
    SmartDashboard.setDefaultBoolean("Climber", pistonGo);
    SmartDashboard.setDefaultNumber("Arm Position", 0);
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
    // IMU haptics
    driverVibration.setDefaultCommand(
      new RunCommand(() -> driverVibration.setVibration(drivetrain.getIMURumble()), driverVibration));
      
    //#region Compressor

    // Run air compressor
    airCompressor.setDefaultCommand(new RunCommand(() -> airCompressor.run(), airCompressor));

    // // Buzz controllers if pressure is too high
    // airCompressor.whileOverPressure().onTrue(
    //   new RunCommand(() -> {
    //     driverVibration.setVibration(1);
    //     m_armControllerVibration.setVibration(1);
    //   }, driverVibration)
    // );
    //#endregion

    //#region Drivetrain 

    // Tie drivetrain to driver's controller 
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.arcadeDrive((Math.pow(m_driverController.getLeftY() * 1, 1) + m_driverController.getRightY()) * 0.75, Math.pow(m_driverController.getRightX() * 1, 1) * 0.75),
      drivetrain)
    );

    //#endregion

    //#region Climb piston
    // Retract solenoid on start
    // launcherPistonThing.runOnce(() -> launcherPistonThing.setActuation(false));

    // // Engage climb solenoid
    m_driverController.a().debounce(0.25).whileTrue(Commands.run(() -> {
      SmartDashboard.putBoolean("Climber", true);
      launcherPistonThing.setActuation(true);
    }));

    // // Disengage climb solenoid
    m_driverController.b().debounce(0.25).whileTrue(Commands.run(() -> {
      SmartDashboard.putBoolean("Climber", false);
      launcherPistonThing.setActuation(false);
    }));

    launcherPistonThing.extended().whileTrue(Commands.sequence(
      new MoveArm(armBase, 1.55),
      new MoveExtension(extension, 0.1)
     ));
    //#endregion

    //#region Arm
    //#region Elevator
    // Tie elevator to left stick Y on arm controller
    extension.setDefaultCommand(new RunCommand(() -> {
      extension.setEffort(Math.pow(-m_armController.getLeftY(), 3));
      SmartDashboard.putNumber("Elevator Effort", m_armController.getLeftY());
      SmartDashboard.putNumber("Elevator Position", extension.getPosition());
    }, extension));
    //#endregion

    //#region Arm Pivot
    // Tie arm pivot to right stick Y on arm controller
    armBase.setDefaultCommand(new ControlledMotor(armBase, () -> m_armController.getRightY() * 0.3));
    extension.setDefaultCommand(new ControlledMotor(extension, m_armController::getLeftY));
    wrist.setDefaultCommand(new ControlledMotor(wrist, () -> Math.pow(m_armController.getRightTriggerAxis() - m_armController.getLeftTriggerAxis(), 3)));

    m_armController.rightBumper().whileTrue(Commands.parallel(
      new HoldWristRelativeToEarth(armBase, wrist),
      Commands.run(() -> {
        drivetrain.setMultiplier(0.75);
      })
      ));
    m_armController.y().debounce(1).onTrue(new CalibrateArm(armBase, extension, wrist));

    //#endregion
    
    //#region Wrist
    // Tie wrist to left and right triggers on arm controller
    // wrist.setDefaultCommand(new RunCommand(() -> {
    //   double effort = Math.pow(m_driverController.getRightTriggerAxis() - m_driverController.getLeftTriggerAxis(), 3);
    //   wrist.setEffort(effort);
    //   SmartDashboard.putNumber("Wrist Effort", effort);
    // }, wrist));
    //#endregion
    //#endregion
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // drivetrain.resetDisplacement();
    return Autos.imuAuto(drivetrain);
    // return new RunCommand(() -> {}, null);
  }
}
