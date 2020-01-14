package edu.iris.seed.record;

import edu.iris.seed.SeedException;

public class ControlHeader implements Header {

	private int sequence;
	private Type type;
	private boolean continuation;

	public ControlHeader(Type type) {
		this(type, false);
	}

	public ControlHeader(Type type, boolean continuation) {
		this(0, type, continuation);
	}

	public ControlHeader(int sequence, Type type, boolean continuation) {
		this.sequence = sequence;
		this.type = type;
		this.continuation = continuation;
	}

	public int getSequence() {
		return sequence;
	}

	public Type getType() {
		return type;
	}

	public boolean isContinuation() {
		return continuation;
	}

	public static class Builder {

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public ControlHeader build(byte[] bytes) throws SeedException {
			if (bytes == null) {
				throw new SeedException("No data to read from buffer, NULL ");
			}
			try {
				Type type = Type.from((char) bytes[6]);
				return new ControlHeader(Integer.parseInt(new String(bytes, 0, 6)), type, ((char) bytes[7] == '*'));
			} catch (SeedException e) {
				
				throw e;
			}
		}

		public ControlHeader build(int sequence, Type type, boolean continuation) {
			return new ControlHeader(sequence, type, continuation);
		}
	}
}
