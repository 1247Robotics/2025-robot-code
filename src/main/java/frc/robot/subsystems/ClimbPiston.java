// this file name is why you dont ask random people to name your classes
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class ClimbPiston extends SubsystemBase {
    // private final Solenoid solenoid = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);
    private final DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 1, 2);

    public ClimbPiston() {
        // solenoid.set(true);
    }
    
    public void setActuation(boolean value) {
        // solenoid.set(value);
        solenoid.set(value ? Value.kForward : Value.kReverse);
    }

    public Trigger extended() {
        // return new Trigger(() -> !solenoid.get());
        return new Trigger(() -> solenoid.get().equals(Value.kForward));
    }

    public Trigger retracted() {
        return new Trigger(() -> solenoid.get().equals(Value.kReverse));
    }

    // public void stop() {
    //     // solenoid.set(true);
    // }
}
