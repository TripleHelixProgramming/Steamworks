package org.usfirst.frc.team2363.util;

public class DrivetrainMath {
	
	int ticksPerWheelRotation(int encoderTicks, double gearRatio) {
		return (int)(encoderTicks * 3 * gearRatio);
	}
	
	double fGain(int encoderTicks, double gearRatio, int RPM) {
		return 1023 / (RPM * (1 / 600) * ticksPerWheelRotation(encoderTicks, gearRatio));
	}
}
