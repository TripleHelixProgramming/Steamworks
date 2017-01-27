package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import static org.usfirst.frc.team2363.robot.Robot.gearGrabber;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.robot.subsystems.GearGrabber.GearGrabberState;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearGrabberDelivery extends Command {
	// declares "on" state
		private GearGrabberState run;
		
    public GearGrabberDelivery(GearGrabberState run) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	// needs code from the gearGrabber subsystem
        requires(gearGrabber);
        // declares "on" state in current instance
        this.run = run;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (run == GearGrabberState.DELIVER) {
    		if (Robot.gearGrabber.hasGear()) {
    			gearGrabber.out();
    			
    		} else {     // GearGrabber OFF State
    			// turns gear grabber inwards
    			gearGrabber.off();
    		}
    	} else {     // GearGrabber OFF State
			// turns gear grabber inwards
			gearGrabber.off();
			gearGrabber.up();
		} 
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
