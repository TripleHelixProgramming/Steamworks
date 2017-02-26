package org.usfirst.frc.team2363.robot.commands.drivetrain;

import java.util.List;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.util.DrivetrainMath;
import org.usfirst.frc.team2363.util.PathReader;
import org.usfirst.frc.team2363.util.PathStep;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathFollower extends Command {
	
	private List<PathStep> steps;
	private int currentStep;
	private double startTime;
	private final String pathName;

    public PathFollower(String pathName) {
    	this.pathName = pathName;
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	this.steps = PathReader.getPathSteps(pathName);
    	Robot.drivetrain.setUpAutoControl();
    	startTime = Timer.getFPGATimestamp();
    	currentStep = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (steps.isEmpty()) {
    		return;
    	}
    	
    	double currentTime = Timer.getFPGATimestamp() - startTime;
    	DriverStation.reportError("" + currentTime, false);
    	
    	while (steps.get(currentStep).getTimestamp() < currentTime) {
    		currentStep++;
    		if (currentStep >= steps.size()) {
    			return;
    		}
    	}
    	
    	if (currentStep < steps.size()) {
    		double leftSpeed = DrivetrainMath.fpsToRpm(steps.get(currentStep).getLeftSpeed(), 4);
    		double rightSpeed =  DrivetrainMath.fpsToRpm(steps.get(currentStep).getRightSpeed(), 4);
    		DriverStation.reportError("Found speeds " + leftSpeed + " " + rightSpeed, false);
    		Robot.drivetrain.setSpeeds(leftSpeed, rightSpeed);
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
