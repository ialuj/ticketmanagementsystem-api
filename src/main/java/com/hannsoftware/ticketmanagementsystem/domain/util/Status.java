package com.hannsoftware.ticketmanagementsystem.domain.util;

import io.micrometer.common.util.StringUtils;

public enum Status {
	
    NEW(0, "New"), IN_PROGRESS(1, "In Progress"), RESOLVED(2, "Resolved");
	
	private Integer code;
	
	private String description;
	
	private Status(Integer code, String description) {
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

	public static Status toEnum(Integer code) {
		if(code == null) return null;
		for(Status status: Status.values()) {
			if(code.equals(status)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid Status!");
	}
	
	public static Status toEnum(String value) {
		if(value == null || StringUtils.isBlank(value)) return null;
		for (Status status: Status.values()) {
			String description = status.getDescription();
			if(value.equalsIgnoreCase(description)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Invalid Status!");
	}

}
