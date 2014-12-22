package com.allfine.enums;

public enum MainMenuRowType {
	FRIEND(1), NON_FRIEND(2);

	private MainMenuRowType(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	private int id;

}
