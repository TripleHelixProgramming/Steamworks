package org.usfirst.frc.team2363.util;

public class PathStep {
	
	private final double timestamp;
	private final double leftSpeed;
	private final double rightSpeed;

	public PathStep(double timestamp, double leftSpeed, double rightSpeed) {
		this.timestamp = timestamp;
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}
	
	public double getTimestamp() {
		return timestamp;
	}
	
	public double getLeftSpeed() {
		return leftSpeed;
	}
	
	public double getRightSpeed() {
		return rightSpeed;
	}
}
