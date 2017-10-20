package org.usfirst.frc.team2363.util.pathplanning;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SrxTrajectoryPoint {
	
	private double position;
	private double rpm;
	private int dt;
	
	@JsonCreator
	SrxTrajectoryPoint() { }
	
	public SrxTrajectoryPoint(double position, double rpm,  int dt) {
		this.position = position;
		this.rpm = rpm;
		this.dt = dt;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public double getRpm() {
		return rpm;
	}

	public void setRpm(double rpm) {
		this.rpm = rpm;
	}

	public int getDt() {
		return dt;
	}

	public void setDt(int dt) {
		this.dt = dt;
	}

}
