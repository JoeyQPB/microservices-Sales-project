package com.joey.ProductServer.exception;

public class ControllerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ControllerException(Throwable e) {
		super(e);
	}
}
