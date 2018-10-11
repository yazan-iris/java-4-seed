package edu.iris.dmc.seed;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.iris.dmc.seed.control.station.B050;

public class ControlIndex {

	private Map<Integer, Blockette> blockettes = new LinkedHashMap<>();

	private List<B050> stations = new ArrayList<>();

	public Blockette put(Blockette blockette) {
		if (blockette instanceof B050) {
			stations.add((B050) blockette);
		}
		return this.blockettes.put(blockette.getId(), blockette);
	}

	public List<B050> get() {
		return this.stations;
	}
}
