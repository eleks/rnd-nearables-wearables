package com.eleks.controller.params;

public class MovementParam {
	private Long timestamp;
	private String type;
	private String stikerId;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStikerId() {
		return stikerId;
	}

	public void setStikerId(String stikerId) {
		this.stikerId = stikerId;
	}

	@Override
	public String toString() {
		return "MovementParam [timestamp=" + timestamp + ", type=" + type + ", stikerId=" + stikerId + "]";
	}

}
