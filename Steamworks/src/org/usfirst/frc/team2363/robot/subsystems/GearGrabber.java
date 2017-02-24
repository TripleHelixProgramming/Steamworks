package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberStop;

import com.ctre.CANTalon;

/**
 *
 */
public class GearGrabber extends Subsystem {

    // Talons
    private CANTalon motor = new CANTalon(GEAR_GRABBER_TALON);
    
    private DoubleSolenoid solenoid = new DoubleSolenoid(PCM_0, GEAR_GRABBER_UP_SOLENOID, GEAR_GRABBER_DOWN_SOLENOID);
    
    // Limit Switch
    private AnalogInput gearLimit = new AnalogInput(GEAR_LIMIT_CHANNEL);
    
    public void in() {
    	// sets gear grabber to rotate in at 50% speed
    	motor.set(-0.75);
    }
    
    public void out() {
    	// sets gear grabber to rotate out at 40% speed
    	motor.set(0.75);
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
    	if (getGearLimit().getValue() > 300) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public double getOutputCurrent() {
    	return motor.getOutputCurrent();
    }
    
    public boolean isOverCurrent() {
    	//detects if the gear grabber goes over 30 amps to prevent damage to the motor
    	if (getOutputCurrent() > 20) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public void initDefaultCommand() {
    	// sets the default gear grabber state to off
        setDefaultCommand(new GearGrabberStop());
    }

	public AnalogInput getGearLimit() {
		return gearLimit;
	}

}

