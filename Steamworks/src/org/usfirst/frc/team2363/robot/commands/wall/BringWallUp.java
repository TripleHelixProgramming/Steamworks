package org.usfirst.frc.team2363.robot.commands.wall;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BringWallUp extends Command {
	
	private boolean isAlreadyUp;

    public BringWallUp() {
        requires(Robot.tiltingWall);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isAlreadyUp = Robot.tiltingWall.isRetracted();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.tiltingWall.off();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isAlreadyUp;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}