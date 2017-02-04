package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftCommand extends Command {

	private boolean shiftUp;
	
    public ShiftCommand(boolean shiftUp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.shiftUp = shiftUp;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (shiftUp) {
    		Robot.drivetrain.shiftUp();
    	} else {
    		Robot.drivetrain.shiftDown();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}