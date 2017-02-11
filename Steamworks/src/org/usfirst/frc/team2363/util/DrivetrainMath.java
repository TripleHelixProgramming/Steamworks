package org.usfirst.frc.team2363.util;

public class DrivetrainMath {
	
	public static int ticksPerWheelRotation(int encoderTicks, double gearRatio) {
		return (int)(encoderTicks * 3 * gearRatio);
	}
	
	public static double fGain(int encoderTicks, double gearRatio, int RPM) {
		return 1023.0 / (RPM * (1.0 / 600.0) * ticksPerWheelRotation(encoderTicks, gearRatio));
	}
	
	public static double RPM(double feetPerSec) {
		return (feetPerSec * 60.0 * 12.0) / (4.0 * Math.PI);
	}
}
