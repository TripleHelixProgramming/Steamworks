package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

//  import org.usfirst.frc.team2363.robot.commands.drivetrain.JoystickDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;
import org.usfirst.frc.team2363.util.DrivetrainMath;

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
	private Solenoid Omni = new Solenoid(PCM_0, DROP_DOWN);
	private DoubleSolenoid shifters = new DoubleSolenoid(PCM_0, SHIFTER_UP, SHIFTER_DOWN);
	
	// Drivetrain
	private RobotDrive robotDrive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);
	
	//Drivetrain Math
	final double gearRatio = 34.0 /50.0;
	final int maxRPM = 385;
	final int encoderTicks = 120;
	
	public Drivetrain() {
		frontLeft.changeControlMode(TalonControlMode.Follower);
		frontLeft.set(rearLeft.getDeviceID());
		rearLeft.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(encoderTicks, gearRatio));
		rearLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		frontRight.changeControlMode(TalonControlMode.Follower);
		frontRight.set(rearRight.getDeviceID());
		rearRight.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(encoderTicks, gearRatio));
		rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearRight.setF(DrivetrainMath.fGain(encoderTicks, gearRatio, maxRPM));
	}
	
	public void arcadeDrive(double throttle, double turn) {
		// drives using speed and turn angle given from controller
		robotDrive.arcadeDrive(throttle, turn);
	}
	
	public void deployOmnis() {
		// deploy front & back Omni wheels
		Omni.set(true);
	}
	
	public void retractOmnis() {
		// retract front & back Omni wheels
		Omni.set(false);
	}
	
	public void shiftUp() {
		shifters.set(Value.kForward);
	}
	
	public void shiftDown() {
		shifters.set(Value.kReverse);
	}
	
	public void driveMotors(double lSpeed, double rSpeed) {
		rearLeft.set(lSpeed);
		rearRight.set(rSpeed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// sets the default drive mode to colson drive
		setDefaultCommand(new TractionDrive());
	}
	
	public void setUpAutoControl() {
		rearLeft.changeControlMode(TalonControlMode.Speed);
		rearRight.changeControlMode(TalonControlMode.Speed);
	}
	
	public void setUpManualControl() {
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void setSpeeds(double leftSpeed, double rightSpeed) {
		rearLeft.set(leftSpeed);
		rearRight.set(rightSpeed);
	}
}
