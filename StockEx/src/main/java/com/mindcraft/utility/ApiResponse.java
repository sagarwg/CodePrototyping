package com.mindcraft.utility;

public class ApiResponse<T> {
	private String message;
	private T data;
	private boolean success;
	private String exception; 
	private int status;

	public ApiResponse(String message, T data, boolean success, String exception, int status) {
		this.message = message;
		this.data = data;
		this.success = success;
		this.exception = exception;
		this.status = status;
	}

//	// Minimal constructor +ve response
//	public ApiResponse(String message, T data, boolean success) {
//		this.message = message;
//		this.data = data;
//		this.success = success;
//	}
//
//	// Minimal constructor -ve delete response
//	public ApiResponse(String message, boolean success) {
//		this.message = message;
//		this.success = success;
//	}
//
//	// Minimal constructor +ve delete response
//	public ApiResponse(String message, boolean success, String exception) {
//		this.message = message;
//		this.success = success;
//		this.exception = exception;
//	}

	// Getters and Setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
