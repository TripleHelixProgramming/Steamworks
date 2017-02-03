package org.usfirst.frc.team2363.robot.commands.wall;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.robot.subsystems.Wall.WallState;

import static org.usfirst.frc.team2363.robot.Robot.*;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WallCommand extends Command {

	//declares "ON" state
	private WallState run;
	
    public WallCommand(WallState run) {
        // Use requires() here to declare subsystem dependencies
    	requires(tiltingWall);
        // eg. requires(chassis);
    	this.run = run;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//extends the wall if not already extended

    	switch(run) {
    	case EXTEND: 
    		if(!Robot.tiltingWall.isExtended()) {
    				tiltingWall.extend();
    			}
    		break;
    	case RETRACT:
    		if(!Robot.tiltingWall.isRetracted()) {
    				tiltingWall.retract();
    			}
    		break;
    	case TRIGGER_ON:
    		if(!Robot.tiltingWall.isExtended()) {
    			tiltingWall.extend();		 
    		}
    		tiltingWall.triggerOn();
    		break;
    	case TRIGGER_OFF:
    		if(!Robot.tiltingWall.isTriggerOff()) {
    			tiltingWall.triggerOff();
    		}
    		break;
    	case CLIMBER_ON:
    		if(!Robot.tiltingWall.isExtended()) {
    			tiltingWall.extend();
    				
    		}
    		tiltingWall.climberOn();
    		break;
    	case CLIMBER_OFF:
    		if(!Robot.tiltingWall.isClimberOff()) {
    			tiltingWall.climberOff();
    		}
    		break;
    	default:		//OFF
    		tiltingWall.off();
    		break;
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
