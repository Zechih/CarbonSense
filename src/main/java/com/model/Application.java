package com.model;

import java.io.Serializable;

public class Application implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String name;
	float waterConsumption;
	float electricityConsumption;
	float recycle;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWaterConsumption() {
		return waterConsumption;
	}
	public void setWaterConsumption(float waterConsumption) {
		this.waterConsumption = waterConsumption;
	}
	public float getElectricityConsumption() {
		return electricityConsumption;
	}
	public void setElectricityConsumption(float electricityConsumption) {
		this.electricityConsumption = electricityConsumption;
	}
	public float getRecycle() {
		return recycle;
	}
	public void setRecycle(float recycle) {
		this.recycle = recycle;
	}
	public float getCarbonEmission() {
		return carbonEmission;
	}
	public void setCarbonEmission(float carbonEmission) {
		this.carbonEmission = carbonEmission;
	}
	float carbonEmission;
	
}
