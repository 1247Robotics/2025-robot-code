// this file name is why you dont ask random people to name your classes
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimbPiston extends SubsystemBase {
    private final Solenoid solenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);

    public ClimbPiston() {
        // solenoid.set(true);
    }
    
    public void setActuation(boolean value) {
        solenoid.set(value);
        System.out.println("Solinoid set to "+value);
    }

    public void stop() {
        // solenoid.set(true);
    }
}
