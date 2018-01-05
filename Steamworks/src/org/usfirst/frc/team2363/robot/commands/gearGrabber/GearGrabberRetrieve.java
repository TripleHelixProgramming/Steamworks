package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import static org.usfirst.frc.team2363.robot.Robot.gearGrabber;

import edu.wpi.first.wpilibj.command.Command;

public class GearGrabberRetrieve extends Command {
	
	private int stalledCount = 0;
	private int limitCount = 0;
	Command rumbleCommand = new RumbleFor2Sec();
	
	public GearGrabberRetrieve() {
        requires(gearGrabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearGrabber.plate_up();
    	gearGrabber.down();
    	
    	if (gearGrabber.isOverCurrent()) {
    		stalledCount++;
    	} else {
    		stalledCount = 0;
    	}
    	
    	if (gearGrabber.hasGear()) {
    		if (!rumbleCommand.isRunning()) {
    			rumbleCommand.start();
    		}
    		gearGrabber.coast();
    	} else {
    		gearGrabber.in();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stalledCount > 50;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!rumbleCommand.isRunning()) {
			rumbleCommand.start();
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    public int getLimitCount() {
    	return limitCount;
    }
}


