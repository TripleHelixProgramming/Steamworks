package org.usfirst.frc.team2363.robot.commands.drivetrain;

import java.util.Optional;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnToBoiler extends PIDCommand {
	
	static double previousAngle = 0.0;

    public TurnToBoiler() {
    	super(0.04, 0, 0.002);
        requires(Robot.drivetrain);
        requires(Robot.lightRing);
        requires(Robot.feeder);
        
        getPIDController().setToleranceBuffer(10);
        getPIDController().setAbsoluteTolerance(.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetAngle();
    	Robot.lightRing.green();
    	setSetpoint(0.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Aiming Error", getPIDController().getError());
    	if (getPIDController().onTarget()) {
    		SmartDashboard.putBoolean("Target Acquired", true);
    		DriverStation.reportError("Target Acquired : Turning Feeder ON", false);
    		//  Robot.feeder.on();
    	} else {
    		SmartDashboard.putBoolean("Target Acquired", false);
    		DriverStation.reportError("Acquiring Target : Feeder OFF", false);
    		//  Robot.feeder.off();;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // return getPIDController().onTarget();
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
	
		Optional<Double> targetAngle = Robot.pixy.getTargetAngle();
		
		if (targetAngle.isPresent()) {
			previousAngle = targetAngle.get();
			SmartDashboard.putNumber("Turn Angle", previousAngle);
			DriverStation.reportError("Target Angle :" + previousAngle, false);
			return targetAngle.get();
		} else {
			DriverStation.reportError("No Target Found: Using previous angle", false);
			return previousAngle;
		}
	}

	@Override
	protected void usePIDOutput(double output) {
		if (output > .3) {
			Robot.drivetrain.tankDrive(.3, -.3);
		} else if(output < -.3) {
			Robot.drivetrain.tankDrive(-.3, .3);
		} else {
			Robot.drivetrain.tankDrive(output, -output);
		}
	}
}