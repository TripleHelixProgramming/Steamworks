package org.usfirst.frc.team2363.robot.commands.shooter;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterCommand extends Command {
	
	private boolean run;

    public ShooterCommand(boolean run) {
    	requires(Robot.shooter);
    	this.run = run;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (run) {
    		Robot.shooter.setPower(Robot.shooter.getOutput());
    	} else {
    		Robot.shooter.setPower(0);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
