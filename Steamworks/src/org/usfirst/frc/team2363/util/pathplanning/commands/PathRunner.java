package org.usfirst.frc.team2363.util.pathplanning.commands;

import java.util.List;

import org.usfirst.frc.team2363.robot.Robot;
import org.usfirst.frc.team2363.util.pathplanning.BoTHTrajectory;
import org.usfirst.frc.team2363.util.pathplanning.SrxPathReader;

import com.ctre.CANTalon;
import com.ctre.CANTalon.MotionProfileStatus;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.TrajectoryPoint;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PathRunner extends Command {
	
	private final String pathName;
	private boolean run;
	private boolean isFinished;
	
    public PathRunner(String pathName) {
        requires(Robot.drivetrain);
        this.pathName = pathName;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	run = true;
    	BoTHTrajectory path = SrxPathReader.importSrxTrajectory(pathName);
    	new ProfileLoader(Robot.drivetrain.getLeft(), 
    			SrxPathReader.getTrajectoryPoints(path.getTrajectory().getLeftProfile())).start();
    	new ProfileLoader(Robot.drivetrain.getRight(), 
    			SrxPathReader.getTrajectoryPoints(path.getTrajectory().getRightProfile())).start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	run = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	run = false;
    }
    
    private class ProfileLoader extends Thread {
    	
    	private final CANTalon talon;
    	private final List<TrajectoryPoint> points;
    	private boolean started = false;
    	
    	public ProfileLoader(CANTalon talon, List<TrajectoryPoint> points) {
    		this.talon = talon;
    		this.points = points;
    	}

		@Override
		public void run() {
			//setup talon
			talon.changeControlMode(TalonControlMode.MotionProfile);
			talon.changeMotionControlFramePeriod(5);
			talon.clearMotionProfileTrajectories();
			
			MotionProfileStatus status = new MotionProfileStatus();
			while (run && !points.isEmpty()) {
				//start the profile
				talon.getMotionProfileStatus(status);
				int bufferCount = status.topBufferCnt;
				if (!started && bufferCount > 5) {
					talon.set(CANTalon.SetValueMotionProfile.Enable.value);
					started = true;
				}
				
				if (!talon.isMotionProfileTopLevelBufferFull()) {
					//set next point to send
					TrajectoryPoint point = points.remove(0);
					//send the point
					talon.pushMotionProfileTrajectory(point);
					talon.processMotionProfileBuffer();
					
					if (bufferCount <= 5)  {
						continue;
					}
				}
					
				//sleep for 10ms
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					run = false;
				}
			}
			while (run && status.topBufferRem > 0) {
				talon.getMotionProfileStatus(status);
			}
			//finish the motion profile
			talon.set(CANTalon.SetValueMotionProfile.Disable.value);
			isFinished = true;
		}
    }
}
