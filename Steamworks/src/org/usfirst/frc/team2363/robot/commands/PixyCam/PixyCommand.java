package org.usfirst.frc.team2363.robot.commands.PixyCam;

import static org.usfirst.frc.team2363.robot.Robot.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PixyCommand extends Command {
	
	int Signature = 1;
	
    public PixyCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	// needs code from the gearGrabber subsystem
        requires(pixy);
      
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (pixy.isOff()) {
    			pixy.on();	
    	} else if (pixy.isOn()) {
    			pixy.off();
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
