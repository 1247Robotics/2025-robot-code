package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.revrobotics.;

public class Drivetrain extends SubsystemBase {
  // private final CANSparkMax
  private final SparkMax F_LEFT = new SparkMax(0, MotorType.kBrushless);
  private final SparkMax R_LEFT = new SparkMax(1, MotorType.kBrushless);
  private final SparkMax F_RIGHT = new SparkMax(2, MotorType.kBrushless);
  private final SparkMax R_RIGHT = new SparkMax(3, MotorType.kBrushless);
  private final DifferentialDrive drive = new DifferentialDrive(F_LEFT, F_RIGHT);

  public Drivetrain() {
    R_LEFT.configure(new SparkMaxConfig().follow(F_LEFT), null, null);
    R_RIGHT.configure(new SparkMaxConfig().follow(F_RIGHT), null, null);

  }
  
}
