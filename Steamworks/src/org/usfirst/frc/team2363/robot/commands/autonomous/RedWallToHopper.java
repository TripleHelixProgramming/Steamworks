package org.usfirst.frc.team2363.robot.commands.autonomous;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.wall.WallExtend;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerExtend;
import org.usfirst.frc.team2363.robot.commands.wall.WallTriggerRetract;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class RedWallToHopper extends CommandGroup {

    public RedWallToHopper() {
    	requires(Robot.drivetrain);
    	requires(Robot.tiltingWall);
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
    	addSequential(new PathFollower("RedHopper"));
    	addSequential(new WallExtend(),2);
    	// addSequential(new WaitCommand(2));
    	addSequential(new WallTriggerExtend());
    }
}
