package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearGroup extends CommandGroup {

    public GearGroup(String firstPath) {
        addSequential(new PathFollower(firstPath));
        addParallel(new GearGrabberDelivery());
        addSequential(new PathFollower("ReverseGear"));
    }
}
