package edu.iris.dmc.seed.control.index;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;

public class B010 extends AbstractBlockette implements IndexBlockette {

	private String version;
	// Volume logical record length, expressed as a power of 2. A 4096 byte
	// logical record would have “12” in this field.
	// Logical record lengths can be from 256 bytes to 32,768 bytes. 4096 bytes
	// is preferred
	private int nthPower;
	private BTime startTime;
	private BTime endTime;
	private BTime volumeTime;
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

}
