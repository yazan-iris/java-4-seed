package edu.iris.dmc.seed.control.station;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;

public class B051 extends AbstractBlockette implements StationBlockette {
	private BTime startTime;
	private BTime endTime;
	private int lookupKey;
	private int level;

	public B051() {
		this(null);
	}

	public B051(String text) {
		super(51, "Station Comment Blockette");
		this.originalText = text;
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

	public int getLookupKey() {
		return lookupKey;
	}

	public void setLookupKey(int lookupKey) {
		this.lookupKey = lookupKey;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		if (this.startTime == null) {
			builder.append("0").append("~");
		} else {
			builder.append(this.startTime).append("~");
		}
		builder.append(this.endTime).append("~");
		builder.append(this.lookupKey, 4);
		builder.append(this.level, 6);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
