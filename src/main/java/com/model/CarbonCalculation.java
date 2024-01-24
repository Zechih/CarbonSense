package com.model;

public class CarbonCalculation {
	public static float calWaterCarbon(float waterConsumption) {
		float carbonEmission = waterConsumption * 0.419f;
		return carbonEmission;
	}
	
	public static float calElectricityCarbon(float electricityConsumption) {
		float carbonEmission = electricityConsumption * 0.589f;
		return carbonEmission;
	}
	
	public static float calRecycleCarbon(float recycle) {
		float carbonEmission = recycle * 2.86f;
		return carbonEmission;
	}
}
