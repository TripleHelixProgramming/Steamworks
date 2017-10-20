package org.usfirst.frc.team2363.util.pathplanning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoTHTrajectory {
	
	private SrxTrajectory trajectory;
	
	public SrxTrajectory getTrajectory() {
		return trajectory;
	}
	
	public void setTrajectory(SrxTrajectory trajectory) {
		this.trajectory = trajectory;
	}
}
