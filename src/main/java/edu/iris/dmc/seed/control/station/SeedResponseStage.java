package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.SeedRunTimeException;

public class SeedResponseStage {
	private int sequence;
	private Map<Integer, ResponseBlockette> blockettes = new HashMap<>();

	public SeedResponseStage(int sequence) {
		this.sequence = sequence;
	}

	public void add(ResponseBlockette b) throws SeedException {
		ResponseBlockette r = blockettes.get(b.getType());
		if (r != null) {
			if (b instanceof OverFlowBlockette) {
				((OverFlowBlockette)r).merge((OverFlowBlockette)b);
				return;
			} else {
				throw new SeedRunTimeException("Cannot add response filter " + b.getType()
						+ " response filter already exist in this stage [" + this.sequence + "]");
			}
		}
		blockettes.put(b.getType(), b);
	}

	public List<ResponseBlockette> getBlockettes() {
		return new ArrayList<>(blockettes.values());
	}

	public int getSequence() {
		return sequence;
	}

	public int size() {
		return this.blockettes.size();
	}
}
