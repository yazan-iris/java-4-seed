package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

public class SeedResponseStage {
	private int sequence;
	private List<ResponseBlockette> blockettes = new ArrayList<>();

	public SeedResponseStage(int sequence) {
		this.sequence = sequence;
	}

	public void add(ResponseBlockette b) {
		blockettes.add(b);
	}

	public List<ResponseBlockette> getBlockettes() {
		return blockettes;
	}

	
	public int getSequence() {
		return sequence;
	}

	public int size() {
		return this.blockettes.size();
	}
}
