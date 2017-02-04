package org.usfirst.frc.team2363.robot.commands.lightRing;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team2363.robot.Robot.*;

/**
 *Set the light ring to green
 */
public class LightRingGreen extends Command {

    public LightRingGreen() {
        requires(lightRing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lightRing.green();
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
