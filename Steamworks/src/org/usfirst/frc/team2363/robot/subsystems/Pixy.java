package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2363.robot.Robot.pixy;

import org.usfirst.frc.team2363.robot.commands.PixyCam.*;

/**
 *
 */
public class Pixy extends Subsystem {
	
	// declares the different Pixy Cam communication states
    public enum PixyState {
    	ON,
    	OFF
    }
    public static I2C pixyi2c;
	public PixyState state;

	public Pixy() {
		
		pixyi2c = new I2C(I2C.Port.kOnboard, 0x54);
		state = PixyState.OFF;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new NoTargetAcquire());
    }

	public void on() {
		PixyPacket Target;
		
		state = PixyState.ON;
		SmartDashboard.putNumber("Pixy", 1);
		
	    
    	SmartDashboard.putNumber("Acquiring Target", 1);
    	Target = pixy.readPixyPacket();
    		
    	SmartDashboard.putNumber("xPosition", Target.X);
    	SmartDashboard.putNumber("yPosition", Target.Y);
    	SmartDashboard.putNumber("width", Target.Width);
    	SmartDashboard.putNumber("height", Target.Height);
    	SmartDashboard.putNumber("Raw 5", Target.Sig);
	}
	
	public void off() {
		state = PixyState.OFF;
		SmartDashboard.putNumber("Pixy", 0);
	}
	
	public boolean isOn() {
		return (state == PixyState.ON);
	}
	
	public boolean isOff() {
		return (state == PixyState.OFF);
	}
	
	//This method parses raw data from the I2C port into Pixy Packet's X , Y, Width, Height, Signature.
	public PixyPacket readPixyPacket(){
		byte[] pixyValues = new byte[64];
		pixyValues[0] = (byte) 0b01010101;
		pixyValues[1] = (byte) 0b10101010;
		PixyPacket packet = new PixyPacket();
		pixyi2c.readOnly(pixyValues, 64);
		if (pixyValues != null) {
			int i = 0;
			while (!(pixyValues[i] == 85 && pixyValues[i + 1] == -86) && i < 50) {
				i++;
			}
			i++;
			if (i > 50)
				i = 49;
			while (!(pixyValues[i] == 85 && pixyValues[i + 1] == -86) && i < 50) {
				i++;
			}
			char xPosition = (char) (((pixyValues[i + 7] & 0xff) << 8) | (pixyValues[i + 6] & 0xff));
			char yPosition = (char) ((pixyValues[i + 9] & 0xff << 8) | pixyValues[i + 8] & 0xff);
			char width = (char) ((pixyValues[i + 11] & 0xff << 8) | pixyValues[i + 10] & 0xff);
			char height = (char) ((pixyValues[i + 13] & 0xff << 8) | pixyValues[i + 12] & 0xff);
			
			packet.X = xPosition;
			packet.Y = yPosition;
			packet.Width = width;
			packet.Height = height;
			packet.Sig = pixyValues[5];
		
		}
		return packet;
	}
	
	public double autoAllign() {
		PixyPacket target = readPixyPacket();
		
		double screenWidth = 640;
		double screenHeight = 400;
		double horizontalAngle = 75;
		double verticleAngle = 47;
	
		double turnAngle = (target.X - (screenWidth / 2)) * (horizontalAngle / screenWidth);
		return turnAngle;
	}
}

