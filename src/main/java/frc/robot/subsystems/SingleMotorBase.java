package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
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
 * <p>The <code>onTick</code> method is called every time any of <code>setPosition</code>, <code>setVelocity</code>, or <code>setEffort</code> gets run and can be changed to run any code, like sending info to SmartDashboard, every time the motor is interacted with.
 */
public class SingleMotorBase extends SubsystemBase {
  protected final SparkMax motor;
  protected final double P = 0.0015;
  protected final double I = 0.0024;
  protected final double D = 0.0043;
  protected final double F = 0;
  protected final double PIDLimits = 0.1;

  private double forwardLimit = Double.MAX_VALUE;
  private double reverseLimit = Double.MIN_VALUE;

  protected final SparkClosedLoopController closedLoop;

  protected final String SmartDashboardKey;

  protected double getPIDLimits() {
    return PIDLimits;
  }

  protected double getPositionP() {
    return P;
  }

  protected double getPositionI() {
    return I;
  }

  protected double getPositionD() {
    return D;
  }

  protected static double CalculateCircumference(double diameter) {
    return Math.PI * diameter;
  }

  protected final SparkMaxConfig config = new SparkMaxConfig();

  public SingleMotorBase(int CANID, double unitsPerRotation, String smartDashboardEntry, boolean invertMotor) {
    motor = new SparkMax(CANID, MotorType.kBrushless);
    motor.clearFaults();

    config.idleMode(IdleMode.kBrake);
    config.encoder.positionConversionFactor(unitsPerRotation);
    config.encoder.velocityConversionFactor(unitsPerRotation);
    config.inverted(invertMotor);
    config.closedLoop
      .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
      .p(getPositionP())
      .i(getPositionI())
      .d(getPositionD())
      // .pidf(positionP, positionI, positionD, positionF)
      .outputRange(-getPIDLimits(), getPIDLimits())

      // .p(velocityP, ClosedLoopSlot.kSlot1)
      // .i(velocityI, ClosedLoopSlot.kSlot1)
      // .d(velocityD, ClosedLoopSlot.kSlot1)
      // .pidf(velocityP, velocityI, velocityD, velocityF, ClosedLoopSlot.kSlot0)
      // .velocityFF(unitsPerRotation, ClosedLoopSlot.kSlot1)
      // .outputRange(-PIDLimits, PIDLimits, ClosedLoopSlot.kSlot1);
      ;


    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    motor.getEncoder().setPosition(0);

    closedLoop = motor.getClosedLoopController();

    if (!smartDashboardEntry.equals("NULL")) {
      SmartDashboard.setDefaultNumber(smartDashboardEntry, 0);
    }
    SmartDashboardKey = smartDashboardEntry;
  }

  SingleMotorBase(int CANID, double unitsPerRotation, String smartDashboardEntry) {
    this(CANID, unitsPerRotation, smartDashboardEntry, false);
  }

  SingleMotorBase(int CANID, double unitsPerRotation) {
    this(CANID, unitsPerRotation, "NULL", false);
  }

  SingleMotorBase(int CANID, String smartDashboardEntry) {
    this(CANID, 1.0, smartDashboardEntry);
  }

  SingleMotorBase(int CANID) {
    this(CANID, "NULL");
  }

  /**
   * Apply the latest config
   */
  protected void applyConfig() {
    motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  /**
   * Set the forward limit switch
   * @param limit
   */
  protected void setForwardLimit(double limit) {
    config.limitSwitch
      .forwardLimitSwitchEnabled(true)
      .forwardLimitSwitchType(Type.kNormallyOpen);

    config.softLimit
      .forwardSoftLimit(limit)
      .forwardSoftLimitEnabled(true);

    applyConfig();

    forwardLimit = limit;
  }

  /**
   * Set the forward limit switch to the current position of the motor.
   */
  protected void setForwardLimit() {
    setForwardLimit(motor.getEncoder().getPosition());
  }

  /**
   * Disable the forward limit switch
   */
  public void disableForwardLimit() {
    config.limitSwitch.forwardLimitSwitchEnabled(false);
    config.softLimit.forwardSoftLimitEnabled(false);
    applyConfig();

    forwardLimit = Double.MAX_VALUE;
  }

  /**
   * Set the reverse limit switch
   * @param limit
   */
  protected void setReverseLimit(double limit) {
    config.limitSwitch
      .reverseLimitSwitchEnabled(true)
      .reverseLimitSwitchType(Type.kNormallyOpen);
    
    config.softLimit
      .reverseSoftLimit(limit)
      .reverseSoftLimitEnabled(true);

    applyConfig();

    reverseLimit = limit;
  }

  /**
   * Set the reverse limit to the current position of the motor
   */
  protected void setReverseLimit() {
    setReverseLimit(motor.getEncoder().getPosition());
  }

  public void disableReverseLimit() {
    config.limitSwitch.reverseLimitSwitchEnabled(false);
    config.softLimit.reverseSoftLimitEnabled(false);
    applyConfig();

    reverseLimit = Double.MIN_VALUE;
  }

  public void setIdle(IdleMode mode) {
    config.idleMode(mode);
    applyConfig();
  }

  public void setBrakes(boolean enable) {
    setIdle(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  public void setPosition(double target) {
    closedLoop.setReference(target, ControlType.kPosition);
    onTick();
  }

  protected void resetPosition(double setPosition) {
    motor.getEncoder().setPosition(setPosition);
  }

  protected void resetPosition() {
    resetPosition(0);
  }

  public void setVelocity(double target) {
    closedLoop.setReference(target, ControlType.kVelocity, ClosedLoopSlot.kSlot1);
    onTick();
  }

  public void setEffort(double effort) {
    motor.set(effort);
    onTick();
  }

  public double getPosition() {
    return motor.getEncoder().getPosition();
  }

  public double getRadians() {
    return getPosition() * 2 * Math.PI;
  }

  public void setRadians(double position) {
    setPosition(position / (2 * Math.PI));
  }

  public double getVelocity() {
    return motor.getEncoder().getVelocity();
  }

  public double getForwardLimit() {
    return forwardLimit;
  }

  public double getReverseLimit() {
    return reverseLimit;
  }

  public void followValueFromSmartDashboard() {
    if (SmartDashboardKey.equals("NULL")) {
      DriverStation.reportError("Cannot read value of NULL SmartDashboard entry", null);
      return;
    }

    double position = SmartDashboard.getNumber(SmartDashboardKey, 0);
    // if (position <= reverseLimit) {
    //   position = (reverseLimit + forwardLimit) / 2;
    //   SmartDashboard.putNumber(SmartDashboardKey, position);
    // }
    SmartDashboard.putNumber("the", position);
    setPosition(position);
  }

  protected void onTick() {}

  public void stop() {
    motor.stopMotor();
  }
}
