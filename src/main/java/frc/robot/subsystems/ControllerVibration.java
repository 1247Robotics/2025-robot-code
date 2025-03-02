package frc.robot.subsystems;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ControllerVibration extends SubsystemBase {
  private final CommandXboxController controller;

  public ControllerVibration(CommandXboxController controller) {
    this.controller = controller;
  }

  public void setVibration(double vibration) {
    controller.setRumble(RumbleType.kBothRumble, vibration);
  }
}
