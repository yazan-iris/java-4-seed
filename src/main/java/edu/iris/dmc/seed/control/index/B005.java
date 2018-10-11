package edu.iris.dmc.seed.control.index;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;


public class B005 extends AbstractBlockette implements IndexBlockette {

	private String version;
	private int logicalRecordLength;
	private BTime startTime;

	public B005() {
		super(5, "Field Volume Identifier Blockette");
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getLogicalRecordLength() {
		return logicalRecordLength;
	}

	public void setLogicalRecordLength(int logicalRecordLength) {
		this.logicalRecordLength = logicalRecordLength;
	}

	public BTime getStartTime() {
		return startTime;
	}

	public void setStartTime(BTime startTime) {
		this.startTime = startTime;
	}
	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3)
				.append("####");
		if (this.version != null) {
			builder.append(this.version, 4);
		}
		builder.append(this.logicalRecordLength, 2);
		if (this.startTime != null) {
			builder.append(this.startTime);
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
