package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberCommand;

import com.ctre.CANTalon;

/**
 *
 */
public class GearGrabber extends Subsystem {

	// declares the different gear grabber states
    public enum GearGrabberState {
    	IN,
    	OUT,
    	OFF
    }
    
    // Talons
    private CANTalon motor = new CANTalon(GEAR_GRABBER_TALON);
    
    // Limit Switch
    private DigitalInput gearLimit = new DigitalInput(GEAR_LIMIT_CHANNEL);
    
    public void in() {
    	// sets gear grabber to rotate in at 50% speed
    	motor.set(-0.5);
    }
    
    public void out() {
    	// sets gear grabber to rotate out at 70% speed
    	motor.set(0.7);
    }
    
    public void off() {
    	// sets gear grabber not to rotate
    	motor.set(0);
    }
    
    public boolean hasGear() {
    	// reads if the gear grabber possesses a gear
    	return !gearLimit.get();
    }

    public void initDefaultCommand() {
    	// sets the default gear grabber state to off
        setDefaultCommand(new GearGrabberCommand(GearGrabberState.OFF));
    }
}

