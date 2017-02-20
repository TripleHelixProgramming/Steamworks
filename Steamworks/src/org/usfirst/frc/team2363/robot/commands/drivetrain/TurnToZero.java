package org.usfirst.frc.team2363.robot.commands.drivetrain;

import java.util.Optional;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToZero extends PIDCommand {

    public TurnToZero() {
    	super(0.04, 0, 0.002);
        requires(Robot.drivetrain);
        getPIDController().setToleranceBuffer(10);
        getPIDController().setAbsoluteTolerance(1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		setSetpoint(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return getPIDController().onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {    
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected double returnPIDInput() {
		return Robot.drivetrain.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
			Robot.drivetrain.tankDrive(-output, output);
	}
}

