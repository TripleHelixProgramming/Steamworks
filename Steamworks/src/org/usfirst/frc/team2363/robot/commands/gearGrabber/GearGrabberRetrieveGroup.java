package org.usfirst.frc.team2363.robot.commands.gearGrabber;

import org.usfirst.frc.team2363.robot.commands.wall.BringWallUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearGrabberRetrieveGroup extends CommandGroup {

    public GearGrabberRetrieveGroup() {
        addSequential(new BringWallUp(), 2);
        addSequential(new GearGrabberRetrieve());
    }
}