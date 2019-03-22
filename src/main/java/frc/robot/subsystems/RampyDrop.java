package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class RampyDrop extends Subsystem {
    DoubleSolenoid doubleSolenoid = new DoubleSolenoid(Config.RAMP_PORT_2, Config.RAMP_PORT_3);
    boolean usingRaam = false;
    
    @Override
    public void periodic() {
        super.periodic();
        open();
    }

    @Override
    protected void initDefaultCommand() {}

    public void open(){
        if(Robot.m_oi.xbox.getStartButton() && Robot.m_oi.xbox.getBackButton()){
            usingRaam = !usingRaam;
        }

        if(usingRaam){
           doubleSolenoid.set(Value.kForward);
        }
        else{
            doubleSolenoid.set(Value.kReverse);
        }
    }
}