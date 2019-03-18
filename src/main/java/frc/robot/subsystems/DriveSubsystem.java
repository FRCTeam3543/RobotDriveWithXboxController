/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * This is the DriveSubsystem or so called drivetrain or driving thingy's
 */
public class DriveSubsystem extends Subsystem {
	//	private Compressor airpusher;
	private DoubleSolenoid doubleSolenoid;
	private AnalogGyro gyro = new AnalogGyro(Config.GYRO_PORT);

	/**
	 * Subsystem Devices
	 */
	////////LEFT////////
	WPI_TalonSRX leftFrontTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_LEFT_FRONT_MOTOR_PORT);
	WPI_TalonSRX leftRearTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_LEFT_REAR_MOTOR_PORT);
	////////Right////////
	WPI_TalonSRX rightFrontTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_RIGHT_FRONT_MOTOR_PORT);
	WPI_TalonSRX rightRearTalonSRX = new WPI_TalonSRX(Config.DRIVELINE_RIGHT_REAR_MOTOR_PORT);

	////////////////////
	//////  Group //////
	////////////////////
	private final SpeedControllerGroup LeftSide = new SpeedControllerGroup(leftFrontTalonSRX, leftRearTalonSRX);
	private final SpeedControllerGroup RightSide = new SpeedControllerGroup(rightFrontTalonSRX, rightRearTalonSRX);
	
	public DifferentialDrive m_drive;

	/**
	 * DriveSubsytem Constructure
	 */
	public DriveSubsystem(){
		super();
		setName("Drive Subsystem");

		leftFrontTalonSRX.setNeutralMode(NeutralMode.Brake);
		rightFrontTalonSRX.setNeutralMode(NeutralMode.Brake);

		//left side
		leftRearTalonSRX.follow(leftFrontTalonSRX);
		//Right side
		rightRearTalonSRX.follow(rightFrontTalonSRX);

		LeftSide.setInverted(true);
		RightSide.setInverted(true);

		m_drive = new DifferentialDrive(LeftSide, RightSide);
		
		m_drive.setSafetyEnabled(false);

		doubleSolenoid = new DoubleSolenoid(Config.DRIVELINE_SOLENOID_PORT_1, Config.DRIVELINE_SOLENOID_PORT_2);

		gyro.setSensitivity(Config.GYRO_SENSITIVITY);
		gyro.calibrate();

		gyro.setName("Gyro");
		m_drive.setName("Robot Drive");
		LeftSide.setName("Left Wheels");
		RightSide.setName("Right Wheels");
		reset();
	}
	
/**
   * When other commands aren't using the drivetrain, allow tank drive with
   * the joystick.
   */
  @Override
  public void initDefaultCommand() {}

  	/**
	 * Get the value from the left stick
	 * 
	 * @param xSpeed Left side values
	 * @param zRotate
	 */

	public void arcadeDrive(double xSpeed, double zRotate, boolean squareInputs){
		m_drive.arcadeDrive(-xSpeed/4, zRotate/4, squareInputs);
	}

  /**
   * Stop the drivetrain from moving.
   */
  public void stop() {
	m_drive.stopMotor();
	}

	public void reset() {
	  	stop();
  		gyro.reset();
  		resetEncoders();
	}

	public void calibrate() {
	  	gyro.calibrate();
	}

	public void resetEncoders() {
		leftFrontTalonSRX.getSensorCollection().setQuadraturePosition(0,0);
		rightFrontTalonSRX.getSensorCollection().setQuadraturePosition(0,0);
	}

	public double getHeading() {
	  	return this.gyro.getAngle();
	}

	public void initOperatorInterface() {
		addChild(gyro);
		addChild(m_drive);
		addChild(LeftSide);
		addChild(RightSide);
		addChild(leftFrontTalonSRX.getSensorCollection());
		addChild(rightFrontTalonSRX.getSensorCollection());
		LiveWindow.add(this);
	}

	public double getLeftQuadPosition() {
  		return leftFrontTalonSRX.getSensorCollection().getQuadraturePosition() * Config.DRIVE_LEFT_QUAD_DPP;
	}

	public double getRightQuadPosition() {
  		return rightFrontTalonSRX.getSensorCollection().getQuadraturePosition() * Config.DRIVE_RIGHT_QUAD_DPP;
	}
}
