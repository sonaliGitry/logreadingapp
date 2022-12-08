package com.example.logreadingapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class LogEvent {
	@Id
	private String id;
    public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "LogEvent [id=" + id + ", duration=" + duration + ", alert=" + alert + ", type=" + type + ", host=" + host
				+ "]";
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public boolean isalert() {
		return alert;
	}
	public void setalert(boolean alert) {
		this.alert = alert;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	private long duration;
    private boolean alert;
    private String type;
    private String host;

}
