package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.iris.seed.BTime;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

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
		builder.append(this.getStageNumber(), 2);

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

	public static B058 build(byte[] bytes) throws SeedException {
		int offset = 7;
		B058 b = new B058();

		b.setStageNumber(SeedStrings.parseInt(bytes, offset, 2));
		offset = offset + 2;

		double sensitivity = SeedStrings.parseDouble(bytes, offset, 12);
		b.setSensitivity(sensitivity);
		offset = offset + 12;
		double frequency = SeedStrings.parseDouble(bytes, offset, 12);
		offset = offset + 12;
		b.setFrequency(frequency);

		int numberOfHistoryValues = SeedStrings.parseInt(bytes, offset, 2);
		offset = offset + 2;

		for (int i = 0; i < numberOfHistoryValues; i++) {
			double s = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double f = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			int x = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			byte[] copy = Arrays.copyOfRange(bytes, x, offset);
			b.add(new Calibration(s, f, BTime.valueOf(copy)));
		}
		return b;
	}

}
