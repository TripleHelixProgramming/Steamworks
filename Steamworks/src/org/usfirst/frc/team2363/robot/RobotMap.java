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
	
	//PS4 Controllers
	public static final int DRIVER_PORT = 0;
	public static final int OPERATOR_PORT = 1;
	
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
	//Gear Grabber Talons
	public static final int FRONT_LEFT_TALON_ID = 11;
	public static final int FRONT_RIGHT_TALON_ID = 13;
	public static final int REAR_LEFT_TALON_ID = 10;
	public static final int REAR_RIGHT_TALON_ID = 12;

	//Gear Grabber Subsystem
	public static final int GEAR_GRABBER_TALON = 4;
	public static final int GEAR_LIMIT_CHANNEL = 0;
	
	//Wall Subsystem
	public static final int CLIMBER_MOTOR_1 = 4;
	public static final int CLIMBER_MOTOR_2 = 5;
	public static final int WALL_SOLENOID_A = 0;
	public static final int WALL_SOLENOID_B = 7;
	public static final int WALL_TRIGGER_SOLENOID = 1;

	//Shooter Subsystem
	public static final int SHOOTER_TALON = 40;
	public static final int SHOOTER_ENCODER = 1;
	
	//Feeder Subsystem
	public static final int FEEDER_TALON = 50;

	//Solenoids
	public static final int FRONT_DROPDOWN_1 = 6;
	public static final int FRONT_DROPDOWN_2 = 1;
	public static final int REAR_DROPDOWN_1 = 5;
	public static final int REAR_DROPDOWN_2 = 2;
	public static final int GEAR_GRABBER_SOLENOID_A = 3;
	public static final int GEAR_GRABBER_SOLENOID_B = 4;
	public static final int SHIFTER_UP = 0;
	public static final int SHIFTER_DOWN = 7;
	
	//LED Ring relay port
	public static final int LIGHT_RING_RELAY = 0;
}	
