package edu.iris.seed.station;

public class Number {
	private double value;
	private double error;

	public Number() {

	}

	public Number(double value, double error) {
		this.value = value;
		this.error = error;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public double getError() {

		return error;
	}
	

	public void setError(Double error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Number [value=" + value + ", error=" + error + "]";
	}

}
