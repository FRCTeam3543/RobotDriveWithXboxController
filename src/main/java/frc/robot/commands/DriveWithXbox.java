/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;

/**
 * This command allows Xbox joystick to drive the robot. It is always running
 * except when interrupted by another command.
 */
public class DriveWithXbox extends Command {
  boolean usingArcadeDrive = false; 

  public DriveWithXbox() {
    requires(Robot.driveSystem);
  }

  @Override
  protected void execute() {
    if (Robot.m_oi.xbox.getBButtonPressed()) {
      usingArcadeDrive = !usingArcadeDrive;
    }

    if (usingArcadeDrive) {
      this.arcadeDrive(Robot.m_oi.getJoystick());
    }
    else {
      this.tankDrive(Robot.m_oi.getJoystick());
    }
  }

  /**
	 * Arcade drive using individual joystick axes.
	 *
	 * @param xbox Xbox style to drive in arcade
	 *    
	 */
	void arcadeDrive(XboxController xbox){
		Robot.driveSystem.arcadeDrive(xbox.getRawAxis(1), xbox.getRawAxis(0));
  }
  
/**
 * Tank drive using a Xbox joystick.
 * 
 *  @param xbox Xbox style joystick to use as the input for tank drive.
 */
void tankDrive(XboxController xbox) {
  Robot.driveSystem.tankDrive(xbox.getRawAxis(1), xbox.getRawAxis(5));
}

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.driveSystem.stop();
  }
}
