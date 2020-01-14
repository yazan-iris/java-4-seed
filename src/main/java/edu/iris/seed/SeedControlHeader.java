package edu.iris.seed;

public class SeedControlHeader implements SeedHeader {

	private int sequence;
	private char type;
	private boolean continuation;

	public SeedControlHeader(int sequence, char type, boolean continuation) {
		super();
		this.sequence = sequence;
		this.type = type;
		this.continuation = continuation;
	}

	public int getSequence() {
		return sequence;
	}

	public char getType() {
		return type;
	}

	public boolean isContinuation() {
		return continuation;
	}

}
