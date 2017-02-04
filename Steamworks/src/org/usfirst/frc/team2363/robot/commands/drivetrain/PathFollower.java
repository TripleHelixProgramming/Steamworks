package org.usfirst.frc.team2363.robot.commands.drivetrain;

import java.util.List;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.util.PathStep;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathFollower extends Command {
	
	private final List<PathStep> steps;
	private int currentStep;

    public PathFollower(List<PathStep> steps) {
        this.steps = steps;
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setUpAutoControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double currentTime = Timer.getFPGATimestamp();
    	while (steps.get(currentStep).getTimestamp() < currentTime) {
    		currentStep++;
    	}
    	
    	if (currentStep < steps.size()) {
    		Robot.drivetrain.setSpeeds(steps.get(currentStep).getLeftSpeed(), steps.get(currentStep).getRightSpeed());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return currentStep >= steps.size();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setUpManualControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setUpManualControl();
    }
}
