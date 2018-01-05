package org.usfirst.frc.team2363.robot.subsystems;

import static org.usfirst.frc.team2363.robot.RobotMap.CLIMBER_MOTOR_LEFT;
import static org.usfirst.frc.team2363.robot.RobotMap.CLIMBER_MOTOR_RIGHT;
import static org.usfirst.frc.team2363.robot.RobotMap.PCM_0;
import static org.usfirst.frc.team2363.robot.RobotMap.WALL_EXTEND_SOLENOID;
import static org.usfirst.frc.team2363.robot.RobotMap.WALL_RETRACT_SOLENOID;
import static org.usfirst.frc.team2363.robot.RobotMap.WALL_TRIGGER_SOLENOID;

import org.usfirst.frc.team2363.robot.commands.wall.WallOff;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 *
 */
public class Wall extends Subsystem {

	private int stalledCurrent = 41;
	private DoubleSolenoid wallSolenoid = new DoubleSolenoid(PCM_0, WALL_EXTEND_SOLENOID, WALL_RETRACT_SOLENOID);
 	private Solenoid triggerSolenoid = new Solenoid(PCM_0, WALL_TRIGGER_SOLENOID);

	private CANTalon climberMotor1 = new CANTalon(CLIMBER_MOTOR_LEFT);
	private CANTalon climberMotor2 = new CANTalon(CLIMBER_MOTOR_RIGHT);

	public void wall() {
	
	}
		
	public void extend() {
		wallSolenoid.set(Value.kForward);
	}
	
	public void retract() {
		wallSolenoid.set(Value.kReverse);
	}
	
	public void triggerExtend() {
		triggerSolenoid.set(true);
	}
	
	public boolean isRetracted() {
		return wallSolenoid.get() == Value.kReverse;
	}
	
	public void triggerRetract() {
		triggerSolenoid.set(false);
	}
	
	public void setClimber(double power) {
		climberMotor1.set(power);
		climberMotor2.set(-power);
	}
	
	public void off() {
		triggerRetract();
		retract();
	}
	
	public boolean isClimberStalled() {
		return ((climberMotor1.getOutputCurrent() > stalledCurrent) || (climberMotor2.getOutputCurrent() > stalledCurrent));
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new WallOff());
    }
    
    public double getMotor1Current() {
    	return climberMotor1.getOutputCurrent();
	}
 
	public double getMotor2Current() {
		return climberMotor2.getOutputCurrent();
	}
}

