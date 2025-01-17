package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AirCompressor extends SubsystemBase {
  private final Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);

  public void run(boolean run) {
    if (run) {
      comp.enableDigital();
    } else {
      comp.disable();
    }
  }    
}
