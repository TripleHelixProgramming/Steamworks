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

/*	private static SerialPort pixy_port;
	// private static Port port = Port.kMXP;   
	// NavX is on the port above, so using kOnBoard
	private static Port port = Port.kOnboard;
	private static PixyPacket[] packets;
	PixyException pExc;
	private static String print;
*/
	public PixyState state;

	public Pixy() {
		/*
		pixy_port = new SerialPort(19200, port);
		pixy_port.setReadBufferSize(14);
		packets = new PixyPacket[7];
		pExc = new PixyException(print);
		*/
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
	
	/*
	 * public void pixyReset(){
	
		pixy_port.reset();
	}
	 */
	
	//This method parses raw data from the pixy_port into readable integers
/*	public int cvt(byte upper, byte lower) {
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
*/	
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
	//This method gathers data, then parses that data, and assigns the ints to global variables
	/* public PixyPacket readPacket(int Signature) throws PixyException {
		int Checksum;
		int Sig;
		byte[] rawData = new byte[32];
		
		try{
			rawData = pixy_port.read(32);
		} catch (RuntimeException e){
		}
		
		if(rawData.length < 32){
			System.out.println("byte array length is broken");
			return null;
		}
		
		for (int i = 0; i <= 16; i++) {
			int syncWord = cvt(rawData[i+1], rawData[i+0]); //Parse first 2 bytes
			if (syncWord == 0xaa55) { //Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
				syncWord = cvt(rawData[i+3], rawData[i+2]); //Parse the next 2 bytes
				if (syncWord != 0xaa55){ //Shifts everything in the case that one syncword is sent
					i -= 2;
				}
				
				//This next block parses the rest of the data
				Checksum = cvt(rawData[i+5], rawData[i+4]);
				Sig = cvt(rawData[i+7], rawData[i+6]);
				if(Sig <= 0 || Sig > packets.length){
					break;
				}
				packets[Sig - 1] = new PixyPacket();
				packets[Sig - 1].X = cvt(rawData[i+9], rawData[i+8]);
				packets[Sig - 1].Y = cvt(rawData[i+11], rawData[i+10]);
				packets[Sig - 1].Width = cvt(rawData[i+13], rawData[i+12]);
				packets[Sig - 1].Height = cvt(rawData[i+15], rawData[i+14]);
				
				//Checks whether the data is valid using the checksum *This if block should never be entered*
				if (Checksum != Sig + packets[Sig - 1].X + packets[Sig - 1].Y + packets[Sig - 1].Width + packets[Sig - 1].Height) {
					packets[Sig - 1] = null;
					throw pExc;
				}
				break;
			}
		}
		//Assigns our packet to a temp packet, then deletes data so that we dont return old data
		PixyPacket pkt = packets[Signature - 1];
		packets[Signature - 1] = null;
		return pkt;
	}
	
	public void updateSmartDash () {
		
	}
	*/
}

