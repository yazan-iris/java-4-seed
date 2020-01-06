package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;

public class B058 extends AbstractResponseBlockette {

	private double sensitivity;
	private double frequency;
	private List<Calibration> history = new ArrayList<Calibration>();

	public B058() {
		super(58, "Channel Sensitivity/Gain Blockette");

	}

	public double getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(double sensitivity) {
		this.sensitivity = sensitivity;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public List<Calibration> getHistory() {
		return history;
	}

	public void setHistory(List<Calibration> history) {
		this.history = history;
	}

	public void add(Calibration calibration) {
		this.history.add(calibration);
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getStageSequence(), 2);

		builder.append(this.getSensitivity(), "-0.00000E-00", 12);
		builder.append(this.getFrequency(), "-0.00000E-00", 12);
		int size = 0;
		if (this.history != null && !this.history.isEmpty()) {
			size = this.history.size();
			builder.append(size, 2);
			for (Calibration cal : this.history) {
				builder.append(cal.getSensitivity(), "-0.00000E-00", 12);
				builder.append(cal.getFrequency(), "-0.00000E-00", 12);
				builder.append(cal.getTime());
			}
		} else {
			builder.append(size, 2);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
