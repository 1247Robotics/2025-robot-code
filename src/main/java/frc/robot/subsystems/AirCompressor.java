package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private final Compressor comp = new Compressor(PneumaticsModuleType.REVPH);

  public void run() {
    comp.enableHybrid(90, 110);
    SmartDashboard.putNumber("Pneumatics Pressure (PSI)", comp.getPressure());
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
