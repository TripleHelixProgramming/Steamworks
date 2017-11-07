package org.iif.th.util.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HelixLogger {
	
	private final List<LogSource> dataSources = new ArrayList<>();
	private final Path file;
	private boolean savedTitles;
	
	public HelixLogger() {
		file = Paths.get("/home/lvuser/Log0.csv");
		try {
			cleanUpFiles();
			Files.createFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSource(String name, Object source, Function<Object, String> f) {
		dataSources.add(new LogSource(name, source, f));
	}
	
	public void saveLogs() {
//		if (!DriverStation.getInstance().isFMSAttached()) {
//			return;
//		}
		try {
			if (!savedTitles) {
				saveTitles();
				savedTitles = true;
			}
			StringBuilder data = new StringBuilder();
			data.append(Instant.now().toString()).append(",");
			data.append(getValues());
			Files.write(file, Collections.singletonList(data.toString()), StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveTitles() throws IOException {
		StringBuilder titles = new StringBuilder();
		titles.append("Timestamp,");
		titles.append(dataSources.stream().map(t -> t.name).collect(Collectors.joining(","))).append(",");
		Files.write(file, Collections.singletonList(titles.toString()), StandardOpenOption.APPEND);
	}
	
	private void cleanUpFiles() throws IOException {
		Files.deleteIfExists(Paths.get("/home/lvuser/Log4.csv"));
		for (int i = 3; i >= 0; i--) {
			Path oldFile = Paths.get("/home/lvuser/Log" + i + ".csv");
			if (Files.exists(oldFile)) {
				Files.move(oldFile, oldFile.resolveSibling("/home/lvuser/Log" + (i + 1) + ".csv"));
			}
		}
	}
	
	private String getValues() {
		return dataSources.stream().map(s -> s.callback.apply(s.source).toString()).collect(Collectors.joining(","));
	}
	
	private class LogSource {
		private final String name;
		private final Object source;
		private final Function<Object, String> callback;
		
		public LogSource(String name, Object source, Function<Object, String> callback) {
			this.name = name;
			this.source = source;
			this.callback = callback;
		}
	}
}
