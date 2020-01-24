package edu.iris.seed.station;

import java.util.Arrays;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B059 extends SeedBlockette implements StationBlockette {
	private BTime startTime;
	private BTime endTime;
	private int lookupKey;
	private int level;

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

	public BlocketteBuilder<B059> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B059> {

		public Builder() {
			super(59);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B059 build() throws SeedException { 

			int offset = 7;
			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			B059 b = new B059();

			byte[] copy = Arrays.copyOfRange(bytes, i, offset);
			b.setStartTime(BTime.valueOf(copy));

			offset++;
			i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			copy = Arrays.copyOfRange(bytes, i, offset);
			b.setEndTime(BTime.valueOf(copy));

			offset++;
			int key = SeedStrings.parseInt(bytes, offset, 4);
			b.setLookupKey(key);
			offset = offset + 4;
			if (offset >= bytes.length) {
				return b;
			}

			int level = SeedStrings.parseInt(bytes, offset, 6);
			b.setLevel(level);

			return b;
		}
	}
}