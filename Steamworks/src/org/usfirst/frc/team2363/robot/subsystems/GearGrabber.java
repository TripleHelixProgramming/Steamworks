package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
    	RETRIEVE,
    	DELIVER, 
    	OFF
    }
    
    // Talons
    private CANTalon motor = new CANTalon(GEAR_GRABBER_TALON);
    
    private DoubleSolenoid solenoid = new DoubleSolenoid(GEAR_GRABBER_SOLENOID_A, GEAR_GRABBER_SOLENOID_B);
    
    // Limit Switch
    private DigitalInput gearLimit = new DigitalInput(GEAR_LIMIT_CHANNEL);
    
    public void in() {
    	// sets gear grabber to rotate in at 50% speed
    	motor.set(-0.5);
    }
    
    public void out() {
    	// sets gear grabber to rotate out at 40% speed
    	motor.set(0.4);
    }
    
    public void off() {
    	// sets gear grabber not to rotate
    	motor.set(0);
    }
    
    public void up() {
    	solenoid.set(Value.kForward);
    }
   
    public void down() {
    	solenoid.set(Value.kReverse);
    }
    
    public boolean isUp() {
    	return solenoid.get() == Value.kForward;
    }
    
    public boolean isDown() {
    	return solenoid.get() == Value.kReverse;
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

