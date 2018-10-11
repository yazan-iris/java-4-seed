package edu.iris.dmc.seed.control.station;

import edu.iris.dmc.seed.BTime;

public class Calibration {
	private Double sensitivity;
	private Double frequency;
	private BTime time;

	public Calibration() {
	}

	public Calibration(Double sensitivity, Double frequency, BTime time) {
		super();
		this.sensitivity = sensitivity;
		this.frequency = frequency;
		this.time = time;
	}

	public Double getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(Double sensitivity) {
		this.sensitivity = sensitivity;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public BTime getTime() {
		return time;
	}

	public void setTime(BTime time) {
		this.time = time;
	}

}
