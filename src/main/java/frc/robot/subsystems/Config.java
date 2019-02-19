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

    ////// VISION //////
    // should be http://10.35.43.11/axis-cgi/mjpg/video.cgi
    public static final String CAMERA_ADDRESS = "10.35.43.11";
    public static final int CAMERA_WIDTH = 320;
    public static final int CAMERA_HEIGHT = 240;

    // Drivetrain speed controllers (No drivetrain speed controllers here!MWUHAHA)
    
    /////Lift System//////
    public static final int LIFT_MOTOR_PORT = 7;
    public static final double LIFT_SPEED_UP = 0.5;
    public static final double LIFT_STAY = 0.1;

    public static final int LIFT_ENCODER_A = 4;
    public static final int LIFT_ENCODER_B = 5;

//    public static final double LIFT_ENCODER_DPP =  1 / 162974.66; // distance-per-pulse, in radians based on 4096 pulses per cycle
    public static final double LIFT_ENCODER_DPP =  2 * Math.PI / 4096 ; // distance-per-pulse, in radians based on 4096 pulses per cycle

    ///// Drivey Motor Ports /////////
    public static final int DRIVELINE_LEFT_FRONT_MOTOR_PORT 		= 4;
    public static final int DRIVELINE_LEFT_REAR_MOTOR_PORT 			= 6;
    public static final int DRIVELINE_RIGHT_FRONT_MOTOR_PORT 		= 1;
    public static final int DRIVELINE_RIGHT_REAR_MOTOR_PORT 		= 3;

    ////////////////////////////////////
    ///////// Hatch Pneumatics /////////
    ////////////////////////////////////
    public static final int HATCH_PORT_0 = 0;
    public static final int HATCH_PORT_1 = 1;

    ///////////////////////////////////
    ///////// Ramp Pneumatics /////////
    ///////////////////////////////////
    public static final int RAMP_PORT_2 = 2;
    public static final int RAMP_PORT_3 = 3;

    public static final double DRIVE_LEFT_QUAD_DPP = 0.00003844376;    // meters per pulse, measured 2/9/19
    public static final double DRIVE_RIGHT_QUAD_DPP = -DRIVE_LEFT_QUAD_DPP;

    /////////////////////////////////
    ///////// Ball Subsytem /////////
    /////////////////////////////////
    public static final int BALL_PICKUP_MOTOR_PORT_2 = 2;
    public static final int BALL_PICKUP_MOTOR_PORT_1 = 5;
    public static final double BALL_PICKUP_MOTOR_SPEED = 0.5;
    public static final double BALL_SHOOT_MOTOR_SPEED = 1.0;

    // Digital Ports
    public static final int BALL_PICKUP_SWITCH_PORT = 1;
    public static final int LINE_SENSOR_LEFT = 2;
    public static final int LINE_SENSOR_RIGHT = 3;

    // PCM Channels
    public static final int COMPRESSOR_PORT 						= 5;
	public static final int DRIVELINE_SOLENOID_PORT_1 				= 6;
    public static final int DRIVELINE_SOLENOID_PORT_2				= 7;
    
    //Gyro
    public static final double GYRO_SENSITIVITY = 0.007;
    public static final int GYRO_PORT = 0;

    // Analog Ports
    public static final int ULTRASOUND_LEFT = 1;
    public static final int ULTRASOUND_RIGHT = 2;

	// OI Buttons etc
    public static final int BALL_PICKUP_TOGGLE_BUTTON               = 1;

    //PID's
    public static final double BALL_PICKUP_PID_KP = 1.5708;  // based on full output at pi/2 error
    public static final double BALL_PICKUP_PID_KI = 0.0;
    public static final double BALL_PICKUP_PID_KD = 0.0;
    public static final double BALL_PICKUP_ENCODER_DPP = 1 / 162974.66; // radians per pulse (4096 pulses per cycle and gear ratio of 250)

    public static final double THROTTLE_MIN = 0.5;
    public static final double RUMBLE_VALUE = 0.7;
}
