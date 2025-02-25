package com.hannsoftware.ticketmanagementsystem.domain.util;

import io.micrometer.common.util.StringUtils;

public enum Priority {
	
	LOW(0, "Low"), MEDIUM(1, "Medium"), HIGH(2, "High");
	
	private Integer code;
	
	private String description;
	
	private Priority(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Priority toEnum(Integer code) {
		if(code == null) return null;
		for(Priority priority: Priority.values()) {
			if(code.equals(priority)) {
				return priority;
			}
		}
		throw new IllegalArgumentException("Invalid Priority!");
	}
	
	public static Priority toEnum(String value) {
		if(value == null || StringUtils.isBlank(value)) return null;
		for (Priority priority: Priority.values()) {
			if(value.equalsIgnoreCase(priority.getDescription())) {
				return priority;
			}
		}
		throw new IllegalArgumentException("Invalid Priority!");
	}

}
