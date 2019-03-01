package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

public class ElevatorSubsystem extends Subsystem{
    
    WPI_TalonSRX elevatorMotor = new WPI_TalonSRX(Config.ELEVATOR_MOTOR_PORT); 

    @Override
    protected void initDefaultCommand() {}

    public void periodic() {
        super.periodic();
        elevateup();
    }

    public void elevateup(){
        if(Robot.m_oi.xbox.getPOV(0) == 0){
            elevatorMotor.set(Config.ELEVATOR_MOTOR_SPEED_UP);
        }
        else if(Robot.m_oi.xbox.getPOV(180) == 180){
            elevatorMotor.set(Config.ELEVATOR_MOTOR_SPEED_DOWN);
        }
        else{
            //Nothing
        }
    }
}