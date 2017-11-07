package org.usfirst.frc.team2363.robot.commands.wall;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team2363.robot.Robot.*;

import org.usfirst.frc.team2363.robot.Robot;
/**
 *
 */
public class WallOff extends Command {

    public WallOff() {
        // Use requires() here to declare subsystem dependencies
        requires(tiltingWall);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		tiltingWall.off();
//		tiltingWall.setClimber(0);
		tiltingWall.setClimber(Robot.oi.getClimberPower());
		tiltingWall.triggerRetract();
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
