package com.example.logreadingapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.logreadingapp.model.LogEvent;
import com.example.logreadingapp.repo.LogEventRpo;

@Service
public class LogEventService {
	@Autowired
	private LogEventRpo logEventRpo;
	
	public LogEvent  saveLogEvent(LogEvent logEvent)
	{
	return logEventRpo.save(logEvent);
	}

	public List<LogEvent> getAllEvents() {
		List<LogEvent> logEventlist;
		logEventlist = logEventRpo.findAll();
		return logEventlist;
	}
}
