package org.usfirst.frc.team2363.robot.commands.autonomous;

import static org.usfirst.frc.team2363.robot.RobotMap.*;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnAroundBackWheel;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnToBoiler;
import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberStop;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RedGearAndHopper extends CommandGroup {

    public RedGearAndHopper() {
//        addSequential(new PathFollower("RedGear"));
        addParallel(new GearGrabberDelivery());
        addSequential(new WaitCommand(0.5));
        addParallel(new PIDShooterCommand());
        addParallel(new GearGrabberStop());
//        addSequential(new PathFollower("RedFromGearToHopper"));
        addParallel(new TurnAroundBackWheel(0, 1));
        addParallel(new WallExtend());
        addSequential(new TurnToBoiler(REDSIDE_OFFSET));
        addSequential(new FeederCommand(true));
        //WIN
    }
}
