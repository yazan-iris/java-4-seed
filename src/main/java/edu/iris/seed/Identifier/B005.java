package edu.iris.seed.Identifier;

import java.util.Arrays;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;

public class B005 extends SeedBlockette<B005> implements IdentifierBlockette {

	public static final int TYPE = 5;
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
	public String toSeedString() throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");
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

	public BlocketteBuilder<B005> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B005> {

		public Builder() {
			super(5);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B005 build(boolean relax) throws SeedException {
			if (bytes.length < 7) {
				throw new SeedException("Expected at least 7 bytes but was {}", bytes.length);
			}
			int type = Integer.parseInt(new String(bytes, 0, 3));
			if (type != TYPE) {
				throw new SeedException("Expected type {} but was {}", TYPE, type);
			}
			int length = Integer.parseInt(new String(bytes, 3, 4));
			if (bytes.length < length) {
				throw new SeedException("Expected {} bytes but was {}", length, bytes.length);
			}
			B005 b = new B005();

			b.setVersion(new String(bytes, 7, 4));

			int recordLength = Integer.parseInt(new String(bytes, 11, 2));
			b.setLogicalRecordLength(recordLength);
			int offset = 13;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, 13, offset - 1)));
			return b;
		}
	}
}
