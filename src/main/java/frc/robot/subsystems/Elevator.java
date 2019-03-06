package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Elevator extends Subsystem {

    WPI_TalonSRX motor;


    public Elevator() {
        super("Elevator");

        motor = new WPI_TalonSRX(Config.ELEVATOR_MOTOR_PORT);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    protected void initDefaultCommand() {

    }

    public double getAngle()
    {
        return motor.getSensorCollection().getQuadraturePosition();
    }

    public void resetEncoder() {
        motor.getSensorCollection().setQuadraturePosition(0,0);
    }

    public void reset() {
        stop();
        resetEncoder();
    }


    public void stop() {
        motor.stopMotor();
    }

    public void up() {
        motor.set(ControlMode.PercentOutput, Config.ELEVATOR_SPEED);
    }

    public void down() {
        motor.set(ControlMode.PercentOutput, -Config.ELEVATOR_SPEED);
    }

    public void initOperatorInterface() {
        this.setName("Elevator");
        addChild(motor);
        addChild(motor.getSensorCollection());
        LiveWindow.add(this);
    }
}
