package com.model;

import java.io.Serializable;

public class CarbonReportAnalysis implements Serializable{
	private static final long serialVersionUID = 1L;
	
	float totalWaterCarbon;
	float totalElectricityCarbon;
	float totalRecycleCarbon;
	float totalCarbonEmission;
	
	public float getTotalWaterCarbon() {
		return totalWaterCarbon;
	}
	public void setTotalWaterCarbon(float totalWaterCarbon) {
		this.totalWaterCarbon = totalWaterCarbon;
	}
	public float getTotalElectricityCarbon() {
		return totalElectricityCarbon;
	}
	public void setTotalElectricityCarbon(float totalElectricityCarbon) {
		this.totalElectricityCarbon = totalElectricityCarbon;
	}
	public float getTotalRecycleCarbon() {
		return totalRecycleCarbon;
	}
	public void setTotalRecycleCarbon(float totalRecycleCarbon) {
		this.totalRecycleCarbon = totalRecycleCarbon;
	}
	public float getTotalCarbonEmission() {
		return totalCarbonEmission;
	}
	public void setTotalCarbonEmission(float totalCarbonEmission) {
		this.totalCarbonEmission = totalCarbonEmission;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
