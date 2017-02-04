package org.usfirst.frc.team2363.robot.commands.drivetrain;

import static org.usfirst.frc.team2363.robot.Robot.drivetrain;

public class OmniDrive extends AbstractButterflyDrive {
	
	public OmniDrive() {
		// needs code from the drivetrain subsystem
		requires(drivetrain);
	}

	@Override
	protected void defaultDrive() {
		// deploys front & rear omni wheels
		drivetrain.deployOmnis();
	}
}
