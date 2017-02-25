package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToBoiler;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToZero;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberStop;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerExtend;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearAndHopper extends CommandGroup {

    public GearAndHopper(String path, double cameraOffset) {
    	addParallel(new PathSequence(path, cameraOffset));
        addSequential(new WaitCommand(2.5));
        addSequential(new GearGrabberDelivery(), 2);
        addParallel(new PIDShooterCommand());
        addParallel(new GearGrabberStop());
        //WIN
    }
    
    private class PathSequence extends CommandGroup {
    	public PathSequence(String path, double cameraOffset) {
    		addSequential(new PathFollower(path));
    		addSequential(new TurnToZero());
    		addSequential(new WallExtend(), 1);
        	addSequential(new WallTriggerExtend());
            addSequential(new TurnToBoiler(cameraOffset));
    	}
    }
}