package org.usfirst.frc.team2363.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PathReader {
	
	public static List<PathStep> getPathSteps(String fileName) {
		Path path = FileSystems.getDefault().getPath(fileName);
		try {
			return Files.lines(path).map(line -> createPathStep(line)).collect(Collectors.toList());
		} catch (IOException e) {
			
		}
		return Collections.emptyList();
	}

	private static PathStep createPathStep(String line) {
		String[] stepData = line.split("\t");
		return new PathStep(Double.parseDouble(stepData[0]), Double.parseDouble(stepData[1]), Double.parseDouble(stepData[2]));
	}
}
