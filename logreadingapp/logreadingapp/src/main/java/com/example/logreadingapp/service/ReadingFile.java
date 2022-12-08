package com.example.logreadingapp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

import org.hibernate.annotations.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.logreadingapp.LogreadingappApplication;
import com.example.logreadingapp.constant.LogAppConstant;
import com.example.logreadingapp.model.LogEvent;
import com.example.logreadingapp.model.ServerLog;
import com.example.logreadingapp.repo.LogEventRpo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReadingFile {
	@Autowired
	LogEventService logEventService;
	private static final Logger logger = LoggerFactory.getLogger(ReadingFile.class);

	public List<LogEvent> readfile(String fileName) throws FileNotFoundException {

		String host = null;
		String type = null;
		int count = 0;
		File file = new File(fileName);
		Scanner input = new Scanner(file);
		List<LogEvent> logEventlist = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			Map<String, ServerLog> eventMap = new LinkedHashMap<>();
			String readLine;

			while ((readLine = br.readLine()) != null) {

				ObjectMapper objectMapper = new ObjectMapper();
				ServerLog serverLog = objectMapper.readValue(readLine, ServerLog.class);
				Optional<String> optServer = Optional.ofNullable(serverLog.getHost());
				if (optServer.isPresent()) {
					host = optServer.get();

				}
				Optional<String> optType = Optional.ofNullable(serverLog.getType());
				if (optType.isPresent()) {
					type = optType.get();

				}
				if (eventMap.containsKey(serverLog.getId())) {
					getDuration(host, type, eventMap, serverLog);
				} else {
					eventMap.put(serverLog.getId(), serverLog);
				}

			}

			logEventlist = logEventService.getAllEvents();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logEventlist;
	}



	private void getDuration(String host, String type, Map<String, ServerLog> eventMap, ServerLog serverLog) {
		ServerLog temp = eventMap.get(serverLog.getId());
		if (temp.getState().equals(LogAppConstant.FINISHED)
				&& serverLog.getState().equals(LogAppConstant.STARTED)) {
			long duration = Math.abs(temp.getTimestamp() - serverLog.getTimestamp());
			boolean alert = false;
			setlogevent(host, serverLog, duration, alert, type);
		} else if (temp.getState().equals(LogAppConstant.STARTED)
				&& serverLog.getState().equals(LogAppConstant.FINISHED))

		{
			long duration = Math.abs(serverLog.getTimestamp() - temp.getTimestamp());
			boolean alert = false;
			setlogevent(host, serverLog, duration, alert, type);

		}
	}

	private void setlogevent(String host, ServerLog serverLog, long duration, boolean alert, String type) {
		if (duration > LogAppConstant.DURATION) {
			alert = true;
		}
		LogEvent event = new LogEvent();

		event.setDuration(duration);
		event.setalert(alert);
		event.setHost(host);
		event.setId(serverLog.getId());
		event.setType(type);
		try {
			logEventService.saveLogEvent(event);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
