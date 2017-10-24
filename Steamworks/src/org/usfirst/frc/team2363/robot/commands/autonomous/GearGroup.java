package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.GearGrabberDelivery;
import org.usfirst.frc.team2363.util.pathplanning.commands.PathRunner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class GearGroup extends CommandGroup {

    public GearGroup(String firstPath) {
        addSequential(new PathRunner(firstPath));
//        addParallel(new GearGrabberDelivery());
//      addParallel(new GearGrabberDownOut());
//        addSequential(new WaitCommand(.5));
//        addSequential(new PathRunner("ReverseGear"));
    }
}
