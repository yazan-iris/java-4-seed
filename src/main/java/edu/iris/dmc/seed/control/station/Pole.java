package edu.iris.dmc.seed.control.station;

public class Pole {
	private Number real;
	private Number imaginary;

	public Pole() {
	}

	public Pole(double real, double realError, double imaginary, double imaginaryError) {
		this(new Number(real, realError), new Number(imaginary, imaginaryError));
	}

	public Pole(Number real, Number imaginary) {
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
