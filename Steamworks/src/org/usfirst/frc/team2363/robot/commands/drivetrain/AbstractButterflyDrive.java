package org.usfirst.frc.team2363.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import static org.usfirst.frc.team2363.robot.Robot.*;

public abstract class AbstractButterflyDrive extends Command {
	
	public AbstractButterflyDrive() {
		requires(drivetrain);
	}
	
	@Override
	protected void initialize() {
		defaultDrive();
	}

	@Override
	protected void execute() {
		if (oi.isFrontDeployed()) {
			drivetrain.deployFront();
			drivetrain.retractRear();
		} else if (oi.isRearDeployed()) {
			drivetrain.deployRear();
			drivetrain.retractFront();
		} else {
			defaultDrive();
		}
		
		drivetrain.arcadeDrive(oi.getThrottle(), oi.getTurn());
	}
	
	abstract protected void defaultDrive();

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() { }

	@Override
	protected void interrupted() { }
}

