package org.usfirst.frc.team2363.robot.commands.drivetrain;

import org.usfirst.frc.team2363.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class NoTargetFailSafe extends TurnToAngle {

	public NoTargetFailSafe(double angle) {
		super(angle);
	}

	@Override
	protected void execute() {
		if (getPIDController().onTarget()) {
			DriverStation.reportError("NoTargetFailSafe : Turning Feeder ON", false);
			Robot.feeder.on();
		} else {
			DriverStation.reportError("NoTargetFailSafe : Feeder OFF", false);
			Robot.feeder.off();;
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
