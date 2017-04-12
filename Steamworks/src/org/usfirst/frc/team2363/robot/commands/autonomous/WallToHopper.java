package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.NoTargetFailSafe;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.shooter.ShooterCommand;
import org.usfirst.frc.team2363.robot.commands.wall.HopperJuggle;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 */
public class WallToHopper extends CommandGroup {

public WallToHopper(String path) {
    
    // Spin Shooter Up head to the Hopper
    addParallel(new ShooterCommand());
	addParallel(new WallExtend());
    addSequential(new PathFollower(path));     // Trigger the Hopper with the wall.
	
//	addSequential(new TurnToX(cameraOffset));
    addParallel(new SeveralJuggles());
//	//   NO TARGET fall back for vision processing
	if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
		addSequential(new NoTargetFailSafe(90));
	} else {
		addSequential(new NoTargetFailSafe(-90));
	}
} 

private class SeveralJuggles extends CommandGroup {
	public SeveralJuggles() {
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
		addSequential(new HopperJuggle());
	}
}
//    
}
