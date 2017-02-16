package org.usfirst.frc.team2363.util;

public class DrivetrainMath {
	
	public static int ticksPerWheelRotation(int encoderTicks, double gearRatio) {
		return (int)(encoderTicks * 3 * gearRatio);
	}
	
	public static double fGain(int encoderTicks, double gearRatio, int RPM) {
		return 1023.0 / (rpmToNup100ms(encoderTicks, gearRatio, RPM));
	}
	
	public static int fpsToRpm(double fps, double wheelDiameter) {
		return (int)(fps * 60.0 * 12.0 / (wheelDiameter * Math.PI));
	}
	
	public static double rpmToNup100ms(int encoderTicks, double gearRatio, int RPM) {
		return RPM * (1.0 / 600.0) * ticksPerWheelRotation(encoderTicks, gearRatio);
	}
}
