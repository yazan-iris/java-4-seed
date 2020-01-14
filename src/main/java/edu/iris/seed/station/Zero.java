package edu.iris.seed.station;

public class Zero {

	private Number real;
	private Number imaginary;

	public Zero() {
	}

	public Zero(double real, double realError, double imaginary,
			double imaginaryError) {
		this.real = new Number(real, realError);
		this.imaginary = new Number(imaginary, imaginaryError);
	}

	public Zero(Number real, Number imaginary) {
		super();
		this.real = real;
		this.imaginary = imaginary;
	}

	public Number getReal() {
		return real;
	}

	public void setReal(Number real) {
		this.real = real;
	}

	public Number getImaginary() {
		return imaginary;
	}

	public void setImaginary(Number imaginary) {
		this.imaginary = imaginary;
	}

}
