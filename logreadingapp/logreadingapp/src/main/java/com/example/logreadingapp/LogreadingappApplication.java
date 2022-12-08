package com.example.logreadingapp;

import org.apache.logging.log4j.Logger;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.logreadingapp.model.LogEvent;
import com.example.logreadingapp.service.ReadingFile;

@SpringBootApplication
public class LogreadingappApplication implements CommandLineRunner {
	static Logger logger = LogManager.getRootLogger();

	@Autowired
	ReadingFile readingFile;

	public static void main(String[] args) {
		logger.info("LogreadingappApplication started..............");
		SpringApplication.run(LogreadingappApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		List<LogEvent> logEventlist = readingFile
//				.readfile("C:\\logreadingapp\\logreadingapp\\src\\main\\resources\\logFile.txt");
		List<LogEvent> logEventlist = readingFile
				.readfile(args[0]);

		System.out.println("ID\t DURATION\tAlert\tHOST\tTYPE");

		logEventlist.forEach(i -> System.out.println(
				i.getId() + "\t" + i.getDuration() + "\t" + i.isalert() + "\t" + i.getHost() + "\t" + i.getType()));

	}

}
