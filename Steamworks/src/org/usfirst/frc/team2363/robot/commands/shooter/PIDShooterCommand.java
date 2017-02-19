package org.usfirst.frc.team2363.robot.commands.shooter;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PIDShooterCommand extends PIDCommand {
	
    public PIDShooterCommand() {
    	super(0.003, 0, 0);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	getPIDController().enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Shooter Error", getPIDController().getError());
    	setSetpoint(3300);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    

    // Called once after isFinished returns true
    protected void end() {
    	getPIDController().disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	getPIDController().disable();
    }

	@Override
	protected double returnPIDInput() {
		double rpm = Robot.shooter.getRPM();
		DriverStation.reportWarning("Shooter RPM : " + rpm, false);
		return rpm;
	}

	@Override
	protected void usePIDOutput(double output) {
		double power = Shooter.getEstimatedPower(getSetpoint()) + output;
		Robot.shooter.setPower(power);
		SmartDashboard.putNumber("Shooter Power", power);
//		Robot.shooter.setPower(1);
	}
}
