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
	
	private double angle;
	
	private static int DEFAULT_TOLERANCE = 5;

	/**
	 * result created while gyroPID runs
	 */
	double gyroOutput;

	private PIDController gyroPID;

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

		new PIDController(0.04, 0, 0, Robot.ahrs, new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				gyroOutput = output;
			}
		});

		getGyroPID().setAbsoluteTolerance(tolerance);

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
		
		getGyroPID().setSetpoint(Robot.ahrs.getAngle() + getAngle());
		getGyroPID().enable();
	}

	@Override
	public String toString() {
		return "TurnForAngle [angle=" + getAngle() + ", gyroOutput=" + gyroOutput + "]";
	}

	protected void execute() {
		// gyroTurn(angle);

		Robot.drivetrain.driveMotors(-gyroOutput, gyroOutput);
	}

	protected boolean isFinished() {
		return isGyroOnTarget() || isTimedOut();
	}

	protected void end() {
		getGyroPID().disable();
	}

	protected void interrupted() {
		end();
	}

	private boolean isGyroOnTarget() {
		return getGyroPID().onTarget();
	}

	public PIDController getGyroPID() {
		return gyroPID;
	}

	public double getAngle() {
		return angle;
	}

}