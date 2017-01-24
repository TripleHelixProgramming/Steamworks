package org.usfirst.frc.team2363.robot.commands.gearGrabber;


import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearGrabberPosition extends Command {

	private boolean down;
	
    public GearGrabberPosition(boolean down) {
    	this.down = down;
    }

    // Called just before this Command runs the first time
    protected void initialize() { }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	if (down) {
    		Robot.gearGrabber.down();
    	} else {
    		Robot.gearGrabber.up();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() { }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() { }
}
