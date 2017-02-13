package org.usfirst.frc.team2363.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.drivetrain.OmniDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.ShiftCommand;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;

import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.StopShooter;
import org.usfirst.frc.team2363.robot.commands.wall.WallClimber;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerExtend;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberRetrieve;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberStop;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;

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
		
		//shooter
		//Turns on the shooter
		new JoystickButton(operatorController, R1).whenPressed(new PIDShooterCommand());
		//Turns off the shooter
		new JoystickButton(operatorController, L1).whenPressed(new StopShooter());
		//Turns on the shooter feeder while square is being held
		new JoystickButton(operatorController, R2).whenPressed(new FeederCommand(true));
		new JoystickButton(operatorController, R2).whenReleased(new FeederCommand(false));
		//Aiming
		//new JoystickButton(ps4Controller, PS).whenPressed(new InsertCommandHere());
		
		//gear grabber
		//Sucks in the gear while square is being held
		new JoystickButton(operatorController, SQUARE).whenPressed(new GearGrabberRetrieve());
		new JoystickButton(operatorController, SQUARE).whenReleased(new GearGrabberStop());
		new JoystickButton(driverController, SHARE).whenPressed(new GearGrabberRetrieve());
		new JoystickButton(driverController, SHARE).whenReleased(new GearGrabberStop());
		//Pushes out the gear while circle is being held
		new JoystickButton(operatorController, CIRCLE).whenPressed(new GearGrabberDelivery());
		new JoystickButton(operatorController, CIRCLE).whenReleased(new GearGrabberStop());
		new JoystickButton(driverController, OPTIONS).whenPressed(new GearGrabberDelivery());
		new JoystickButton(driverController, OPTIONS).whenReleased(new GearGrabberStop());
		new JoystickButton(driverController, CIRCLE).whenPressed(new GearGrabberDelivery());
		new JoystickButton(driverController, CIRCLE).whenReleased(new GearGrabberStop());
		
		//Fuel Intake
		//new JoystickButton(operatorController, X).whenPressed(new InsertCommandHere());

		//Drivetrain controls
		//Turns on Omni Drive
		new JoystickButton(driverController, L1).whenPressed(new OmniDrive());
		//Turns on Traction Drive
		new JoystickButton(driverController, R1).whenPressed(new TractionDrive());
		//Low gear
		new JoystickButton(driverController, L2).whenPressed(new ShiftCommand(false));
		//High gear
		new JoystickButton(driverController, R2).whenPressed(new ShiftCommand(true));
		
		//Climber activate
		new JoystickButton(operatorController, R3).toggleWhenPressed(new WallClimber());
		
		//Hopper Trigger actuate
		new JoystickButton(operatorController, TRIANGLE).toggleWhenPressed(new WallTriggerExtend());
	}
	
	// omni wheels
	public boolean isOmnisDeployed() {
		return(driverController.getRawButton(L1) == true);
	}
	
	// speed
	public double getThrottle () {
		return driverController.getRawAxis(LEFT_STICK_Y);
	}
	
	// turn angle
	public double getTurn() {
		return driverController.getRawAxis(RIGHT_STICK_X) * getTurnScaling(getThrottle());
	}
	
	public static double getTurnScaling(double x) {
		return -Math.abs(LOW_SPEED_SCALING - HIGH_SPEED_SCALING) * Math.abs(x) + LOW_SPEED_SCALING;
	}
	
	public double getClimberPower() {
		//  return operatorController.getRawAxis(RIGHT_STICK_Y);
		if (operatorController.getRawAxis(RIGHT_STICK_Y) >= 0) {
			return 0;
		} else { 
			return -Math.pow(operatorController.getRawAxis(RIGHT_STICK_Y), 2);
		}
	}
	
	 /**
	  * Gets the input from the operator d-pad
	  * @return Angle the d-pad is being pressed
	  */
	public int getOeratorPOV() {
		return operatorController.getPOV();
	}
}
