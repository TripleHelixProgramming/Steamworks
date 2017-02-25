package org.usfirst.frc.team2363.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static final double HIGH_SPEED_SCALING = 0.55;
	public static final double LOW_SPEED_SCALING = 0.9;
	
	// Pixy Info & Calibration for Field Sides
	public static final double BLUE_X_OFFSET = 213;		// Set these to calibrate the Pixy Camera for each side of field
	public static final double RED_X_OFFSET = 139;		// Set these to calibrate the Pixy Camera for each side of field
	
	public static final double SCREEN_WIDTH = 320;
	public static final double HORIZONTAL_ANGLE = 75;
	public static final double REDSIDE_OFFSET = ((RED_X_OFFSET / SCREEN_WIDTH)* HORIZONTAL_ANGLE) - (HORIZONTAL_ANGLE/2);
	public static final double BLUESIDE_OFFSET = ((BLUE_X_OFFSET / SCREEN_WIDTH)* HORIZONTAL_ANGLE) - (HORIZONTAL_ANGLE/2);
	
	//PS4 Controllers
	public static final int DRIVER_PORT = 0;
	public static final int OPERATOR_PORT = 1;
	public static final int DRIVER_RUMBLE_PORT = 4;
	public static final int OPERATOR_RUMBLE_PORT =5;
	
	//PS4 joystick axis
	public static final int LEFT_STICK_X = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_X = 2;
	public static final int LEFT_TRIGGER = 3;
	public static final int RIGHT_TRIGGER = 4;
	public static final int RIGHT_STICK_Y = 5;
		
	//PS4 Buttons
	public static final int SQUARE = 1;
	public static final int X = 2;
	public static final int CIRCLE = 3;
	public static final int TRIANGLE = 4;
	public static final int L1 = 5;
	public static final int R1 = 6;
	public static final int L2 = 7;
	public static final int R2 = 8;
	public static final int SHARE = 9;
	public static final int OPTIONS = 10;
	public static final int L3 = 11;
	public static final int R3 = 12;
	public static final int PS = 13;
	public static final int TOUCHPAD = 14;
	
	//Drivetrain Talons
	public static final int FRONT_LEFT_TALON_ID = 11;
	public static final int FRONT_RIGHT_TALON_ID = 13;
	public static final int REAR_LEFT_TALON_ID = 10;
	public static final int REAR_RIGHT_TALON_ID = 12;

	//Gear Grabber Subsystem
	public static final int GEAR_GRABBER_TALON = 30;
	public static final int GEAR_LIMIT_CHANNEL = 1;
	
	//Wall Subsystem
	public static final int CLIMBER_MOTOR_LEFT = 60;
	public static final int CLIMBER_MOTOR_RIGHT = 61;

	//Shooter Subsystem
	public static final int SHOOTER_TALON = 40;
	public static final int SHOOTER_ENCODER = 0;
	
	//Feeder Subsystem
	public static final int SERIALIZER_TALON = 50;
	public static final int FEEDER_TALON = 51;

	//Solenoids
	public static final int DROP_DOWN = 3;
	public static final int GEAR_GRABBER_UP_SOLENOID = 2;
	public static final int GEAR_GRABBER_DOWN_SOLENOID = 5;
	public static final int GEAR_GRABBER_PLATE = 0;
	public static final int SHIFTER_UP = 1; //unknown if ports are reversed
	public static final int SHIFTER_DOWN = 6; //^^^
	public static final int WALL_RETRACT_SOLENOID = 0;
	public static final int WALL_EXTEND_SOLENOID = 7;
	public static final int WALL_TRIGGER_SOLENOID = 4;
	
	//LED Ring relay port
	public static final int LIGHT_RING_RELAY = 0;
	
	//PCMs Currently using PCM_0 but is ready for use in the event that we could need 2 PCMs
	public static final int PCM_0 = 0;
	public static final int PCM_1 = 1;
}	
