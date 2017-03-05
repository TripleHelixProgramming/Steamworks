package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.NoTargetFailSafe;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToAngle;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;
import org.usfirst.frc.team2363.robot.commands.wall.WallRetract;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerExtend;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
/**
 *
 */
public class WallToHopper extends CommandGroup {

    public WallToHopper(String path, int cameraOffset) {
    	addParallel(new PIDShooterCommand());
    	addSequential(new PathFollower(path));
    	addSequential(new TurnToAngle(0));
    	addSequential(new WallExtend(), 1);
    	addSequential(new WallTriggerExtend(), .5);
    	addSequential(new TurnToAngle(
    			DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue ? -10 : 10), 1);
    	addSequential(new TurnToAngle(0));
    	addParallel(new HopperJuggle());
//    	addSequential(new TurnToX(cameraOffset));
    	
    	//   NO TARGET fall back for vision processing
    	addSequential(new NoTargetFailSafe(0));
    }
    
    private class HopperJuggle extends CommandGroup {
    	public HopperJuggle() {
    		addSequential(new WallRetract(), 1);
    		addSequential(new WallExtend(), 1);
    		addSequential(new WaitCommand(5));
    		addSequential(new WallRetract(), 2);
    		addSequential(new WallExtend(), 2);
    		addSequential(new WallRetract());
    	}
    }
}
