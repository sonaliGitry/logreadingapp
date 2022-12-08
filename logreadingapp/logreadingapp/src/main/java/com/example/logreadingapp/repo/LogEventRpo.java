package com.example.logreadingapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.logreadingapp.model.LogEvent;
@Repository
public interface LogEventRpo  extends JpaRepository<LogEvent, Integer>{

}
