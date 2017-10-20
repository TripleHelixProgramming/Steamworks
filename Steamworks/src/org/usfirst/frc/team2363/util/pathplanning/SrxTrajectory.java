package org.usfirst.frc.team2363.util.pathplanning;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

//Combines left and right motion profiles in one object
public class SrxTrajectory {
	
	private List<SrxTrajectoryPoint> leftProfile;
	private List<SrxTrajectoryPoint> rightProfile;
	
	@JsonCreator
	public SrxTrajectory() { }
	
	public SrxTrajectory(List<SrxTrajectoryPoint> left, List<SrxTrajectoryPoint> right) {
		this.leftProfile = left;
		this.rightProfile = right;
	}
	
	public List<SrxTrajectoryPoint> getLeftProfile() {
		return leftProfile;
	}
	public void setLeftProfile(List<SrxTrajectoryPoint> leftProfile) {
		this.leftProfile = leftProfile;
	}
	public List<SrxTrajectoryPoint> getRightProfile() {
		return rightProfile;
	}
	public void setRightProfile(List<SrxTrajectoryPoint> rightProfile) {
		this.rightProfile = rightProfile;
	}
}
