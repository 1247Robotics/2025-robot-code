package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class Wrist extends SingleMotorBase {
  private static final double gearRatio = 1.0 / 60.0;
  private static final double smallGear = 15;
  private static final double bigGear = 125;
  private static final double gearsRatio = smallGear / bigGear;
  // private boolean didHitSwitch = false;
  private DigitalInput homingSwitch = new DigitalInput(0);

  @Override
  protected double getPositionP() {
    return 50;
  }

  @Override
  protected double getPositionI() {
    return 0.001;
  }

  @Override
  protected double getPositionD() {
    return 0;
  }

  @Override
  protected double getPIDLimits() {
    return 1;
  }

  public Wrist() {
    super(23, gearRatio * gearsRatio, "Wrist Pivot", true);
  }

  @Override
  protected void onTick() {
    SmartDashboard.putNumber("Wrist Position", getPosition());
    if (homingSwitch.get()) {
      atStart();
    }
  }

  public boolean isHome() {
    return homingSwitch.get();
  }

  public Trigger atHome() {
    return new Trigger(this::isHome);
  }

  public void atStart() {
    System.out.println("resetting wrist");
    resetPosition(-0.01);
    setReverseLimit(0);
    setForwardLimit(0.55);
  }

  // public void atEnd() {
  //   setForwardLimit();
  // }
}
