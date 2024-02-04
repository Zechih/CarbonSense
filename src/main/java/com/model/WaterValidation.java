package com.model;

import java.io.Serializable;

public class WaterValidation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int waterID;
	private float waterProportionalFactor;
	private float waterUsageValueRM;
	private float waterUsageValueM3;
	private byte[] waterConsumptionProof;
	private String status;

	public int getWaterID() {
		return waterID;
	}

	public void setWaterID(int waterID) {
		this.waterID = waterID;
	}

	public float getWaterProportionalFactor() {
		return waterProportionalFactor;
	}

	public void setWaterProportionalFactor(float waterProportionalFactor) {
		this.waterProportionalFactor = waterProportionalFactor;
	}

	public float getWaterUsageValueRM() {
		return waterUsageValueRM;
	}

	public void setWaterUsageValueRM(float waterUsageValueRM) {
		this.waterUsageValueRM = waterUsageValueRM;
	}

	public float getWaterUsageValueM3() {
		return waterUsageValueM3;
	}

	public void setWaterUsageValueM3(float waterUsageValueM3) {
		this.waterUsageValueM3 = waterUsageValueM3;
	}

	public byte[] getWaterConsumptionProof() {
		return waterConsumptionProof;
	}

	public void setWaterConsumptionProof(byte[] waterConsumptionProof) {
		this.waterConsumptionProof = waterConsumptionProof;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
