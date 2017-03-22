package org.usfirst.frc.team2363.robot.commands.drivetrain;

import java.util.List;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.util.DrivetrainMath;
import org.usfirst.frc.team2363.util.PathReader;
import org.usfirst.frc.team2363.util.PathStep;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PathFollower extends Command {
	
	private List<PathStep> steps;
	private int currentStep;
	private double startTime;
	private final String pathName;
	private double errorTotal;
	private double WHEEL_DIAMETER = 4;

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
    	errorTotal = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (steps.isEmpty()) {
    		return;
    	}
    	
    	double currentTime = Timer.getFPGATimestamp() - startTime;
    	while (steps.get(currentStep).getTimestamp() < currentTime) {
    		currentStep++;
    		if (currentStep >= steps.size()) {
    			return;
    		}
    	}
    	
    	if (currentStep < steps.size()) {
    		double leftSpeed = DrivetrainMath.fpsToRpm(steps.get(currentStep).getLeftSpeed(), WHEEL_DIAMETER);
    		double rightSpeed =  DrivetrainMath.fpsToRpm(steps.get(currentStep).getRightSpeed(), WHEEL_DIAMETER);
    		Robot.drivetrain.setSpeeds(leftSpeed, rightSpeed);
    	}
    	
    	SmartDashboard.putNumber("Left Error", Robot.drivetrain.getLeftError());
    	SmartDashboard.putNumber("Right Error", Robot.drivetrain.getRightError());
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
