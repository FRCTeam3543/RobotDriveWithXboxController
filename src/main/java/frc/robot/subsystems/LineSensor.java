package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class LineSensor extends Subsystem {
    DigitalInput leftSensor = new DigitalInput(Config.LINE_SENSOR_LEFT);
    DigitalInput rightSensor = new DigitalInput(Config.LINE_SENSOR_RIGHT);

    @Override
    protected void initDefaultCommand() {

    }

    public void initOperatorInterface() {
        setName("Line Sensor");
        leftSensor.setName("Left Line Sensor");
        rightSensor.setName("Right Line Sensor");

        addChild(leftSensor);
        addChild(rightSensor);
        LiveWindow.add(this);
    }

    public boolean isEitherEnabled() {
        return isLeftEnabled() || isRightEnabled();
    }

    public boolean isLeftEnabled() {
        return !leftSensor.get();
    }

    public boolean isRightEnabled() {
        return !rightSensor.get();
    }

    public boolean isBothEnabled() {
        return isLeftEnabled() && isRightEnabled();
    }
}
