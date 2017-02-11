package org.usfirst.frc.team2363.robot.commands.wall;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team2363.robot.Robot.*;

import org.usfirst.frc.team2363.robot.Robot;

/**
 *
 */
public class WallClimber extends Command {

    public WallClimber() {
        // Use requires() here to declare subsystem dependencies
        requires(tiltingWall);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	tiltingWall.extend();
		tiltingWall.setClimber(Robot.oi.getClimberPower());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tiltingWall.isClimberStalled();
    }

    // Called once after isFinished returns true
    protected void end() {
    	new WallClimberStalled();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
