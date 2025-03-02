package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class AirCompressor extends SubsystemBase {
  private final Compressor comp = new Compressor(PneumaticsModuleType.REVPH);

  public void run() {
    comp.enableHybrid(90, 110);
    SmartDashboard.putNumber("Pneumatics Pressure (PSI)", comp.getPressure());
    SmartDashboard.putBoolean("Running Air Compressor", comp.getCurrent() > 0.1);
    if (comp.getPressure() > 114) {
      DriverStation.reportError("AIR PRESSURE OUT OF CONTROL", null);
      DriverStation.reportWarning("AIR PRESSURE AT " + comp.getPressure(), false);
    }
  }

  public Trigger whileOverPressure() {
    return new Trigger(() -> comp.getPressure() > 114);
  }

  public void pressurize() {
    run();
  }

  public void go() {
    run();
  }

  public void doTheThing() {
    run();
  }

  public void dontRun() {
    comp.disable();
  }

  public void stop() {
    comp.disable();
  }
}
