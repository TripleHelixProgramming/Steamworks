package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberCommand;

import com.ctre.CANTalon;

/**
 *
 */
public class GearGrabber extends Subsystem {

    public enum GearGrabberState {
    	IN,
    	OUT,
    	OFF
    }
    
    private CANTalon motor = new CANTalon(GEAR_GRABBER_TALON);
    
    public void in() {
    	motor.set(-0.5);
    }
    
    public void out() {
    	motor.set(0.7);
    }
    
    public void off() {
    	motor.set(0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new GearGrabberCommand(GearGrabberState.OFF));
    }
}

