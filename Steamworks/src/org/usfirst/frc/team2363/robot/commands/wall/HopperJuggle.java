package org.usfirst.frc.team2363.robot.commands.wall;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HopperJuggle extends CommandGroup {

    public HopperJuggle() {
   		addSequential(new WallRetract(), 0.55);
		addSequential(new WallExtend(), 0.75);
    }
}
