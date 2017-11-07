package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.Robot;
//  import org.usfirst.frc.team2363.robot.commands.drivetrain.JoystickDrive;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;
import org.usfirst.frc.team2363.util.DrivetrainMath;

public class Drivetrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	//  Talons
	//  Talons
	private CANTalon frontLeft = new CANTalon(FRONT_LEFT_TALON_ID);
	private CANTalon middleLeft = new CANTalon(MIDDLE_LEFT_TALON_ID);
	private CANTalon rearLeft = new CANTalon(REAR_LEFT_TALON_ID);

	private CANTalon frontRight = new CANTalon(FRONT_RIGHT_TALON_ID);
	private CANTalon middleRight = new CANTalon(MIDDLE_RIGHT_TALON_ID);
	private CANTalon rearRight = new CANTalon(REAR_RIGHT_TALON_ID);

	// Solenoids
	private Solenoid omni = new Solenoid(PCM_0, DROP_DOWN);
	private DoubleSolenoid shifters = new DoubleSolenoid(PCM_0, SHIFTER_UP, SHIFTER_DOWN);

	private static AHRS ahrs;

	// Drivetrain
	private RobotDrive robotDrive = new RobotDrive(frontLeft, frontRight);

	private static final int ENCODER_TICKS = 4096;
	private static final double GEAR_RATIO = 1;
	private static final int MAX_RPM = 735;

	public Drivetrain() {

		Robot.LOG.addSource("LEFT1 Current", frontLeft, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		Robot.LOG.addSource("LEFT2 Current", middleLeft, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		Robot.LOG.addSource("LEFT3 Current", rearLeft, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		
		Robot.LOG.addSource("LEFT1 Voltage", frontLeft, f -> "" + ((CANTalon)(f)).getOutputVoltage());
		Robot.LOG.addSource("LEFT2 Voltage", middleLeft, f -> "" + ((CANTalon)(f)).getOutputVoltage());
		Robot.LOG.addSource("LEFT3 Voltage", rearLeft, f -> "" + ((CANTalon)(f)).getOutputVoltage());

		Robot.LOG.addSource("RIGHT1 Current", frontRight, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		Robot.LOG.addSource("RIGHT2 Current", middleRight, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		Robot.LOG.addSource("RIGHT3 Current", rearRight, f -> "" + ((CANTalon)(f)).getOutputCurrent());
		
		Robot.LOG.addSource("RIGHT1 Voltage", frontRight, f -> "" + ((CANTalon)(f)).getOutputVoltage());
		Robot.LOG.addSource("RIGHT2 Voltage", middleRight, f -> "" + ((CANTalon)(f)).getOutputVoltage());
		Robot.LOG.addSource("RIGHT3 Voltage", rearRight, f -> "" + ((CANTalon)(f)).getOutputVoltage());
		
		Robot.LOG.addSource("Left Drivetrain Speed", frontLeft, f -> "" + ((CANTalon)(f)).getSpeed());
		Robot.LOG.addSource("Right Drivetrain Speed", frontRight, f -> "" + ((CANTalon)(f)).getSpeed());
		
		Robot.LOG.addSource("Drop Downs", omni, f -> "" + ((Solenoid)f).get());

		robotDrive.setSafetyEnabled(false);

		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontLeft.setVoltageRampRate(30);
		//		frontLeft.setF(DrivetrainMath.fGain(ENCODER_TICKS, GEAR_RATIO, MAX_RPM));
		frontLeft.setF(0.20388);
		frontLeft.setP(0.05);
		// 		frontLeft.setD(0.001);
		frontLeft.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frontLeft.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(ENCODER_TICKS, GEAR_RATIO));
		//		frontLeft.reverseSensor(true);

		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.setVoltageRampRate(30);
		//		frontRight.setF(DrivetrainMath.fGain(ENCODER_TICKS, GEAR_RATIO, MAX_RPM));
		frontRight.setF(0.20388);
		frontRight.setP(0.05);
		//		frontRight.setD(0.001);
		frontRight.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		frontRight.configEncoderCodesPerRev(DrivetrainMath.ticksPerWheelRotation(ENCODER_TICKS, GEAR_RATIO));
		frontRight.reverseSensor(true);

		middleLeft.changeControlMode(TalonControlMode.Follower);
		middleLeft.set(frontLeft.getDeviceID());
		middleLeft.enableBrakeMode(true);

		middleRight.changeControlMode(TalonControlMode.Follower);
		middleRight.set(frontRight.getDeviceID());
		middleRight.enableBrakeMode(true);

		rearLeft.changeControlMode(TalonControlMode.Follower);
		rearLeft.set(frontLeft.getDeviceID());
		rearLeft.enableBrakeMode(true);

		rearRight.changeControlMode(TalonControlMode.Follower);
		rearRight.set(frontRight.getDeviceID());
		rearRight.enableBrakeMode(true);
		// Instantiate the NavMXP Gyro
		try {
			ahrs = new AHRS(SPI.Port.kMXP); 
		} catch (RuntimeException ex ) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}

	public void arcadeDrive(double throttle, double turn) {
		robotDrive.arcadeDrive(throttle, turn);
		SmartDashboard.putNumber("R1", frontRight.getOutputCurrent());
		SmartDashboard.putNumber("R2", middleRight.getOutputCurrent());
		SmartDashboard.putNumber("R3", rearRight.getOutputCurrent());
		SmartDashboard.putNumber("L1", frontLeft.getOutputCurrent());
		SmartDashboard.putNumber("L2", middleLeft.getOutputCurrent());
		SmartDashboard.putNumber("L3", rearLeft.getOutputCurrent());
	}

	public void tankDrive(double left, double right) {
		robotDrive.tankDrive(left, right, false);
	}

	public void deployOmnis() {
		// deploy front & back Omni wheels
		omni.set(true);
	}

	public void retractOmnis() {
		// retract front & back Omni wheels
		omni.set(false);
	}

	public void shiftUp() {
		shifters.set(Value.kForward);
	}

	public void shiftDown() {
		shifters.set(Value.kReverse);
	}

	public void driveMotors(double lSpeed, double rSpeed) {
		frontLeft.set(lSpeed);
		frontRight.set(rSpeed);
	}

	@Override
	protected void initDefaultCommand() {
		// sets the default drive mode to colson drive
		setDefaultCommand(new TractionDrive());
	}

	public void setUpAutoControl() {
		frontLeft.enableBrakeMode(true);
		frontRight.enableBrakeMode(true);
		frontRight.reverseOutput(true);
	}

	public void setUpManualControl() {
		frontLeft.changeControlMode(TalonControlMode.PercentVbus);
		frontRight.changeControlMode(TalonControlMode.PercentVbus);
		frontLeft.enableBrakeMode(true);
		frontRight.enableBrakeMode(true);
		frontRight.reverseOutput(false);
	}

	public void setSpeeds(double leftSpeed, double rightSpeed) {
		//		rearLeft.set(-(leftSpeed / MAX_RPM) * 100);
		//		rearRight.set((rightSpeed / MAX_RPM) * 100);
		frontLeft.set(-leftSpeed);
		frontRight.set(rightSpeed);
	}

	public double getAngle() {
		return ahrs.getAngle();
	}

	public void resetAngle() {
		ahrs.zeroYaw();
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
		return omni.get();
	}

	public double getLeftError() {
		return frontLeft.getError();
	}

	public double getRightError() {
		return frontRight.getError();
	}
}
