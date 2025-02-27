package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * <p>Base class for controlling a single SparkMAX motor controller with a NEO and gearbox.</p>
 * 
 * <p>PID controller can be adjusted by defining the <code>P</code>, <code>I</code>, and <code>D</code> instance variables:</p>
 * <pre>{@code
 * public class Motor extends SingleMotorBase {
 *   protected final double P = 0.1;
 *   protected final double I = 0;
 *   protected final double D = 0;
 * }
 * </pre>
 * <p>The Motor and Closed Loop Controllers can be accessed with the <code>motor</code> and <code>closedLoop</code> variables, respectively.</p>
 */
public class SingleMotorBase extends SubsystemBase {
  protected final SparkMax motor;
  protected final double P = 0.025;
  protected final double I = 0.0024;
  protected final double D = 0.0043;

  protected final SparkClosedLoopController closedLoop;

  protected final String SmartDashboardKey;

  public SingleMotorBase(int CANID, double unitsPerRotation, String smartDashboardEntry) {
    motor = new SparkMax(CANID, MotorType.kBrushless);
    motor.clearFaults();

    SparkFlexConfig config = new SparkFlexConfig();
    config.idleMode(IdleMode.kBrake);
    config.absoluteEncoder.positionConversionFactor(unitsPerRotation);
    config.absoluteEncoder.velocityConversionFactor(unitsPerRotation);
    config.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      .p(P)
      .i(I)
      .d(D)
      .outputRange(-1, 1)

      .p(P, ClosedLoopSlot.kSlot1)
      .i(I, ClosedLoopSlot.kSlot1)
      .d(D, ClosedLoopSlot.kSlot1)
      .velocityFF(unitsPerRotation, ClosedLoopSlot.kSlot1)
      .outputRange(-1, 1, ClosedLoopSlot.kSlot1);


    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    motor.getEncoder().setPosition(0);

    closedLoop = motor.getClosedLoopController();

    if (!smartDashboardEntry.equals("NULL")) {
      SmartDashboard.setDefaultNumber(smartDashboardEntry, 0);
    }
    SmartDashboardKey = smartDashboardEntry;
  }

  SingleMotorBase(int CANID, double unitsPerRotation) {
    this(CANID, unitsPerRotation, "NULL");
  }

  SingleMotorBase(int CANID, String smartDashboardEntry) {
    this(CANID, 1.0, smartDashboardEntry);
  }

  SingleMotorBase(int CANID) {
    this(CANID, "NULL");
  }

  public void setPosition(double target) {
    closedLoop.setReference(target, ControlType.kPosition);
  }

  public void setVelocity(double target) {
    closedLoop.setReference(target, ControlType.kVelocity, ClosedLoopSlot.kSlot1);
  }

  public void setEffort(double effort) {
    motor.set(effort);
  }

  public double getPosition() {
    return motor.getAbsoluteEncoder().getPosition();
  }

  public double getVelocity() {
    return motor.getAbsoluteEncoder().getVelocity();
  }

  public void followValueFromSmartDashboard() {
    if (SmartDashboardKey.equals("NULL")) {
      DriverStation.reportError("Cannot read value of NULL SmartDashboard entry", null);
      return;
    }

    double position = SmartDashboard.getNumber(SmartDashboardKey, -1);
    if (position < 0) {
      SmartDashboard.putNumber(SmartDashboardKey, 0);
      position = 0;
    }
    setPosition(position);
  }
  public void stop() {
    motor.stopMotor();
  }  
}
