package com.hannsoftware.ticketmanagementsystem.domain.util;

import io.micrometer.common.util.StringUtils;

public enum Category {
	
    NETWORK(0, "Network"), HARDWARE(1, "Hardware"), SOFTWARE(2, "Software"), OTHER(3, "Other");
	
	private Integer code;
	
	private String description;
	
	private Category(Integer code, String description) {
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

	public static Category toEnum(Integer code) {
		if(code == null) return null;
		for(Category category: Category.values()) {
			if(code.equals(category)) {
				return category;
			}
		}
		throw new IllegalArgumentException("Invalid Category!");
	}
	
	public static Category toEnum(String value) {
		if(value == null || StringUtils.isBlank(value)) return null;
		for (Category category: Category.values()) {
			if(value.equalsIgnoreCase(category.getDescription())) {
				return category;
			}
		}
		throw new IllegalArgumentException("Invalid Category!");
	}

}
