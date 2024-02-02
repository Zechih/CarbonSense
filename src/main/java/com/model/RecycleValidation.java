package com.model;

import java.io.Serializable;

public class RecycleValidation implements Serializable{
    private static final long serialVersionUID = 1L;
	
	private int recycleID;
	private float accumulatedKg;
	private float recycleRM;
	private byte[] recycleConsumptionProof;
	private String status;
	
	public int getRecycleID() {
		return recycleID;
	}
	public void setRecycleID(int recycleID) {
		this.recycleID = recycleID;
	}
	public float getAccumulatedKg() {
		return accumulatedKg;
	}
	public void setAccumulatedKg(float AccumulatedKg) {
		this.accumulatedKg = AccumulatedKg;
	}
	public float getRecycleRM() {
		return recycleRM;
	}
	public void setRecycleRM(float recycleRM) {
		this.recycleRM = recycleRM;
	}
	public byte[] getRecycleConsumptionProof() {
		return recycleConsumptionProof;
	}
	public void setRecycleConsumptionProof(byte[] recycleConsumptionProof) {
		this.recycleConsumptionProof = recycleConsumptionProof;
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
