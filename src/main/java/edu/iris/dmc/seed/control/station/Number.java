package edu.iris.dmc.seed.control.station;

public class Number {
	private Double value;
	private Double error;

	public Number() {

	}

	public Number(Double value, Double error) {
		this.value = value;
		this.error = error;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getError() {
		if(error !=null) {
		return error;
		}else {
			error=0.0;
		}
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
