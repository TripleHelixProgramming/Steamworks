 
package org.usfirst.frc.team2363.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2363.robot.commands.autonomous.GearAndHopper;
import org.usfirst.frc.team2363.robot.commands.autonomous.GearGroup;
import org.usfirst.frc.team2363.robot.commands.autonomous.KeyToGear;
import org.usfirst.frc.team2363.robot.commands.autonomous.WallToHopper;
import org.usfirst.frc.team2363.robot.commands.drivetrain.AutoAim;
import org.usfirst.frc.team2363.robot.commands.drivetrain.PathFollower;
import org.usfirst.frc.team2363.robot.commands.drivetrain.TractionDrive;
import org.usfirst.frc.team2363.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2363.robot.subsystems.Feeder;
import org.usfirst.frc.team2363.robot.subsystems.GearGrabber;
import org.usfirst.frc.team2363.robot.subsystems.LightRing;
import org.usfirst.frc.team2363.robot.subsystems.Shooter;
import org.usfirst.frc.team2363.robot.subsystems.Wall;
import org.usfirst.frc.team2363.robot.commands.shooter.PIDShooterCommand;
import org.usfirst.frc.team2363.robot.commands.shooter.PixyCheck;
import org.usfirst.frc.team2363.util.Pixy;

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
	public static LightRing lightRing;
	public static Wall tiltingWall;
	public static PIDShooterCommand pidShooterCommand;
	
	private final DigitalInput autoBoilerHopper = new DigitalInput(2);
	private final DigitalInput autoLoaderGear = new DigitalInput(3);
	private final DigitalInput autoGearHopper = new DigitalInput(4);
//	private final DigitalInput autoCenter = new DigitalInput(5);
	private final DigitalInput autoKeyGear = new DigitalInput(5);
	
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
//		chooser.addDefault("Default Auto", new TractionDrive());
//		chooser.addDefault("Pixy Calibrate", new PixyCheck());
//		chooser.addDefault("Red Auto Aim", new AutoAim(RobotMap.RED_X_OFFSET));
//		chooser.addDefault("Blue Auto Aim", new AutoAim(RobotMap.BLUE_X_OFFSET));
//		chooser.addObject("CONFIG", new PathFollower("TestDrive"));
//		chooser.addObject("Center Gear", new GearGroup("Center"));
//		chooser.addObject("Red Boiler Hopper", new WallToHopper("RedBoilerHopper", RobotMap.RED_X_OFFSET));
//		chooser.addObject("Red Loader Gear", new GearGroup("RedLoaderGear"));
//		chooser.addObject("Red Boiler Gear", new GearGroup("RedBoilerGear"));
//		chooser.addObject("Red Key Gear Hopper", new GearAndHopper("RedKeyGearHopper", RobotMap.RED_X_OFFSET));
//		chooser.addObject("Blue Boiler Hopper", new WallToHopper("BlueBoilerHopper", RobotMap.BLUE_X_OFFSET));
//		chooser.addObject("Blue Loader Gear", new GearGroup("BlueLoaderGear"));
//		chooser.addObject("Blue Boiler Gear", new GearGroup("BlueBoilerGear"));
//		chooser.addObject("Blue Key Gear Hopper", new GearAndHopper("BlueKeyGearHopper", RobotMap.BLUE_X_OFFSET));
		
		// Set the start heading as zero.  Later TurnToZero is used to return to this heading.
		// to trigger the hopper.
		SmartDashboard();
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		oi.setControllerRumble(false);
	}

	@Override
	public void disabledPeriodic() {
		// makes sure only one command per subsystems runs at a time
		Scheduler.getInstance().run();
		SmartDashboard();
		SmartDashboard.putBoolean("Gear Grabber Tape", gearGrabber.hasGear());
		SmartDashboard.putNumber("Robot heading", drivetrain.getAngle());
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());

		
		
		if (!autoBoilerHopper.get()) {
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + "  Boiler Hopper");
		} else if (!autoLoaderGear.get()) {
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name () + " Loader Gear");
		} else if (!autoGearHopper.get()){
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + " Gear Hopper");
		} else {
//			SmartDashboard.putString("Selected Auto", "Center Gear");
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + " Key Gear");
		}
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
		
		Robot.drivetrain.resetAngle();        // Reset the gyro.  This position is 0 for TurnToAngle(0);
		Robot.drivetrain.shiftDown();         // Low gear for autonomous
		
		// reads the selected autonomous mode from SmartDashboard
//		autonomousCommand = chooser.getSelected();
//		autonomousCommand = new PixyCheck();
//		autonomousCommand = new GearGroup("RedLoaderGear");
//		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
//			autonomousCommand = new WallToHopper("BlueBoilerHopper", RobotMap.BLUE_X_OFFSET);
//		} else {
//			autonomousCommand = new WallToHopper("RedBoilerHopper", RobotMap.RED_X_OFFSET);
//		}
		
		WallToHopper blueBoilerHopper = new WallToHopper("BlueBoilerHopper");
		WallToHopper redBoilerHopper = new WallToHopper("RedBoilerHopper");
		
		KeyToGear blueKeyGear = new KeyToGear("BlueKeyGear");
		KeyToGear redKeyGear = new KeyToGear("RedKeyGear");
		
