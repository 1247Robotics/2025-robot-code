package frc.robot.commands;

import java.util.function.DoubleSupplier;

import frc.robot.subsystems.SingleMotorBase;

public class ControlledMotor extends HoldMotor {
  DoubleSupplier controlSupplier;

  public ControlledMotor(SingleMotorBase motor, DoubleSupplier controlSupplier) {
    super(motor);
    this.controlSupplier = controlSupplier;
  }

  @Override
  public void execute() {
    double input = controlSupplier.getAsDouble();

    if (Math.abs(input) < 0.05) {
      super.execute();
      return;
    }

    motor.setEffort(input);
    holdPosition = motor.getPosition();
  }
  
}
