package com.model;

import java.io.Serializable;

public class CarbonRegion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String region;
	private float water_Carbon;
	private float electricity_Carbon;
	private float recycle_Carbon;
	private float total_Carbon;
//	private int total_Submmission;
	
	public float getTotal_Carbon() {
		return total_Carbon;
	}
	public void setTotal_Carbon(float total_Carbon) {
		this.total_Carbon = total_Carbon;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public float getWater_Carbon() {
		return water_Carbon;
	}
	public void setWater_Carbon(float water_Carbon) {
		this.water_Carbon = water_Carbon;
	}
	public float getElectricity_Carbon() {
		return electricity_Carbon;
	}
	public void setElectricity_Carbon(float electricity_Carbon) {
		this.electricity_Carbon = electricity_Carbon;
	}
	public float getRecycle_Carbon() {
		return recycle_Carbon;
	}
	public void setRecycle_Carbon(float recycle_Carbon) {
		this.recycle_Carbon = recycle_Carbon;
	}
//	public int getTotal_Submmission() {
//		return total_Submmission;
//	}
//	public void setTotal_Submmission(int total_Submmission) {
//		this.total_Submmission = total_Submmission;
//	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
