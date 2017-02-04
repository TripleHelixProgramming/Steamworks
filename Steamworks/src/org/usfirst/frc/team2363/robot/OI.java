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
import org.usfirst.frc.team2363.robot.commands.lightRing.LightRingBoth;
import org.usfirst.frc.team2363.robot.commands.lightRing.LightRingGreen;
import org.usfirst.frc.team2363.robot.commands.lightRing.LightRingOff;
import org.usfirst.frc.team2363.robot.commands.lightRing.LightRingRed;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.robot.subsystems.GearGrabber.GearGrabberState;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Joystick driverController;
	private Joystick operatorController;

	public OI() {
		//Controllers
		driverController = new Joystick(DRIVER_PORT);
		operatorController = new Joystick(OPERATOR_PORT);
		
//		new JoystickButton(ps4Controller, L2).whenPressed(new TractionDrive()); // Colson Wheels
//		new JoystickButton(ps4Controller, R2).whenPressed(new OmniDrive());  // Omni Wheels
		
		//shooter
		//Turns on the shooter
		new JoystickButton(driverController, L1).whenPressed(new PIDShooterCommand());
		//Turns off the shooter
		new JoystickButton(driverController, R1).whenPressed(new StopShooter());
		//Turns on the shooter feeder
		new JoystickButton(driverController, L2).whenPressed(new FeederCommand(true));
		//Turns off the shooter feeder
		new JoystickButton(driverController, R2).whenPressed(new FeederCommand(false));
		
		//gear grabber
		//Sucks in the gear while square is being held
		new JoystickButton(driverController, SQUARE).whenPressed(new GearGrabberRetrieve(GearGrabberState.RETRIEVE));
		new JoystickButton(driverController, SQUARE).whenReleased(new GearGrabberStop());
		//Pushes out the gear while circle is being held
		new JoystickButton(driverController, CIRCLE).whenPressed(new GearGrabberDelivery(GearGrabberState.DELIVER));
		new JoystickButton(driverController, CIRCLE).whenReleased(new GearGrabberStop());
		
		//light ring
		//Turns the light to green while triangle is being held
		new JoystickButton(operatorController, TRIANGLE).whenPressed(new LightRingGreen());
		new JoystickButton(operatorController, TRIANGLE).whenReleased(new LightRingOff());
		//Turns the light to red while circle is being held
		new JoystickButton(operatorController, CIRCLE).whenPressed(new LightRingRed());
		new JoystickButton(operatorController, CIRCLE).whenReleased(new LightRingOff());
		//Turns the light to red and green while square is being held
		new JoystickButton(operatorController, SQUARE).whenPressed(new LightRingBoth());
		new JoystickButton(operatorController, SQUARE).whenReleased(new LightRingOff());
	}
	
	// front omni wheels
	public boolean isFrontDeployed() {
		return driverController.getRawAxis(RIGHT_STICK_Y) < -0.6;
	}
	
	// back omni wheels
	public boolean isRearDeployed() {
		return driverController.getRawAxis(RIGHT_STICK_Y) > 0.6;
	}
	
	// speed
	public double getThrottle () {
		return -driverController.getRawAxis(LEFT_STICK_Y);
	}
	
	// turn angle
	public double getTurn() {
		return -driverController.getRawAxis(RIGHT_STICK_X) * getTurnScaling(getThrottle());
	}
	
	public static double getTurnScaling(double x) {
		return -Math.abs(LOW_SPEED_SCALING - HIGH_SPEED_SCALING) * Math.abs(x) + LOW_SPEED_SCALING;
	}
}
