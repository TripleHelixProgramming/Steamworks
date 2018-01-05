package org.iif.th.util.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.DriverStation;

public class HelixLogger {

	private final List<LogSource> dataSources = new ArrayList<>();
	private Path file;
	private boolean logWhenNotConnected;
	
	private String eventName;
	private int matchNumber;
	private final Properties prop = new Properties();
	private String loggingLocation = "/home/lvuser/logs/";
	
	public HelixLogger(){
		File usb1 = new File("/media/sda1/");
		if (usb1.exists()) {
			loggingLocation = "/media/sda1/logs/";
		}
		getConfig();
	}
	
	private void getConfig() {
		InputStream input = null;
		try {
			input = new FileInputStream("/home/lvuser/logs/logger.properties");
			prop.load(input);
			logWhenNotConnected = Boolean.valueOf(prop.getProperty("logWhenNotConnected", "false"));
			if (logWhenNotConnected) {
				eventName = "test";
			} else {
				eventName = prop.getProperty("eventName", "default");
			}
			matchNumber = Integer.valueOf(prop.getProperty("matchNumber", "0"));
		} catch (IOException ex) { 
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) { }
			}
		}
	}
	
	private void createLogDirectory() throws IOException {
		File logDirectory = new File(loggingLocation);
		if (!logDirectory.exists()) {
			Files.createDirectory(Paths.get(loggingLocation));
		}
	}
	
	private void createFile() {
		Writer output = null;
		try {
			createLogDirectory();
			file = Paths.get(loggingLocation + eventName + "_"+ matchNumber + ".csv");
			Files.createFile(file);
			prop.setProperty("matchNumber", "" + (matchNumber + 1));
			saveTitles();
			output = new FileWriter("/home/lvuser/logs/logger.properties");
			prop.store(output, "");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) { }
			}
		}
	}

	public void addSource(String name, Object source, Function<Object, String> f) {
		dataSources.add(new LogSource(name, source, f));
	}

	public void saveLogs() {
		if (!logWhenNotConnected && !DriverStation.getInstance().isFMSAttached()) {
			return;
		}
		
		try {
			if (file == null) {
				createFile();
			}

			StringBuilder data = new StringBuilder();
			data.append(Instant.now().toString()).append(",");
			data.append(DriverStation.getInstance().getMatchTime()).append(",");
			data.append(getValues());
			Files.write(file, Collections.singletonList(data.toString()), StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTitles() throws IOException {
		StringBuilder titles = new StringBuilder();
		titles.append("Timestamp,");
		titles.append("match_time");
		titles.append(dataSources.stream().map(t -> t.name).collect(Collectors.joining(","))).append(",");
		Files.write(file, Collections.singletonList(titles.toString()), StandardOpenOption.APPEND);
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
