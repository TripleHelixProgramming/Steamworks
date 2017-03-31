package org.usfirst.frc.team2363.robot.subsystems;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.shooter.StopShooter;
import org.usfirst.frc.team2363.util.DrivetrainMath;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {

	private CANTalon motor1 = new CANTalon(SHOOTER_TALON);

	public static final double MAX_SPEED = 6100;
	public static int ENC_TICKS = 3;
	
	public Shooter() {
		motor1.changeControlMode(TalonControlMode.Speed);
		motor1.setF(3.15);
		motor1.setP(40);
		motor1.setI(0.01);
		motor1.setIZone(100);
		motor1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor1.configEncoderCodesPerRev(ENC_TICKS);
		motor1.enableBrakeMode(false);
		motor1.enableControl();
	}
	
	public void start() {
		motor1.enable();
	}
	
	public void stop() {
		motor1.disable();
	}
	
	public double getRPM() {
		return motor1.get() * 0.458333;
	}
	
	public double getError() {
		return motor1.getClosedLoopError();
	}
	
	public void setPower(double d) {
		motor1.set(d * 2.18);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new StopShooter());
    }
    
    public double getMotor1Current() {
    	return motor1.getOutputCurrent();
	}
}

 