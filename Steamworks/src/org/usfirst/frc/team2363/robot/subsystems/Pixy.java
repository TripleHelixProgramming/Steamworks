package org.usfirst.frc.team2363.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.usfirst.frc.team2363.robot.Robot.pixy;

import java.util.Optional;

import org.usfirst.frc.team2363.robot.commands.PixyCam.*;

/**
 *
 */
public class Pixy{
	
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
	
	//This method parses raw data from the I2C port into Pixy Packet's X , Y, Width, Height, Signature.
	public Optional<PixyPacket> readPixyPacket(){
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
			packet.Area = packet.Height * packet.Width;
		
		}
	/*	int largestArea = 0;
		int targetX = 0;
		int targetY = 0;
		
		if (largestArea < packet.Area){
			largestArea = packet.Area;
			targetX = packet.X;
			targetY = packet.Y;
		}*/
		
		return Optional.of(packet);
	}
	
	public Optional<Double> getTargetAngle() {
		Optional<PixyPacket> target = readPixyPacket();
		
		double screenWidth = 640;
		double screenHeight = 400;
		double horizontalAngle = 75;
		double verticleAngle = 47;
	
		double turnAngle = (target.get().X - (screenWidth / 2)) * (horizontalAngle / screenWidth);
		return Optional.of(turnAngle);
	}
	
	public Optional<PixyPacket> getTarget() {
		return pixy.readPixyPacket();
	}
}

