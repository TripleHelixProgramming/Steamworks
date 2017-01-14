package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import static org.usfirst.frc.team2363.robot.subsystems.GearGrabber.*;
import static org.usfirst.frc.team2363.robot.Robot.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearGrabberCommand extends Command {
	
	private GearGrabberState run;
	
	public GearGrabberCommand(GearGrabberState run) {
        requires(gearGrabber);
        this.run = run;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (run == GearGrabberState.IN) {
    		gearGrabber.in();
    	} else if (run == GearGrabberState.OUT) {
    		gearGrabber.out();
    	} else {
    		gearGrabber.off();
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
