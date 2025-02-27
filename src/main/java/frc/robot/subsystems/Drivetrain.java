package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.VirtualIMU.VirtualIMU;

// import com.revrobotics.;

public class Drivetrain extends SubsystemBase {
  // private final CANSparkMax
  private final SparkFlex F_LEFT = new SparkFlex(11, MotorType.kBrushless);
  private final SparkFlex R_LEFT = new SparkFlex(12, MotorType.kBrushless);
  private final SparkFlex F_RIGHT = new SparkFlex(13, MotorType.kBrushless);
  private final SparkFlex R_RIGHT = new SparkFlex(14, MotorType.kBrushless);
  private final DifferentialDrive drive = new DifferentialDrive(F_LEFT, F_RIGHT);
  private final VirtualIMU pos = new VirtualIMU(F_LEFT, F_RIGHT);
  private final CommandXboxController controller;

  public Drivetrain(CommandXboxController controller) {
    R_LEFT.configure(new SparkFlexConfig().follow(F_LEFT), null, null);
    R_RIGHT.configure(new SparkFlexConfig().follow(F_RIGHT), null, null);
    pos.resetDisplacement();
    this.controller = controller;

  }

  public void arcadeDrive(double speed, double rotation) {
    drive.arcadeDrive(speed, rotation);
    pos.updateMotors();
    SmartDashboard.putNumber("Angle", pos.getAngle());
    SmartDashboard.putNumber("Y Displacement", getYDisplacement());
    SmartDashboard.putNumber("X Displacement", getXDisplacement());

    double rumble = Math.abs(pos.getAvgAccel() / 2);

    if (rumble < 0.1) rumble = 0;

    SmartDashboard.putNumber("rumble", rumble);

    controller.setRumble(RumbleType.kBothRumble, rumble);
  }

  public void stop() {
    drive.stopMotor();
  }

  public void setBrakes(boolean value) {
    F_LEFT.configure(new SparkFlexConfig().idleMode(value ? IdleMode.kBrake : IdleMode.kCoast ), null, null);
    R_LEFT.configure(new SparkFlexConfig().idleMode(value ? IdleMode.kBrake : IdleMode.kCoast), null, null);
    F_RIGHT.configure(new SparkFlexConfig().idleMode(value ? IdleMode.kBrake : IdleMode.kCoast), null, null);
    R_RIGHT.configure(new SparkFlexConfig().idleMode(value ? IdleMode.kBrake : IdleMode.kCoast), null, null);
  }

  public double getYDisplacement() {
    return pos.getPosition()[0];
  }

  public double getXDisplacement() {
    return pos.getPosition()[1];
  }

  public void resetDisplacement() {
    pos.resetDisplacement();
  }

  /**
   * The accumulative rotation reading from the NavX
   * @return
   */
  public double getAngle() {
    return pos.getAngle();
  }

  /**
   * Get the current robot heading, relative to last reset and between [0, 360) 
   * @return
   */
  public double getHeading() {
    return pos.getAngle() % 360;
  }

  public double getRadianHeading() {
    return (getHeading() / 360) * (2 * Math.PI);
  }
}
