package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import static org.usfirst.frc.team2363.robot.Robot.gearGrabber;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearGrabberDown extends Command {

    public GearGrabberDown() {
        requires(gearGrabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearGrabber.off();
    	gearGrabber.down();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
