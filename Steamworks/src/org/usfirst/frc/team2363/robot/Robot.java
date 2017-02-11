 
package org.usfirst.frc.team2363.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TestF;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;
import org.usfirst.frc.team2363.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2363.robot.subsystems.Feeder;
import org.usfirst.frc.team2363.robot.subsystems.GearGrabber;
import org.usfirst.frc.team2363.robot.subsystems.LightRing;
import org.usfirst.frc.team2363.robot.subsystems.Shooter;
import org.usfirst.frc.team2363.robot.subsystems.Wall;
import org.usfirst.frc.team2363.robot.subsystems.Pixy;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TurnForAngle;
import org.usfirst.frc.team2363.util.PathReader;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// ps4 interface class
	public static OI oi;
	
	// subsystems
	public static Drivetrain drivetrain;
	public static GearGrabber gearGrabber;
	public static Shooter shooter;
	public static Feeder feeder;
	public static Pixy pixy;
	public static AHRS ahrs;
	public static LightRing lightRing;
	public static Wall tiltingWall;
	
	// declare SmartDashboard tools
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

  public Robot() {
      
    	// declare subsystems
	  drivetrain = new Drivetrain();
	  gearGrabber = new GearGrabber();
	  shooter = new Shooter();
	  feeder = new Feeder();
	  pixy = new Pixy();
	  lightRing = new LightRing();
	  tiltingWall = new Wall();
    
      // Instantiate the NavMXP Gyro
      try {
          ahrs = new AHRS(SPI.Port.kMXP); 
      } catch (RuntimeException ex ) {
          DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
      }
  }
    
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		// declare ps4 interface
		oi = new OI();
		// sets the default autonomous mode
		chooser.addDefault("Default Auto", new TractionDrive());
		chooser.addObject("TestF", new TestF());
		// chooser.addObject("My Auto", new MyAutoCommand());
		// allows user to choose autonomous mode from the SmartDashboard
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("autoturn", Robot.pixy.autoAllign());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
		// makes sure only one command per subsystems runs at a time
		Scheduler.getInstance().run();

		shooter.getRPM();  // print RPM to dashboard
    
		SmartDashboard.putBoolean("Has Gear", gearGrabber.hasGear());
		SmartDashboard.putNumber("Gear Grabber Current", gearGrabber.getOutputCurrent());
		SmartDashboard.putNumber("Analog Value", gearGrabber.getGearLimit().getValue());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// reads the selected autonomous mode from SmartDashboard
//		autonomousCommand = chooser.getSelected();
		autonomousCommand = new PathFollower(PathReader.getPathSteps("RedHopper1"));

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// makes sure only one command per subsystems runs at a time
		Scheduler.getInstance().run();
		drivetrain.updateSmartDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// makes sure only one command per subsystems runs at a time
		Scheduler.getInstance().run();
		SmartDashboard.putBoolean("Has Gear", gearGrabber.hasGear());
		SmartDashboard.putNumber("Gear Grabber Current", gearGrabber.getOutputCurrent());
		drivetrain.updateSmartDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		// brings up a window with the state of the robot parts
		LiveWindow.run();
	}
}
