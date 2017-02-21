package org.usfirst.frc.team2363.util;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Pixy {

	public static I2C pixyi2c;
	public static PixyPacket previousPkt = new PixyPacket();

	public Pixy() {
		pixyi2c = new I2C(I2C.Port.kOnboard, 0x54);
	}

	private void UpdateSmartDash(PixyPacket Target) {

		SmartDashboard.putNumber("xPosition", Target.X);
		SmartDashboard.putNumber("yPosition", Target.Y);
		SmartDashboard.putNumber("width", Target.Width);
		SmartDashboard.putNumber("height", Target.Height);
		SmartDashboard.putNumber("Signature", Target.Sig);
		SmartDashboard.putNumber("Area", Target.Area);
	}

	//This method parses raw data from the I2C port into Pixy Packet's X , Y, Width, Height, Signature.
	public Optional<PixyPacket> readPixyPacket(){
		byte[] pixyValues = new byte[64];
		pixyValues[0] = (byte) 0b01010101;
		pixyValues[1] = (byte) 0b10101010;

		PixyPacket packet = new PixyPacket();
		
		pixyi2c.readOnly(pixyValues, 64);
		if (pixyValues == null) {
			SmartDashboard.putString("Target Angle", "No Target");
			return Optional.empty();
		}
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

		UpdateSmartDash(packet);

		return Optional.of(packet);
	}

	public Optional<Double> getTargetAngle() {
		Optional<PixyPacket> target = readPixyPacket();
		
		double screenWidth = 320;
		double horizontalAngle = 75;
		
		// Running through filters.
		
		// Ensure the packet was not null
		if (!target.isPresent()) {
			target = Optional.of(previousPkt);
			//  return Optional.empty();
		} 
		
		// Height and Width of the target should not be 0
		if (target.get().Height == 0 && target.get().Width == 0) {
			target = Optional.of(previousPkt);
			// return Optional.empty();
		}
		
		// X should range 0 - 320
		if ((target.get().X < 0) || (target.get().X > 320)) {
			target = Optional.of(previousPkt);
		} else {
			previousPkt.X = target.get().X;
			previousPkt.Y = target.get().Y;
			previousPkt.Height = target.get().Height;
			previousPkt.Width = target.get().Width;
			previousPkt.Sig = target.get().Sig;
			previousPkt.Area = target.get().Area;
		}
		
// 		double  turnAngle = (((target.get().X / screenWidth) * horizontalAngle) - (horizontalAngle / 2)) - 13.125;
		double turnAngle = (((target.get().X / screenWidth) * horizontalAngle) - (horizontalAngle / 2));
		
		DriverStation.reportError("Turn Angle : " + turnAngle, false);
		return Optional.of(turnAngle);
	}
}

