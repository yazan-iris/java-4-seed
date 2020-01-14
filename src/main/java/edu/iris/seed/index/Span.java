package edu.iris.seed.index;

import edu.iris.seed.BTime;

public class Span {

	private BTime start;
	private BTime end;

	public Span() {
	}

	public Span(BTime start, BTime end) {
		this.start = start;
		this.end = end;
	}

	public BTime getStart() {
		return start;
	}

	public void setStart(BTime start) {
		this.start = start;
	}

	public BTime getEnd() {
		return end;
	}

	public void setEnd(BTime end) {
		this.end = end;
	}

}
