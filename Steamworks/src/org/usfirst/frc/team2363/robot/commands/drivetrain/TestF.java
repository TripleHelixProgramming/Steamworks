package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.util.DrivetrainMath;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestF extends Command {

    public TestF() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setUpAutoControl();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.setSpeeds(DrivetrainMath.fpsToRpm(5, 4), DrivetrainMath.fpsToRpm(5, 4) );
//    	Robot.drivetrain.setSpeeds(100, 100);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setUpManualControl();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setUpManualControl();
    }
}
