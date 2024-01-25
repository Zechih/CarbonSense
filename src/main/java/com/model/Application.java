package com.model;

import java.io.Serializable;

public class Application implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String category;
	private String region;
	private float waterConsumption;
	private float electricityConsumption;
	private float recycle;
	float carbonEmission;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
