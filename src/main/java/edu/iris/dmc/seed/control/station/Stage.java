package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

public class Stage {
	private int sequence;
	List<Integer> responses = new ArrayList<Integer>();

	public Stage() {

	}

	public Stage(int sequence) {
		this.sequence = sequence;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public List<Integer> getResponses() {
		return responses;
	}

	public void setResponses(List<Integer> responses) {
		this.responses = responses;
	}

	public void add(Integer lookup) {
		this.responses.add(lookup);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sequence;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stage other = (Stage) obj;
		if (sequence != other.sequence)
			return false;
		return true;
	}

}
