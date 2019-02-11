package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class BallPickupSubsystem extends Subsystem {

    WPI_TalonSRX motor = new WPI_TalonSRX(Config.BALL_PICKUP_MOTOR_PORT);
    DigitalInput hasBallSwitch = new DigitalInput(Config.BALL_PICKUP_SWITCH_PORT);

    boolean enabled = false;

    public BallPickupSubsystem() {
        super();
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Actually do the ball pickup work.
     *
     * If we have a ball, do nothing.  Otherwise, run the motor.
     */
    void pickupBall()
    {
        if (enabled && !hasBallSwitch.get()) {
            motor.set(Config.BALL_PICKUP_MOTOR_SPEED);
        }
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
}
