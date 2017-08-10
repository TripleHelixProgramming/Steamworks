package org.iif.th.util.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelixLogger {
	
	private final Map<String, Function<Object, String>> dataSources = new HashMap<>();
	
	private final Path file;
	private boolean savedTitles;
	
	public HelixLogger() {
		file = Paths.get("/home/lvuser/Log0.txt");
		try {
			cleanUpFiles();
			Files.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSource(String name, Function<Object, String> f) {
		dataSources.put(name, f);
	}
	
	public void saveLogs() {
		try {
			if (!savedTitles) {
				saveTitles();
				savedTitles = true;
			}
			StringBuilder data = new StringBuilder();
			data.append(Instant.now().toString()).append("\t");
			data.append(getValues());
			
			Files.write(file, Collections.singletonList(data.toString()), StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveTitles() throws IOException {
		StringBuilder titles = new StringBuilder();
		titles.append("Timestamp\t");
		titles.append(getTitles(dataSources)).append("\t");
		Files.write(file, Collections.singletonList(titles.toString()), StandardOpenOption.APPEND);
	}
	
	private String getTitles(Map<String, ?> map) {
		return String.join("\t", map.keySet());
	}
	
	private void cleanUpFiles() throws IOException {
		Files.deleteIfExists(Paths.get("/home/lvuser/Log4.txt"));
		for (int i = 3; i >= 0; i--) {
			Path oldFile = Paths.get("/home/lvuser/Log" + i + ".txt");
			if (Files.exists(oldFile)) {
				Files.move(oldFile, oldFile.resolveSibling("/home/lvuser/Log" + (i + 1) + ".txt"));
			}
		}
	}
	
	private String getValues() {
		return String.join("\t", 
				dataSources.values().stream()
				.map(object -> (object != null ? object.toString() : null))
				.collect(Collectors.toList()));
	}
}
