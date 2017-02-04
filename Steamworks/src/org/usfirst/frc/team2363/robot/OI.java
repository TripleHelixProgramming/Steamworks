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
	private Joystick ps4Controller;
	private Joystick operatorController;
	
	public OI() {
		//Controllers
		ps4Controller = new Joystick(PS4_PORT);
		operatorController = new Joystick(OPERATOR_PORT);
		
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
		return -ps4Controller.getRawAxis(RIGHT_STICK_X) * getTurnScaling(getThrottle());
	}
	
	public static double getTurnScaling(double x) {
		return -Math.abs(LOW_SPEED_SCALING - HIGH_SPEED_SCALING) * Math.abs(x) + LOW_SPEED_SCALING;
	}
	public double getClimberPower() {
//		return operatorController.getRawAxis(RIGHT_STICK_Y);
		if (operatorController.getRawAxis(RIGHT_STICK_Y) >= 0) {
			return 0;
		} else {
			return -Math.pow(operatorController.getRawAxis(RIGHT_STICK_Y), 2);
		}
	}
}
