/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

public class Config {
    ////// OPERATOR INTERFACE //////
    public static final int XBOX_PORT  = 0;
    // Drivetrain speed controllers (No drivetrain speed controllers here!MWUHAHA)

        ///// Drivey Motor Ports /////////
        public static final int DRIVELINE_LEFT_FRONT_MOTOR_PORT 		= 4;
        public static final int DRIVELINE_LEFT_REAR_MOTOR_PORT 			= 6;
        public static final int DRIVELINE_RIGHT_FRONT_MOTOR_PORT 		= 1;
        public static final int DRIVELINE_RIGHT_REAR_MOTOR_PORT 		= 3;


    public static final double DRIVE_LEFT_QUAD_DPP = 0.00003844376;    // meters per pulse, measured 2/9/19
    public static final double DRIVE_RIGHT_QUAD_DPP = -DRIVE_LEFT_QUAD_DPP;

    public static final int BALL_PICKUP_MOTOR_PORT                  = 5;
    public static final double BALL_PICKUP_MOTOR_SPEED              = 0.5;

    // Digital Ports
    public static final int BALL_PICKUP_SWITCH_PORT = 1;
    public static final int LINE_SENSOR_LEFT = 2;
    public static final int LINE_SENSOR_RIGHT = 3;

    // PCM Channels
    public static final int COMPRESSOR_PORT 						= 5;
	public static final int DRIVELINE_SOLENOID_PORT_1 				= 6;
	public static final int DRIVELINE_SOLENOID_PORT_2				= 7;
    public static final double GYRO_SENSITIVITY = 0.007;
    public static final int GYRO_PORT = 0;

    // Analog Ports
    public static final int ULTRASOUND_LEFT = 1;
    public static final int ULTRASOUND_RIGHT = 2;

	// OI Buttons etc
    public static final int BALL_PICKUP_TOGGLE_BUTTON               = 1;

    // Drivetrain Encoder Ports
    // Intake speed controllers
    // Intake Ultrasonic Ports

}
