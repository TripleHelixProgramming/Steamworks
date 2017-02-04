package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;

/**
 *
 */
public class Feeder extends Subsystem {

	CANTalon motor = new CANTalon(FEEDER_TALON);
	
	public void on() {
		motor.set(-0.5);
	}
	
	public void off() {
		motor.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new FeederCommand(false));
    }
}

