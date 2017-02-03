package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnForAngle extends Command {
	
	double angle;
	
	private static int DEFAULT_TOLERANCE = 5;

	/**
	 * result created while gyroPID runs
	 */
	double gyroOutput;

	PIDController gyroPID;

	/**
	 * Attempts to turn the robot by the given relative angle, within the given
	 * tolerance
	 * 
	 * @param angle
	 *            Angle in degrees
	 * @param tolerance
	 *            Tolerance in degrees
	 */
	public TurnForAngle(double angle, double tolerance) {
		requires(Robot.drivetrain);

		this.angle = angle;
		this.setTimeout(5);

		gyroPID = new PIDController(0.04, 0, 0, Robot.ahrs, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				gyroOutput = output;
			}
		});

		gyroPID.setAbsoluteTolerance(tolerance);

		SmartDashboard.putData("gyroid", gyroPID);
	}

	/**
	 * Creates a PIDGyroTurn with the given relative angle and a default
	 * tolerance of 5
	 * 
	 * @param angle
	 *            The angle to turn to
	 */
	public TurnForAngle(double angle) {
		this(angle, DEFAULT_TOLERANCE);
	}

	protected void initialize() {
		System.err.println("Initializing " + this);
		
		SmartDashboard.putNumber("Turning For Angle", Robot.ahrs.getAngle() + angle);
		
		gyroPID.setSetpoint(Robot.ahrs.getAngle() + angle);
		gyroPID.enable();
	}

	@Override
	public String toString() {
		return "TurnForAngle [angle=" + angle + ", gyroOutput=" + gyroOutput + "]";
	}

	protected void execute() {
		// gyroTurn(angle);
		
		SmartDashboard.putNumber("gyro_value", Robot.ahrs.getAngle());

		Robot.drivetrain.driveMotors(-gyroOutput, gyroOutput);
	}

	protected boolean isFinished() {
		return isGyroOnTarget() || isTimedOut();
	}

	protected void end() {
		gyroPID.disable();
	}

	protected void interrupted() {
		end();
	}

	private boolean isGyroOnTarget() {
		return gyroPID.onTarget();
	}

}