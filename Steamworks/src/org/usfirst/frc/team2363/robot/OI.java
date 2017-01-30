package org.usfirst.frc.team2363.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.PixyCam.PixyCommand;
import org.usfirst.frc.team2363.robot.commands.drivetrain.OmniDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;

import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.StopShooter;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberRetrieve;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberStop;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.robot.subsystems.GearGrabber.GearGrabberState;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	private Joystick ps4Controller;

	public OI() {
		//Controllers
		ps4Controller = new Joystick(PS4_PORT);
		
//		new JoystickButton(ps4Controller, L2).whenPressed(new TractionDrive()); // Colson Wheels
//		new JoystickButton(ps4Controller, R2).whenPressed(new OmniDrive());  // Omni Wheels
		// Sucks in the gear
		new JoystickButton(ps4Controller, SQUARE).whenPressed(new GearGrabberRetrieve(GearGrabberState.RETRIEVE));
		new JoystickButton(ps4Controller, SQUARE).whenReleased(new GearGrabberStop());
		// Pushes out the gear

		new JoystickButton(ps4Controller, L1).whenPressed(new PIDShooterCommand());
		new JoystickButton(ps4Controller, R1).whenPressed(new StopShooter());
		new JoystickButton(ps4Controller, L2).whenPressed(new FeederCommand(true));
		new JoystickButton(ps4Controller, R2).whenPressed(new FeederCommand(false));

		new JoystickButton(ps4Controller, CIRCLE).whenPressed(new GearGrabberDelivery(GearGrabberState.DELIVER));
		new JoystickButton(ps4Controller, CIRCLE).whenReleased(new GearGrabberStop());

	}
	
	// front omni wheels
	public boolean isFrontDeployed() {
		return ps4Controller.getRawAxis(RIGHT_STICK_Y) < -0.6;
	}
	
	// back omni wheels
	public boolean isRearDeployed() {
		return ps4Controller.getRawAxis(RIGHT_STICK_Y) > 0.6;
	}
	
	// speed
	public double getThrottle () {
		return -ps4Controller.getRawAxis(LEFT_STICK_Y);

	}
	
	// turn angle
	public double getTurn() {
		return -ps4Controller.getRawAxis(RIGHT_STICK_X);
	}
}
