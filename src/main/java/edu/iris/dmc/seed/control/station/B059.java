package edu.iris.dmc.seed.control.station;

import edu.iris.dmc.seed.Blockette;
import java.util.Set;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;

public class B059 extends AbstractBlockette implements StationBlockette {
	private BTime startTime;
	private BTime endTime;
	private int lookupKey;
	private int level;

	private B052 b052;

	public B059() {
		super(59, "Channel Comment Blockette");
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
		builder.append(this.startTime).append("~");
		if (this.endTime != null) {
			builder.append(this.endTime);
		}
		builder.append("~");
		builder.append(this.lookupKey, 4);
		builder.append(this.level, 6);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
