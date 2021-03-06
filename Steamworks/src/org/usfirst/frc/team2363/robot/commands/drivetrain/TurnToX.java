package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToX extends PIDCommand {
	
	int previousX;
	int targetX;
	int noTargetCount;
	int NO_TARGET_THRESHHOLD = 10;
	int offset = 0;

    public TurnToX(int offset) {
//    	super(0.015, 0.0015, 0.0015);		//	Practice Robot PID Values
    	super(0.01, 0.001, 0.001);			//	Competition Robot PID Values
    	
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
    	Robot.lightRing.green();
    	noTargetCount = 0;
    	setSetpoint(offset);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Aiming Error", getPIDController().getError());
    	if (getPIDController().onTarget()) {
    		SmartDashboard.putBoolean("Target Aquired: ", true);
    		DriverStation.reportError("Target Acquired : Turning Feeder ON", false);
    		Robot.feeder.on();
    	} else {
    		SmartDashboard.putBoolean("Target Acquired: ", false);
    		DriverStation.reportError("Acquiring Target : Feeder OFF", false);
    		Robot.feeder.off();;
    	}
    }

    protected boolean isFinished() {
    	//  This command should only finished, if it is continually finding NO TARGET
    	if (noTargetCount > NO_TARGET_THRESHHOLD) {
    		DriverStation.reportError("REVERTING TO TURN TO ZERO SHOOTING!! ", false);
    		
    	}
    	return noTargetCount > NO_TARGET_THRESHHOLD;
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
			noTargetCount = 0;
			SmartDashboard.putString("Target X: ", "" + targetX);
			DriverStation.reportError("Target X :" + targetX, false);
			return targetX;
		} else {
			noTargetCount++;
			SmartDashboard.putString("Target X: ", "Using previous X = " + previousX);
			DriverStation.reportError("NO TARGET: Using previous X = " + previousX, false);
			return previousX;
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("TurnToX Motor Output: ", output);
		Robot.drivetrain.tankDrive(output, -output);
	}
}
