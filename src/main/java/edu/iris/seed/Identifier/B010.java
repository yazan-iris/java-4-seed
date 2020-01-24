package edu.iris.seed.Identifier;

import java.util.Arrays;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B010 extends SeedBlockette<B010> implements IdentifierBlockette {

	private static final int TYPE = 10;
	private static final int MINUMUM_LENGTH = 18;

	private String version = "02.4";
	// Volume logical record length, expressed as a power of 2. A 4096 byte
	// logical record would have “12” in this field.
	// Logical record lengths can be from 256 bytes to 32,768 bytes. 4096 bytes
	// is preferred
	private int nthPower;
	private BTime startTime;
	private BTime endTime;
	private BTime volumeTime = BTime.now();
	private String organization;
	private String label;

	public B010() {
		this(12);
	}

	public B010(int power) {
		super(10, "Volume Identifier Blockette");
		if (power < 8 || power > 15) {
			throw new IllegalArgumentException("Invalid record length power(" + power + ")");
		}
		this.nthPower = power;
		this.volumeTime = BTime.now();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getNthPower() {
		return nthPower;
	}

	public void setNthPower(int nthPower) {
		if (nthPower < 8 || nthPower > 15) {
			throw new IllegalArgumentException("Invalid record length power(" + nthPower + ")");
		}
		this.nthPower = nthPower;
	}

	public BTime getStartTime() {
		return startTime;
	}

	public void setStartTime(BTime startTime) {
		this.startTime = startTime;
	}

	public BTime getEndTime() {
		return endTime;
	}

	public void setEndTime(BTime endTime) {
		this.endTime = endTime;
	}

	public BTime getVolumeTime() {
		return volumeTime;
	}

	public void setVolumeTime(BTime volumeTime) {
		this.volumeTime = volumeTime;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.version);
		builder.append(this.nthPower, 2);

		builder.append(this.startTime).append("~");
		builder.append(this.endTime).append("~");
		builder.append(this.volumeTime).append("~");
		builder.append(this.organization).append("~");
		builder.append(this.label).append("~");

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B010> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B010> {

		public Builder() {
			super(10);
		}

		private String organization;
		private String label;
		private String version;

	
		public static Builder newInstance() {
			return new Builder();
		}

		public Builder organization(String organization) {
			this.organization = organization;
			return this;
		}

		public Builder label(String label) {
			this.label = label;
			return this;
		}

		public Builder version(String version) {
			this.version = version;
			return this;
		}

		public B010 build(boolean relax) throws SeedException {
			validate(TYPE, MINUMUM_LENGTH, bytes);
			B010 b = new B010();

			int offset = 7;
			b.setVersion(new String(bytes, offset, 4));
			offset = offset + 4;
			int nthPower = SeedStrings.parseInt(bytes, offset, 2);
			b.setNthPower(nthPower);
			offset = offset + 2;
			int from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;

			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setEndTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;
			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setVolumeTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;

			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setOrganization((new String(Arrays.copyOfRange(bytes, from, offset))));
			offset++;

			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setLabel((new String(Arrays.copyOfRange(bytes, from, offset))));
			return b;
		}
	}
}
