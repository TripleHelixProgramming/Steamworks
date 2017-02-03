package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

//  import org.usfirst.frc.team2363.robot.commands.drivetrain.JoystickDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;

/**
 *
 */
public class Drivetrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//  Talons
	private CANTalon frontLeft = new CANTalon(FRONT_LEFT_TALON_ID);
	private CANTalon frontRight = new CANTalon(FRONT_RIGHT_TALON_ID);
	private CANTalon rearLeft = new CANTalon(REAR_LEFT_TALON_ID);
	private CANTalon rearRight = new CANTalon(REAR_RIGHT_TALON_ID);
	
	// Solenoids
	private DoubleSolenoid frontOmni = new DoubleSolenoid(FRONT_DROPDOWN_1, FRONT_DROPDOWN_2);
	private DoubleSolenoid rearOmni = new DoubleSolenoid(REAR_DROPDOWN_1, REAR_DROPDOWN_2);
	
	// Drivetrain
	private RobotDrive robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	
	public Drivetrain() {
	}
	
 /*   public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new JoystickDrive()); 

    }*/
	public void arcadeDrive(double throttle, double turn) {
		// drives using speed and turn angle given from controller
		robotDrive.arcadeDrive(throttle, turn);
	}
	
	public void deployFront() {
		// deploys the front two omniwheels
		frontOmni.set(Value.kForward);
	}
	
	public void retractFront() {
		// retracts the front two omniwheels
		frontOmni.set(Value.kReverse);
	}
	
	public void deployRear() {
		// deploys the back two omniwheels
		rearOmni.set(Value.kForward);
	}
	
	public void retractRear() {
		// retracts the back two omniwheels
		rearOmni.set(Value.kReverse);
	}
	
	public void driveMotors(double lSpeed, double rSpeed) {
		frontLeft.set(lSpeed);
		rearLeft.set(lSpeed);
		frontRight.set(rSpeed);
		rearRight.set(rSpeed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// sets the default drive mode to colson drive
		setDefaultCommand(new TractionDrive());
	}
	
	public void setUpAutoControl() {
		frontLeft.changeControlMode(TalonControlMode.Speed);
		frontRight.changeControlMode(TalonControlMode.Speed);
		rearLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(frontLeft.getDeviceID());
		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(frontRight.getDeviceID());
	}
	
	public void setUpManualControl() {
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void setSpeeds(double leftSpeed, double rightSpeed) {
		frontLeft.set(leftSpeed);
		frontRight.set(rightSpeed);
	}
}
