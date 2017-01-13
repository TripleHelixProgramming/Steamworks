package org.usfirst.frc.team2363.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static final int FRONT_LEFT_TALON_ID = 1;
	public static final int FRONT_RIGHT_TALON_ID = 3;
	public static final int REAR_LEFT_TALON_ID = 0;
	public static final int REAR_RIGHT_TALON_ID = 2;
	
	//Controllers
	public static final int PS4_PORT = 0;
	
	//PS4 joystick axis
	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_X = 2;
	public static final int RIGHT_STICK_Y = 5;
	
	public static final int L2 = 7;
	public static final int R2 = 8;
	
	//Solenoids
	public static final int FRONT_DROPDOWN_1 = 6;
	public static final int FRONT_DROPDOWN_2 = 1;
	public static final int REAR_DROPDOWN_1 = 5;
	public static final int REAR_DROPDOWN_2 = 2;
}
