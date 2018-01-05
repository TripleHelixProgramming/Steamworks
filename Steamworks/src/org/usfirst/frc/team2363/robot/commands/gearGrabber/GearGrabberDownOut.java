package org.usfirst.frc.team2363.robot.commands.gearGrabber;
import org.usfirst.frc.team2363.robot.commands.wall.BringWallUp;

import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 *
 */
public class GearGrabberDownOut extends CommandGroup {

    public GearGrabberDownOut() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new BringWallUp(), .5);
    	addSequential(new GearGrabberDelivery(), .5);
//    	addSequential(new WaitCommand(0.4));
    	addSequential(new GearGrabberDown());
    }
}
