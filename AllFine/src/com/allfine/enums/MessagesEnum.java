package com.allfine.enums;

public enum MessagesEnum {
	MI_SUCCESSFUL(1000, "success"),

	MI_APP_VERSION_INCORRECT(1022, "app version incorrect"),

	MI_TOKEN_ERROR(1002, "token error"),

	MI_EXCEPTION_ERROR(6, "token error"),

	MI_NO_NETWORK_CONNECTION(2, "token error"),

	MI_NO_INTERNET_CONNECTION(1, "token error"),

	MI_SERVER_CONNECTION_PROBLEM(7, "token error");
	
//	public final static int APP_VERSION_OLD = 1021;
//	public final static int SERVER_ERROR_1 = 1015;
//	public final static int SERVER_ERROR_2 = 1014;
//	public final static int SERVER_ERROR_3 = 1013;
//	public final static int MISSING_DATA = 1003;
//	public final static int NO_SUCH_USER = 1004;
//	public final static int NOT_FOUND_DATA_ERROR = 1003;
//	public final static int APP_GCM_EMPTY = 1001;
//	/*
//	 * Local Message codes. Must be different values
//	 */
//	public ;
//	public final static int 
//	public final static int
//	public final static int ;
//	// public final static int UN_SUCCESSFUL = 16;

	private MessagesEnum(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	private int id;
	private String message;
}
