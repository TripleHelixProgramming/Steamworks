package org.usfirst.frc.team2363.robot.commands.drivetrain;

import static org.usfirst.frc.team2363.robot.Robot.*;

public class TractionDrive extends AbstractButterflyDrive {
	
	public TractionDrive() {
		requires(drivetrain);
	}

	@Override
	protected void defaultDrive() {
		drivetrain.retractFront();
		drivetrain.retractRear();
	}
}
