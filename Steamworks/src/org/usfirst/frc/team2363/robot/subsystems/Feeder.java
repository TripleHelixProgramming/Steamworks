package org.usfirst.frc.team2363.robot.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.feeder.FeederCommand;

/**
 *
 */
public class Feeder extends Subsystem {

	CANTalon feeder = new CANTalon(FEEDER_TALON);
	CANTalon serializer = new CANTalon(SERIALIZER_TALON);
	CANTalon feeder2 = new CANTalon(FEEDER2_TALON);
	
	public void on() {
		feeder.set(-1);
		serializer.set(.5);
		feeder2.set(1);
	}
	
	public void off() {
		feeder.set(0);
		serializer.set(0);
		feeder2.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new FeederCommand(false));
    }
    
    public double getFeederCurrent() {
    	return feeder.getOutputCurrent();
	}
}