//		GearGroup blueBoilerGear = new GearGroup("BlueBoilerGear");
		GearGroup redBoilerGear = new GearGroup("RedBoilerGear");
		GearGroup blueLoaderGear = new GearGroup("BlueLoaderGear");
		GearGroup redLoaderGear = new GearGroup("RedLoaderGear");
		GearGroup centerGear = new GearGroup("Center");
		GearAndHopper blueGearHopper = new GearAndHopper("BlueBoilerGear", "BlueGearHopper", RobotMap.BLUE_X_OFFSET);
		GearAndHopper redGearHopper = new GearAndHopper("RedBoilerGear", "RedGearHopper", RobotMap.RED_X_OFFSET);
		PathFollower testDrive = new PathFollower("TestDrive");
//		WallToHopper blueBoilerHopper = new WallToHopper("BlueBoilerHopper", RobotMap.BLUE_X_OFFSET);
//		WallToHopper redBoilerHopper = new WallToHopper("RedBoilerHopper", RobotMap.RED_X_OFFSET);
		
		if (!autoBoilerHopper.get()) {
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + "  Boiler Hopper");
			if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
				autonomousCommand = blueBoilerHopper;
			} else {
				autonomousCommand = redBoilerHopper;
			}
		} else if (!autoLoaderGear.get()) {
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name () + " Loader Gear");
			if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
				autonomousCommand = blueLoaderGear;
			} else {
				autonomousCommand = redLoaderGear;
			}
		} else if (!autoGearHopper.get()){
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + " Gear Hopper");
			if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
				autonomousCommand = blueGearHopper;
			} else {
				autonomousCommand = redGearHopper;
			}
		} else {
			SmartDashboard.putString("Selected Auto", DriverStation.getInstance().getAlliance().name() + " Key Gear");
			if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
				autonomousCommand = blueKeyGear;
			} else {
				autonomousCommand = redKeyGear;
			}
//			SmartDashboard.putString("Selected Auto", "Center Gear");
//			autonomousCommand = centerGear;
//			SmartDashboard.putString("Selected Auto", "Test Drive");
//			autonomousCommand = testDrive;
		}
		
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
		SmartDashboard.putNumber("Robot heading", drivetrain.getAngle());
		SmartDashboard();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		Robot.drivetrain.shiftUp();      // High gear for Teleop
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		// makes sure only one command per subsystems runs at a time
		Scheduler.getInstance().run();
		
		SmartDashboard();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		// brings up a window with the state of the robot parts
		LiveWindow.run();
	}
	
	public void SmartDashboard() {
		// Motor Voltages
//		SmartDashboard.putNumber("Shooter Current", shooter.getMotor1Current());
//		SmartDashboard.putNumber("Gear Grabber Current", gearGrabber.getOutputCurrent());
//		SmartDashboard.putNumber("Feeder Current", feeder.getFeederCurrent());
//		SmartDashboard.putNumber("Front Left Drivetrain Current", drivetrain.getFrontLeft().getOutputCurrent());
//		SmartDashboard.putNumber("Front Right Drivetrain Current", drivetrain.getFrontRight().getOutputCurrent());
//		SmartDashboard.putNumber("Rear Left Drivetrain Current", drivetrain.getRearLeft().getOutputCurrent());
//		SmartDashboard.putNumber("Rear Right Drivetrain Current", drivetrain.getRearRight().getOutputCurrent());
//		SmartDashboard.putNumber("Climber 1 Current", tiltingWall.getMotor1Current());
//		SmartDashboard.putNumber("Climber 2 Current", tiltingWall.getMotor2Current());

		// Drivetrain
//		SmartDashboard.putBoolean("Omnis Deployed", drivetrain.getOmniState());
//		SmartDashboard.putString("Drivetrain Gear State", drivetrain.getShifters());
//		SmartDashboard.putNumber("Left Drivetrain Speed", drivetrain.getRearLeft().getSpeed());
//		SmartDashboard.putNumber("Right Drivetrain Speed", drivetrain.getRearRight().getSpeed());

		// Autonomous
//		SmartDashboard.putData("Auto mode", chooser);
		
		// Gear Grabber
//		SmartDashboard.putBoolean("Has Gear", gearGrabber.hasGear());
		
		// Shooter
//		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		
		// Climber
//		SmartDashboard.putData("Climber State", tiltingWall);
//		SmartDashboard.putNumber("Climber Power", Robot.oi.getClimberPower());
		
		// Light Ring
//		SmartDashboard.putData("Light Ring Colour", lightRing);
		
	}
}
