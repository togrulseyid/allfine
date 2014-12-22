package com.allfine.operations;

import java.io.IOException;
import java.io.StringWriter;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectConvertor<T> {

	private T object;
	private String strObject;
	private StringWriter strWriter;
	private ObjectMapper mapper;

	public T getClassObject(String jsonObject, Class<T> classType)
			throws IOException {
		mapper = new ObjectMapper();
		try {
			setObject(mapper.readValue(jsonObject, classType));
		} catch (Exception e) {
			Log.d("exception", "exception: " + e.getMessage());
		}
		return getObject();
	}

	public String getClassString(T object) throws IOException {
		strWriter = new StringWriter();
		mapper = new ObjectMapper();
		try {
			mapper.writeValue(strWriter, object);
			strObject = strWriter.toString();
		} catch (Exception e) {
			Log.d("exception", "exception: " + e.getMessage());
		}
		return strObject;
	}

	public T getObject() {
		return object;
	}
	
	
	
	

	public ObjectNode readTree(String data) throws JsonProcessingException,
			IOException {
		Log.d("testA", "dataMapper: " + data);
		return (ObjectNode) mapper.readTree(data);
	}
    private ObjectNode jsonObj;
	public ObjectNode getJson(Object obj) {
		// some code to generate the Object user...
		JsonNode jsonNode = mapper.valueToTree(obj);
		if (jsonNode.isObject()) {
			jsonObj = (ObjectNode) jsonNode;
			return jsonObj;
		}
		return null;
	}
	
	
	public ObjectNode getObjectNode() {
		return mapper.convertValue(object, ObjectNode.class);
	}
	public JsonNode getJsonNode() {
		return mapper.convertValue("foo", JsonNode.class);
	}
	
	
	
	   

	public void setObject(T object) {
		this.object = object;
	}
}
