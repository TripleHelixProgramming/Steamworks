package org.usfirst.frc.team2363.robot.btMacro;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RecordState extends Command {
	
	private boolean state;
	
    public RecordState(boolean state) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.btMain);
        this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.btMain.isRecording = state;
    	Robot.btMain.operatorControl();
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
