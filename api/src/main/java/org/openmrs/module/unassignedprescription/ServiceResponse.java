package org.openmrs.module.unassignedprescription;

import java.time.LocalDateTime;

public class ServiceResponse {
	
	private boolean response;
	
	private Object data;
	
	private String message;
	
	private LocalDateTime localDateTime;
	
	public ServiceResponse(boolean response) {
		this.response = response;
	}
	
	public ServiceResponse(boolean response, Object data) {
		this.response = response;
		this.data = data;
	}
	
	public ServiceResponse(boolean response, String message) {
		this.response = response;
		this.message = message;
	}
	
	public ServiceResponse(boolean response, Object data, LocalDateTime localDateTime) {
		this.response = response;
		this.data = data;
		this.localDateTime = localDateTime;
	}
	
	public ServiceResponse(boolean response, Object data, String message, LocalDateTime localDateTime) {
		this.response = response;
		this.data = data;
		this.message = message;
		this.localDateTime = localDateTime;
	}
	
	public boolean isResponse() {
		return response;
	}
	
	public void setResponse(boolean response) {
		this.response = response;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
	
	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
}
