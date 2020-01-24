package edu.iris.seed;

public class SeedControlHeader implements SeedHeader {

	private int sequence;
	private Type type;
	private boolean continuation;

	public SeedControlHeader(int sequence, Type type, boolean continuation) {
		super();
		this.sequence = sequence;
		this.type = type;
		this.continuation = continuation;
	}

	public int getSequence() {
		return sequence;
	}

	public Type getRecordType() {
		return type;
	}

	public boolean isContinuation() {
		return continuation;
	}

	public static class Builder {

		private int sequence;
		private Type type;
		private boolean continuation;

		private Builder(int sequence, Type type, boolean continuation) {
			this.sequence = sequence;
			this.type = type;
			this.continuation = continuation;
		}

		public static Builder newInstance(int sequence, Type type, char continuation) {
			return newInstance(sequence, type, continuation == '*' ? true : false);
		}

		public static Builder newInstance(int sequence, Type type, boolean continuation) {
			return new Builder(sequence, type, continuation);
		}

		public static Builder newInstance(byte[] bytes) throws SeedException {
			return new Builder(Integer.parseInt(new String(bytes, 0, 6)), Type.from((char) bytes[6]),
					((char) bytes[7]) == '*' ? true : false);
		}

		public SeedControlHeader build() {
			return new SeedControlHeader(this.sequence, this.type, this.continuation);
		}
	}
}
