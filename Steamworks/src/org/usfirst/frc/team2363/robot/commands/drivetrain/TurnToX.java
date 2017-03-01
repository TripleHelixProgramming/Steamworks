package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToX extends PIDCommand {
	
	static int previousX;
	static int targetX;
	int offset = 0;

    public TurnToX(int offset) {
//    	super(0.015, 0.0015, 0.0015);
    	super(0.01, 0.001, 0.001);
        requires(Robot.drivetrain);
        requires(Robot.lightRing);
        requires(Robot.feeder);
        
        this.offset = offset;
        previousX = offset;
        targetX = offset;
        
        getPIDController().setToleranceBuffer(10);
        getPIDController().setAbsoluteTolerance(5);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetAngle();
    	Robot.lightRing.green();
    	setSetpoint(offset);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Aiming Error", getPIDController().getError());
    	if (getPIDController().onTarget()) {
    		SmartDashboard.putBoolean("Target Aquired", true);
			SmartDashboard.putNumber("Turn To X", previousX);
    		DriverStation.reportError("Target Acquired : Turning Feeder ON", false);
    		Robot.feeder.on();
    	} else {
    		SmartDashboard.putBoolean("Target Acquired", false);
			SmartDashboard.putNumber("Turn To X", previousX);
    		DriverStation.reportError("Acquiring Target : Feeder OFF", false);
    		Robot.feeder.off();;
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

	@Override
	protected double returnPIDInput() {
		
		targetX = Robot.pixy.getTargetX();
		
		if (targetX != -1) {
			previousX = targetX;
			SmartDashboard.putNumber("Turn To X", targetX);
			DriverStation.reportError("Target To X :" + targetX, false);
			return targetX;
		} else {
			SmartDashboard.putNumber("Turn To X", previousX);
			DriverStation.reportError("No Target Found: Using previous angle", false);
			return previousX;
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Aiming Error", output);
		Robot.drivetrain.tankDrive(output, -output);
	}
}
