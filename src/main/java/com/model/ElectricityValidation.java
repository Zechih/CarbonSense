package com.model;

import java.io.Serializable;

public class ElectricityValidation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int electricityID;
	private float electricityProportionalFactor;
	private float electricUsageValueRM;
	private float electricUsageValueM3;
	private byte[] electricConsumptionProof;
	private String status;
	
	public int getElectricityID() {
		return electricityID;
	}
	public void setElectricityID(int electricityID) {
		this.electricityID = electricityID;
	}
	public float getElectricityProportionalFactor() {
		return electricityProportionalFactor;
	}
	public void setElectricityProportionalFactor(float electricityProportionalFactor) {
		this.electricityProportionalFactor = electricityProportionalFactor;
	}
	public float getElectricUsageValueRM() {
		return electricUsageValueRM;
	}
	public void setElectricUsageValueRM(float electricUsageValueRM) {
		this.electricUsageValueRM = electricUsageValueRM;
	}
	public float getElectricUsageValueM3() {
		return electricUsageValueM3;
	}
	public void setElectricUsageValueM3(float electricUsageValueM3) {
		this.electricUsageValueM3 = electricUsageValueM3;
	}
	public byte[] getElectricConsumptionProof() {
		return electricConsumptionProof;
	}
	public void setElectricConsumptionProof(byte[] electricConsumptionProof) {
		this.electricConsumptionProof = electricConsumptionProof;
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
