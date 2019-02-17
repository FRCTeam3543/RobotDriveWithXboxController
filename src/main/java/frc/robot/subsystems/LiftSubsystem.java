package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class LiftSubsystem extends Subsystem implements PIDSource {

    WPI_TalonSRX liftMotors = new WPI_TalonSRX(Config.LIFT_MOTOR_PORT);

    PIDController positionController = new PIDController(Config.BALL_PICKUP_PID_KP,
    Config.BALL_PICKUP_PID_KI,
    Config.BALL_PICKUP_PID_KD,
    this, liftMotors);

    public LiftSubsystem(){
        super();       
    }

    @Override
    public void periodic() {
        super.periodic();
        startLift();
        SmartDashboard.putNumber("Lifty Encoder", getLiftAngle());
        SmartDashboard.putNumber("PID Setpoint", positionController.getSetpoint());
        SmartDashboard.putNumber("PID Error", positionController.getError());
        SmartDashboard.putBoolean("PID Enabled", positionController.isEnabled());
    }

    @Override
    protected void initDefaultCommand() {}

    public void startLift(){
        if(Robot.m_oi.xbox.getBumper(Hand.kRight)){
            disablePid();
            Liftup();
        }        
        else if (Robot.m_oi.xbox.getTriggerAxis(Hand.kRight) < -0.7) {
            enablePid(getLiftAngle());
//            stay();
        }
        else {
            disablePid();
        }
        if(Robot.m_oi.xbox.getBumper(Hand.kLeft)){
            stop();
        }
    }

    public void stop(){
        liftMotors.stopMotor();
    }
    public void Liftup(){
        liftMotors.set(Config.LIFT_SPEED_UP);
    }

    public void stay(){

        liftMotors.set(Config.LIFT_STAY);
    }


    void enablePid(double setPoint) {
        positionController.setAbsoluteTolerance(2); // FIXME
        positionController.setSetpoint(setPoint);
        positionController.enable();
    }

    void disablePid() {
        positionController.disable();
    }

    /**
     * Returns encoder position in radians (for the lift)
     * @return
     */
    double getLiftAngle() {
        return (double)liftMotors.getSensorCollection().getQuadraturePosition() / Config.BALL_PICKUP_ENCODER_DPP;
    }

    PIDSourceType pidSource;
    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return pidSource;
    }

    @Override
    public double pidGet() {
        return getLiftAngle();
	}



    public void reset() {
        liftMotors.stopMotor();
        liftMotors.getSensorCollection().setQuadraturePosition(0, 0);
        disablePid();
    }

    public void initOperatorInterface() {
        this.setName("Lifty");
        addChild(positionController);
        addChild(liftMotors);
        addChild(liftMotors.getSensorCollection());
        LiveWindow.add(this);
    }
}