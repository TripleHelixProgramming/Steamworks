package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import static org.usfirst.frc.team2363.robot.RobotMap.*;

import org.usfirst.frc.team2363.robot.commands.lightRing.LightRingOperator;

/**
 *This is the subsystem for the light ring control.
 *The RoboRIO talks to the LED microcontroller through the relay port defined in RobotMap.
 *The settings for the relay are as follows:
 *kOff: turn the LED off
 *kOn: signal both green and red LEDs
 *kForward: signal the red LED
 *kReverse: signal the green LED
 */
public class LightRing extends Subsystem {

    Relay light = new Relay(LIGHT_RING_RELAY);
    
    /**
     * Turns the light ring off
     */
    public void off() {
    	light.set(Relay.Value.kOff);
    }
    
    /**
     * Oscillate between green and red
     */
    public void both() {
    	light.set(Relay.Value.kOn);
    }
    
    /**
     * Turns the ring to green
     */
    public void green() {
    	light.set(Relay.Value.kReverse);
    	
    }
    
    /**
     * Turns the ring to red
     */
    public void red() {
    	light.set(Relay.Value.kForward);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new LightRingOperator());
    }
}

