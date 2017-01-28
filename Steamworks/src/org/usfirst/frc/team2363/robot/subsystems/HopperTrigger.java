package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class HopperTrigger extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public enum HopperTriggerState {
		WALL_EXTEND,	//Hopper tilt wall out
		WALL_RETRACT,	//Hopper Tilt wall in
		TRIGGER_ON, 	//Hopper trigger activated
		TRIGGER_OFF,	//Hopper trigger deactivated
		TILT_WALL_OFF,	//Wall retracted, trigger deactivated
		OFF
	}
	
	private DoubleSolenoid wallSolenoid = new DoubleSolenoid(HOPPER_TRIGGER_SOLENOID_A, HOPPER_SOLENOID_B); 
	private DoubleSolenoid triggerSolenoid = new DoubleSolenoid(HOPPER_TRIGGER_SOLENOID_C, HOPPER_SOLENOID_D);
	
	public void HopperTrigger() {
	
	}
		
	public void wallExtend() {
		wallSolenoid.set(Value.kForward);
	}
	
	public void wallRetract() {
		wallSolenoid.set(Value.kReverse);
	}
	
	public void triggerOn() {
		triggerSolenoid.set(Value.kForward);
	}
	public void triggerOff() {
		triggerSolenoid.set(Value.kReverse);
	}
	
	public boolean isWallExtented() {
		return wallSolenoid.get() ==Value.kForward;
	}
	
	public boolean isWallRetracted() {
		return wallSolenoid.get() ==Value.kReverse;
	}
	
	public boolean isTriggerOn() {
		return triggerSolenoid.get() == Value.kForward;
	}
	
	public boolean isTriggerOff() {
		return triggerSolenoid.get() == Value.kReverse;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new HopperTriggerCommand(HopperTriggerState.OFF));
    }
}

