package com.allfine.models;

import com.allfine.models.core.CoreModel;

public class GCMInfoModel extends CoreModel{
	private String gcm;
	private String imei;

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toString() {
		return "GCMInfoModel [gcm=" + gcm + ", imei=" + imei + "]";
	}

}
