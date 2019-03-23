package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class HatchSubsystem extends Subsystem {
    DoubleSolenoid doubleSolenoid = new DoubleSolenoid(Config.HATCH_PORT_0, Config.HATCH_PORT_1);
    boolean usingHatch = false;

    public HatchSubsystem() {
        super();
    }

    @Override
    public void periodic() {
        super.periodic();
        if(usingHatch){
            opened();
        }
        else{
            closed();
        }
    }

    @Override
    protected void initDefaultCommand() {}

    public void opened() {
        doubleSolenoid.set(Value.kForward);
    }

    public void closed(){
        doubleSolenoid.set(Value.kReverse);
    }

    public void toggleHatch() {
        usingHatch = !usingHatch;
    }
}