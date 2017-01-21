package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import static org.usfirst.frc.team2363.robot.subsystems.GearGrabber.*;

import org.usfirst.frc.team2363.robot.Robot;
//import org.usfirst.frc.team2363.robot.subsystems.Intake.IntakeState;

import static org.usfirst.frc.team2363.robot.Robot.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearGrabberCommand extends Command {
	
	// declares "on" state
	private GearGrabberState run;
	
	public GearGrabberCommand(GearGrabberState run) {
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
    	// turns off the gear grabber if it already has a gear in it
    	if (run == GearGrabberState.IN) {
    		if (Robot.gearGrabber.hasGear()) {
    			gearGrabber.off();
    		} else {
    			// turns gear grabber inwards
    			gearGrabber.in();
    		}
    	}  else {
    		// keeps gear grabber still
    		gearGrabber.off();
    	} 
    		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	// doesn't end
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


