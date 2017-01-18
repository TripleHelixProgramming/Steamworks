package org.usfirst.frc.team2363.robot.subsystems;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	private CANTalon motor1 = new CANTalon(SHOOTER_TALON);
	private Counter encoder = new Counter(SHOOTER_ENCODER);
	
	private static final double SPEED = 1825;
	private static final double MAX_SPEED = 5310;
	private static final double P = 0.000001;
	private static final double CONVERTED_SPEED = 60 / SPEED;
	
//	private boolean running;
	
	public Shooter() {
//		encoder.setSamplesToAverage(12);
//		encoder.setDistancePerPulse(1.0/120);
		motor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
	}
	
	public void on() {
//		running = true;
		SmartDashboard.putBoolean("ShooterState", true);
//		DriverStation.reportError("ON", false);
		//    	motor.set(-1);
	}

	public void off() {
//		running = false;
		SmartDashboard.putBoolean("ShooterState", true);
//		DriverStation.reportError("OFF", false);
		//    	motor.set(0);
	}
	
	public double getRPM() {
		if (encoder.getStopped()) {
			return 0;
		}
		return Math.abs(1/encoder.getPeriod() * 60.0);
//		return Math.abs(motor1.getEncVelocity() / 13.66);
	}
	
	public double getError() {
		return getRPM() - SPEED;
	}
	
	public double getOutput() {
		return (SPEED/MAX_SPEED) + (P * getError());
	}
	
/*	private class BangBang extends Thread {

		@Override
		public void run() {
			while (true) {
//				DriverStation.reportError("" + encoder.getPeriod(), false);
    			if ((encoder.getPeriod() > CONVERTED_SPEED || encoder.getStopped()) && running) {
//				if (running) {
					motor1.set(1);
				} else {
					motor1.set(0);
				}
				try {
					sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	} */

	public void setPower(double d) {
		motor1.set(d);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

