/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.DriveWithXbox;

/**
 * This is the DriveSubsystem or so called drivetrain or driving thingy's
 */
public class DriveSubsystem extends Subsystem {
	//	private Compressor airpusher;
	private DoubleSolenoid doubleSolenoid; 

	/**
	 * Subsystem Devices
	 */
	////////LEFT////////
	WPI_TalonSRX leftFrontTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_LEFT_FRONT_MOTOR_PORT);
	WPI_TalonSRX leftMiddleTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_LEFT_MIDDLE_MOTOR_PORT);
	WPI_TalonSRX leftRearTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_LEFT_REAR_MOTOR_PORT);
	////////Right////////
	WPI_TalonSRX rightFrontTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_RIGHT_FRONT_MOTOR_PORT);
	WPI_TalonSRX rightMiddleTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_RIGHT_MIDDLE_MOTOR_PORT);
	WPI_TalonSRX rightRearTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_RIGHT_REAR_MOTOR_PORT);

	////////////////////
	//////  Group //////
	////////////////////
	private final SpeedControllerGroup LeftSide = new SpeedControllerGroup(leftFrontTalonSRX, leftMiddleTalonSRX, leftRearTalonSRX);
	private final SpeedControllerGroup RightSide = new SpeedControllerGroup(rightFrontTalonSRX, rightMiddleTalonSRX, rightRearTalonSRX);
	
	private final DifferentialDrive m_drive;

	/**
	 * DriveSubsytem Constructure
	 */
	public DriveSubsystem(){
		super();

		leftFrontTalonSRX.setNeutralMode(NeutralMode.Brake);
		rightFrontTalonSRX.setNeutralMode(NeutralMode.Brake);

		//left side
		leftMiddleTalonSRX.follow(leftFrontTalonSRX);
		leftRearTalonSRX.follow(leftFrontTalonSRX);
		//Right side
		rightMiddleTalonSRX.follow(rightFrontTalonSRX);
		rightRearTalonSRX.follow(rightFrontTalonSRX);

		LeftSide.setInverted(true);
		RightSide.setInverted(true);

		m_drive = new DifferentialDrive(LeftSide, RightSide);
		
		m_drive.setSafetyEnabled(false);

		doubleSolenoid = new DoubleSolenoid(Config.DRIVELINE_SOLENOID_PORT_1, Config.DRIVELINE_SOLENOID_PORT_2);
	}
	
/**
   * When other commands aren't using the drivetrain, allow tank drive with
   * the joystick.
   */
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveWithXbox());
	}

  /**
   * Tank drive using individual joystick axes.
   *
   * @param leftAxis Left sides value
   * @param rightAxis Right sides value
   */
  public void tankDrive(double leftAxis, double rightAxis) {
    m_drive.tankDrive(leftAxis, rightAxis);
  }

  	/**
	 * Get the value from the left stick
	 * 
	 * @param xSpeed Left side values
	 * @param zRotate
	 */
	public void arcadeDrive(double xSpeed, double zRotate){
		m_drive.arcadeDrive(xSpeed, zRotate, true);
	}

  /**
   * Stop the drivetrain from moving.
   */
  public void stop() {
	m_drive.stopMotor();
	}
}
