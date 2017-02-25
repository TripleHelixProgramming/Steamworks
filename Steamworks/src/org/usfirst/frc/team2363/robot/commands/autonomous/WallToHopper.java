package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToBoiler;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToZero;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerExtend;

import edu.wpi.first.wpilibj.command.CommandGroup;
/**
 *
 */
public class WallToHopper extends CommandGroup {

    public WallToHopper(String path, double cameraOffset) {
    	addParallel(new PIDShooterCommand());
    	addSequential(new PathFollower(path));
    	addSequential(new TurnToZero());
    	addSequential(new WallExtend(), 1);
    	addSequential(new WallTriggerExtend());
    	addSequential(new TurnToBoiler(cameraOffset));
    }
}