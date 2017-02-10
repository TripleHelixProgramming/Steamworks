package org.usfirst.frc.team2363.robot.commands.lightRing;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team2363.robot.Robot.*;

public class LightRingOperator extends Command {
	
	/**
	 *Use to give control of the light ring to the operator d-pad
	 */
    public LightRingOperator() {
        requires(lightRing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (oi.getOeratorPOV() == 90) {
    		//right on d-pad
    		lightRing.red();
    	} else if (oi.getOeratorPOV() == 270) {
    		//left on d-pad
    		lightRing.green();
    	} else if (oi.getOeratorPOV() == 0) {
    		//up on d-pad
    		lightRing.both();
    	} else {
    		//anything that isn't defined above
    		lightRing.off();
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
