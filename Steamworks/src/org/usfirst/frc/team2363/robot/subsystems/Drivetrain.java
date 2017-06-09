package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

//  import org.usfirst.frc.team2363.robot.commands.drivetrain.JoystickDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;
import org.usfirst.frc.team2363.util.DrivetrainMath;

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
	
	private static AHRS ahrs;
	
	// Drivetrain
	private RobotDrive robotDrive = new RobotDrive(rearLeft, rearRight);
	
	private static final int ENCODER_TICKS = 120;
	private static final double GEAR_RATIO = 50.0 / 34.0;
	private static final int MAX_RPM = 415;
	
	public Drivetrain() {
		robotDrive.setSafetyEnabled(false);
		
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
//		rearLeft.setF(DrivetrainMath.fGain(ENCODER_TICKS, GEAR_RATIO, MAX_RPM));
		rearLeft.setF(0.79);
		rearLeft.setP(1);
		rearLeft.setD(0.001);
		rearLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearLeft.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(ENCODER_TICKS, GEAR_RATIO));
		rearLeft.reverseSensor(true);
		
		rearRight.changeControlMode(TalonControlMode.PercentVbus);
//		rearRight.setF(DrivetrainMath.fGain(ENCODER_TICKS, GEAR_RATIO, MAX_RPM));
		rearRight.setF(0.79);
		rearRight.setP(1);
		rearRight.setD(0.001);
		rearRight.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rearRight.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(ENCODER_TICKS, GEAR_RATIO));
		rearRight.reverseSensor(true);
		
		frontLeft.changeControlMode(TalonControlMode.Follower);
		frontLeft.set(rearLeft.getDeviceID());
		frontLeft.enableBrakeMode(true);
		
		frontRight.changeControlMode(TalonControlMode.Follower);
		frontRight.set(rearRight.getDeviceID());
		frontRight.enableBrakeMode(true);
		
	      // Instantiate the NavMXP Gyro
	      try {
	          ahrs = new AHRS(SPI.Port.kMXP); 
	      } catch (RuntimeException ex ) {
	          DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	      }

	}
	
	public void arcadeDrive(double throttle, double turn) {
		robotDrive.arcadeDrive(throttle, turn);
	}
	
	public void tankDrive(double left, double right) {
		robotDrive.tankDrive(left, right, false);
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
		rearLeft.enableBrakeMode(true);
		rearRight.enableBrakeMode(true);
	}
	
	public void setUpManualControl() {
		rearLeft.changeControlMode(TalonControlMode.PercentVbus);
		rearRight.changeControlMode(TalonControlMode.PercentVbus);
		rearLeft.enableBrakeMode(false);
		rearRight.enableBrakeMode(false);
	}
	
	public void setSpeeds(double leftSpeed, double rightSpeed) {
//		rearLeft.set(-(leftSpeed / MAX_RPM) * 100);
//		rearRight.set((rightSpeed / MAX_RPM) * 100);
		rearLeft.set(-leftSpeed);
		rearRight.set(rightSpeed);
	}
	
	public double getAngle() {
		return ahrs.getAngle();
	}
	
	public void resetAngle() {
		ahrs.zeroYaw();
	}
	public CANTalon getRearLeft() {
		return rearLeft;
	}
	
	public CANTalon getRearRight() {
		return rearRight;
	}
	
	public CANTalon getLeft() {
		return frontLeft;
	}
	
	public CANTalon getRight() {
		return frontRight;
	}

	public String getShifters() {
		if (shifters.get() == Value.kForward) {
			return "High Gear";
		} else {
			return "Low Gear";
		}
	}
	
	public boolean getOmniState() {
		return Omni.get();
	}
	
	public double getLeftError() {
		return rearLeft.getError();
	}
	
	public double getRightError() {
		return rearRight.getError();
	}
}
