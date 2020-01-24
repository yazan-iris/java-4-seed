package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.Calibration;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B048 extends AbstractAbbreviationBlockette<B048> implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String responseName;
	private Double sensitivity;
	private Double frequency;
	private List<Calibration> history = new ArrayList<Calibration>();

	public B048() {
		super(48, "Channel Sensitivity/Gain Dictionary Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
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
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName).append("~");
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
		// 048 56 5GLBELA2009016HXXXX~ 3.11333E+05 0.00000E+00 0
		// 04800660005GLBELA2009016HXXXX~ 3.11333E++3.11333E+05+0.00000E+0000

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BlocketteBuilder<B048> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B048> {

		public Builder() {
			super(48);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B048 build() throws SeedException { 
			int offset = 7;
			B048 b = new B048();

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			String responseName = new String(bytes, i, offset - i);
			b.setResponseName(responseName);
			// skip ~
			offset++;

			double sensitivity = SeedStrings.parseDouble(bytes, offset, 12);
			b.setSensitivity(sensitivity);
			offset = offset + 12;
			double frequency = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			b.setFrequency(frequency);

			int numberOfHistoryValues = SeedStrings.parseInt(bytes, offset, 2);
			offset = offset + 2;

			for (i = 0; i < numberOfHistoryValues; i++) {
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
}
