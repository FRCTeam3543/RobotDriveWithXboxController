/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Config;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public XboxController xbox = new XboxController(Config.XBOX_PORT);

	// Instantiaion of Xbox
	public XboxController getJoystick() {
		return xbox;
	  }

	OI() {
	}

	void loop() {
		if (xbox.getYButtonPressed()) {
			Robot.driveSystem.reset();
		}

		if (xbox.getXButton()) {
			Robot.elevator.down();
		}
		else if (xbox.getBButton()) {
			Robot.elevator.up();
		}
		else if (xbox.getBumper(GenericHID.Hand.kRight)) {
			Robot.elevator.up();
		}
		else if (xbox.getBumper(GenericHID.Hand.kLeft)) {
			Robot.elevator.down();
		}
		else {
			Robot.elevator.stop();
		}
	}

	/**
	 * Make the controller rumble
	 */
	void rumble() {
		xbox.setRumble(GenericHID.RumbleType.kLeftRumble, Config.RUMBLE_VALUE);
	}

	void arcadeDrive() {
		// tricky
		double mag = xbox.getRawAxis(1);
		double turn = - xbox.getRawAxis(0);
		double throttle = 1 - Math.abs(xbox.getRawAxis(5)); // 0 to 1
		throttle = throttle * (1 - Config.THROTTLE_MIN) + Config.THROTTLE_MIN;
		// adjust magnitude and turn by the throttle value
		Robot.arcadeDrive(mag * throttle,  turn * throttle * 1.5, false);
	}
}
	

