package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private final Compressor comp = new Compressor(PneumaticsModuleType.REVPH);

  public void run() {
    comp.enableAnalog(90, 120);
    SmartDashboard.putNumber("Pneumatics Pressure (PSI)", comp.getPressure());
  }

  public void dontRun() {
    comp.disable();
  }

  public void stop() {
    comp.disable();
  }
}
