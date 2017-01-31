package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.wall.WallCommand;
/**
 *
 */
public class Wall extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public enum WallState {
		WALL_EXTEND,	//Hopper tilt wall out
		WALL_RETRACT,	//Hopper Tilt wall in
		TRIGGER_ON, 	//Hopper trigger activated
		TRIGGER_OFF,	//Hopper trigger deactivated
		CLIMBER_ON,
		CLIMBER_OFF,
		OFF
	}
	
	private DoubleSolenoid wallSolenoid = new DoubleSolenoid(WALL_SOLENOID_A, WALL_SOLENOID_B); 
 	private Solenoid triggerSolenoid = new Solenoid(WALL_TRIGGER_SOLENOID);
	private CANTalon climberMotor1 = new CANTalon(CLIMBER_MOTOR_1);
	private CANTalon climberMotor2 = new CANTalon(CLIMBER_MOTOR_2);
	
	public void wall() {
	
	}
		
	public void wallExtend() {
		wallSolenoid.set(Value.kForward);
	}
	
	public void wallRetract() {
		wallSolenoid.set(Value.kReverse);
	}
	
	public void triggerOn() {
		triggerSolenoid.set(true);
	}
	
	public void triggerOff() {
		triggerSolenoid.set(false);
	}
	
	public void climberOn() {
		climberMotor1.set(1);
		climberMotor2.set(1);
	}
	
	public void climberOff() {
		climberMotor1.set(0);
		climberMotor2.set(0);
	}
	
	public void off() {
		wallSolenoid.set(Value.kReverse);
		triggerSolenoid.set(false);
	}
	
	public boolean isWallExtented() {
		return wallSolenoid.get() ==Value.kForward;
	}
	
	public boolean isWallRetracted() {
		return wallSolenoid.get() ==Value.kReverse;
	}
	
	public boolean isTriggerOn() {
		return triggerSolenoid.get() == true;
	}
	
	public boolean isTriggerOff() {
		return triggerSolenoid.get() == false;
	}
	
	public boolean isTiltWallOff() {
		return wallSolenoid.get() == Value.kReverse;
	}
	
	public boolean isOff() {
		return ((triggerSolenoid.get() == false) && (wallSolenoid.get() == Value.kReverse));
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new WallCommand(WallState.OFF));
    }
}

