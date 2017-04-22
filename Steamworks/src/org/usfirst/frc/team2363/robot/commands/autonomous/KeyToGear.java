package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.NoTargetFailSafe;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.robot.commands.shooter.ShooterCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.StopShooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class KeyToGear extends CommandGroup {

    public KeyToGear(String path) {
    	addParallel(new ShooterCommand());
    	addSequential(new WaitCommand(1));
    	addSequential(new FeederCommand(true), 4);
    	addParallel(new StopShooter());
    	addParallel(new FeederCommand(false));
    	addSequential(new PathFollower(path));
    	addParallel(new GearGrabberDelivery());
        addSequential(new WaitCommand(.5));
        addSequential(new PathFollower("ReverseGear"));
    }
}
