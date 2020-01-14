package edu.iris.seed.station;

public class Pole {
	private edu.iris.seed.station.Number real;
	private edu.iris.seed.station.Number imaginary;

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
