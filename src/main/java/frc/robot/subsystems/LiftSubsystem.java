package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class LiftSubsystem extends Subsystem implements PIDSource, HUD.Provider {

    final WPI_TalonSRX liftMotors;
    final AnalogInput anglePot;
    final PIDController positionController;

    public LiftSubsystem(){
        super();
        liftMotors = new WPI_TalonSRX(Config.LIFT_MOTOR_PORT);
        liftMotors.setNeutralMode(NeutralMode.Brake);
        anglePot = new AnalogInput(Config.LIFT_POT_PORT);
        positionController = new PIDController(Config.BALL_PICKUP_PID_KP,
                Config.BALL_PICKUP_PID_KI,
                Config.BALL_PICKUP_PID_KD,
                anglePot, liftMotors);

        positionController.setAbsoluteTolerance(0.05);
        positionController.setOutputRange(-0.5, 0.5);
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
        return anglePot.getVoltage() * Config.LIFT_POT_DEGREES_PER_VOLT;
        //        return (double)liftMotors.getSensorCollection().getQuadraturePosition() / Config.BALL_PICKUP_ENCODER_DPP;
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
//        encoder.reset();
        liftMotors.stopMotor();
//        liftMotors.getSensorCollection().setQuadraturePosition(0, 0);
        disablePid();
    }

    public void initOperatorInterface() {
        this.setName("Lifty");
        addChild(anglePot);
        addChild(positionController);
        addChild(liftMotors);
        addChild(liftMotors.getSensorCollection());
        LiveWindow.add(this);
    }

    public void updateHUD(HUD hud) {
        double liftAngle = getLiftAngle();

        // Todo - move me based on lift angle
        hud.color(HUD.YELLOW).crosshair(HUD.relPoint(0.65, 0.35), 20);
    }
}