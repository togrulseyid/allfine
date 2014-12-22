package com.allfine.models.core;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SerializableObject<T> extends Object implements Serializable {

	T obj;

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "SerializableObject [obj=" + obj + "]";
	}

}
