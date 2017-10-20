package org.usfirst.frc.team2363.util.pathplanning;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ctre.CANTalon.TrajectoryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SrxPathReader {
	
	public static List<TrajectoryPoint> getTrajectoryPoints(List<SrxTrajectoryPoint> points) {
		List<TrajectoryPoint>  trajectoryPoints = new ArrayList<>();
		for (SrxTrajectoryPoint point : points) {
			TrajectoryPoint trajectoryPoint = new TrajectoryPoint();
			trajectoryPoint.position = point.getPosition();
			trajectoryPoint.velocity = point.getRpm();
			trajectoryPoint.timeDurMs = point.getDt();
			trajectoryPoints.add(trajectoryPoint);
		}
		if (!trajectoryPoints.isEmpty()) {
			trajectoryPoints.get(0).zeroPos = true;
			TrajectoryPoint trajectoryPoint = new TrajectoryPoint();
			trajectoryPoint.isLastPoint = true;
			trajectoryPoint.position = trajectoryPoints.get(trajectoryPoints.size() - 1).position;
			trajectoryPoint.velocity = 0.0;
			trajectoryPoint.timeDurMs = 0;
			trajectoryPoints.add(trajectoryPoint);
		}
		return trajectoryPoints;
	}

	public static BoTHTrajectory importSrxTrajectory(String fileName){
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new File("/home/lvuser/" + fileName + "_SrxTrajectory.json"), BoTHTrajectory.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void main(String... args) {
		BoTHTrajectory trajectory = importSrxTrajectory("scaling_calibration");
		List<TrajectoryPoint> left = getTrajectoryPoints(trajectory.getTrajectory().getLeftProfile());
		for (TrajectoryPoint point : left) {
			System.out.println(point.timeDurMs);
		}
	}
}
