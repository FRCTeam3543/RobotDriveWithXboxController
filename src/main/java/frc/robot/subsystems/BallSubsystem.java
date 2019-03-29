package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.OI;
import frc.robot.Robot;
import org.opencv.core.Mat;

public class BallSubsystem extends Subsystem implements HUDProvider {

    WPI_TalonSRX motor = new WPI_TalonSRX(Config.BALL_PICKUP_MOTOR_PORT_1);
    WPI_TalonSRX motor1 = new WPI_TalonSRX(Config.BALL_PICKUP_MOTOR_PORT_2);
    DigitalInput hasBallSwitch = new DigitalInput(Config.BALL_PICKUP_SWITCH_PORT);
    
    boolean enabled = false;

    boolean lockAndLoad = false;

    public BallSubsystem() {
        super();
    }

    @Override
    protected void initDefaultCommand() {}

    /**
     * Actually do the ball pickup work.
     *
     * If we have a ball, do nothing.  Otherwise, run the motor.
     */
    void pickupBall(){
        if (isLockAndLoad() && !hasABall()) {
            grab();
        }
        else if(isShootTriggerPressed()){
            shoot();
        }
        else {
            motor.stopMotor();
            motor1.stopMotor();
        }
    }

    long shootStartTime = 0;
    long SHOOT_DURATION = 500; // ms
    void startShooting() {
        shootStartTime = System.currentTimeMillis();
        shoot();
    }

    public void toggleLockAndLoad() {
        lockAndLoad = !lockAndLoad;
    }

    boolean isShootTriggerPressed() {
        return Robot.m_oi.xbox.getTriggerAxis(Hand.kLeft) > 0.55
//                || Robot.m_oi.getTrigger()
        ;
    }

    boolean isShooting() {
        return System.currentTimeMillis() - shootStartTime < SHOOT_DURATION;
    }

    void shoot() {
        lockAndLoad = false;
        motor.set(Config.BALL_SHOOT_MOTOR_SPEED);
        motor1.set(-Config.BALL_SHOOT_MOTOR_SPEED);    
    }

    void grab() {
        motor.set(-Config.BALL_PICKUP_MOTOR_SPEED);
        motor1.set(Config.BALL_PICKUP_MOTOR_SPEED);
    }

    boolean isLockAndLoad() {
        return lockAndLoad;
    }

    boolean hasABall() {
        return hasBallSwitch.get();
    }

    @Override
    public void periodic() {
        super.periodic();
        pickupBall();
    }

    /**
     * Return true if the ball pickup system is enabled
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }

    void setEnabled(boolean b) {
        enabled = b;
    }

    public void initOperatorInterface() {
        motor.setName("Ball Pickup Motor");
        addChild( motor);
        hasBallSwitch.setName("Has Ball Switch");
        addChild(hasBallSwitch);
        setName("Ball Pickup Subsystem");
        LiveWindow.add(this);
    }

    @Override
    public void updateHUD(Mat mat) {
        HUD.with(mat)
                .thickness(5)
                .color(hasABall() ? HUD.GREEN : HUD.WHITE)
                .circle(HUD.point(CameraSubsystem.WIDTH - 50, 25), 10);
    }
}