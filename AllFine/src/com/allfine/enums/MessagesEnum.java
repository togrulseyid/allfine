package com.allfine.enums;

import com.allfine.R;

public enum MessagesEnum {

	// MI_SUCCESSFUL(1000, "success"),
	MI_SUCCESSFUL(1000, 1000),

	// MI_APP_VERSION_INCORRECT(1022, "app version incorrect"),
	MI_APP_VERSION_INCORRECT(1022, R.string.message_error_app_version_incorrect),

	// MI_TOKEN_ERROR(1002, "token error"),
	MI_TOKEN_ERROR(1002, R.string.message_error_token),

	// MI_EXCEPTION_ERROR(6, "token error"),
	MI_EXCEPTION_ERROR(6, R.string.message_error_exception),

	// MI_NO_NETWORK_CONNECTION(2, "No Network connection"),
	MI_NO_NETWORK_CONNECTION(2, R.string.message_error_no_network_connection),

	// MI_NO_INTERNET_CONNECTION(1, "No Internet Connection"),
	MI_NO_INTERNET_CONNECTION(1, R.string.message_error_no_internet_connection),

	// MI_SERVER_CONNECTION_PROBLEM(7, "Connection Problem"),
	MI_SERVER_CONNECTION_PROBLEM(7,
			R.string.message_error_server_connection_problem),

	// MI_ALREADY_FRIEND_REQUEST_SEND(1016, "Already Friend request send");
	MI_ALREADY_FRIEND_REQUEST_SEND(1016,
			R.string.message_error_already_friend_request_send);

	// public final static int APP_VERSION_OLD = 1021;
	// public final static int SERVER_ERROR_1 = 1015;
	// public final static int SERVER_ERROR_2 = 1014;
	// public final static int SERVER_ERROR_3 = 1013;
	// public final static int MISSING_DATA = 1003;
	// public final static int NO_SUCH_USER = 1004;
	// public final static int NOT_FOUND_DATA_ERROR = 1003;
	// public final static int APP_GCM_EMPTY = 1001;
	// /*
	// * Local Message codes. Must be different values
	// */
	// // public final static int UN_SUCCESSFUL = 16;
	// private MessagesEnum(int id, String message) {
	// this.id = id;
	// this.message = message;
	// }

	private MessagesEnum(int id, int message) {
		this.id = id;
		this.messageId = message;
	}

	public int getId() {
		return id;
	}

	public int getMessageId() {
		return messageId;
	}

	private int id;
	private int messageId;

}
