package org.usfirst.frc.team2363.robot.commands.shooter;

import org.usfirst.frc.team2363.robot.Robot;
import static org.usfirst.frc.team2363.robot.Robot.lightRing;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  PixyCheck command is used to check that we are receiving data from the Pixy Cam
 */
public class PixyCheck extends Command {

    public PixyCheck() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lightRing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lightRing.green();  // Red is really green.
    	int targetX = Robot.pixy.getTargetX();
    	if (targetX != -1) {
    		SmartDashboard.putString("Calibrate X:", "" + targetX);
    	} else {
    		SmartDashboard.putString("Calibrate X:", "No Target Found");
    		DriverStation.reportError("No Target Found", false);
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
