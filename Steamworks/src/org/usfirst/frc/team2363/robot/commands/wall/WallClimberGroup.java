package org.usfirst.frc.team2363.robot.commands.wall;

import org.usfirst.frc.team2363.robot.commands.gearGrabber.BringGearGrabberUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class WallClimberGroup extends CommandGroup {

    public WallClimberGroup() {
        addSequential(new BringGearGrabberUp(), 2);
        addSequential(new WallClimber());
    }
}
