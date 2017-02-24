package org.usfirst.frc.team2363.robot.subsystems;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	private CANTalon motor1 = new CANTalon(SHOOTER_TALON);
	private Counter encoder = new Counter(SHOOTER_ENCODER);
	
	public static final double MAX_SPEED = 6100;
	private double previousRPM;
	
	public Shooter() {
		motor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	}
	
	public double getRPM() {
		if (encoder.getStopped()) {
			previousRPM = 0;
			return 0;
		}
		
		double currentRPM = Math.abs(60/encoder.getPeriod());
//		if (currentRPM > Shooter.MAX_SPEED * 2) {
		if (Math.abs(currentRPM - previousRPM) > 2000) {
			return previousRPM;
		}
		previousRPM = currentRPM;
		return currentRPM;
	}
	
	public void setPower(double d) {
		motor1.set(d);
	}
	
	public static double getEstimatedPower(double targetSpeed) {
		return targetSpeed / MAX_SPEED;
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getMotor1Current() {
    	return motor1.getOutputCurrent();
	}
}

 