package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 *
 */
public class TurnToBoiler extends PIDCommand {

    public TurnToBoiler() {
    	super(0, 0, 0);
        requires(Robot.drivetrain);
        requires(Robot.lightRing);
        getPIDController().setToleranceBuffer(3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lightRing.green();
//    	if (Robot.pixy.getTargetAngle().isPresent()) {
//   		setSetpoint(Robot.pixy.getTargetAngle().get());
//    	}
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
    	Robot.lightRing.off();
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
		Robot.drivetrain.tankDrive(output, -output);
	}
}
