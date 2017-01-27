package org.usfirst.frc.team2363.robot.btMacro;

import java.io.FileNotFoundException;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MacroPlayCommand extends Command {
	
	BTMacroPlay player = null;
//	private boolean isFinished;

    public MacroPlayCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//try to create a new player
    	//if there is a file, great - you have a new non-null object "player"
    	try 
    	{
    		 player = new BTMacroPlay();
		} 
    	
		//if not, print out an error
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		//as long as there is a file you found, then use the player to scan the .csv file
		//and set the motor values to their specific motors
		if (player != null)
		{
			player.play();
//			isFinished = false; 
		}
		//do nothing if there is no file
		//if there is a player and you've disabled autonomous, then flush the rest of the values
		//and stop reading the file
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false; //isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if(player!= null)
		{
			player.end();
//			isFinished = true;
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
