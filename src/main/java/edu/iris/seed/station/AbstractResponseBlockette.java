package edu.iris.seed.station;

import java.nio.charset.StandardCharsets;

import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;

public abstract class AbstractResponseBlockette<T extends ResponseBlockette> extends SeedBlockette<T> implements ResponseBlockette {

	private int stageNumber;
	private int signalInputUnit;
	private int signalOutputUnit;

	public AbstractResponseBlockette(int type, String title) {
		super(type, title);
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public int getSignalInputUnit() {
		return signalInputUnit;
	}

	public void setSignalInputUnit(int signalInputUnit) {
		this.signalInputUnit = signalInputUnit;
	}

	public int getSignalOutputUnit() {
		return signalOutputUnit;
	}

	public void setSignalOutputUnit(int signalOutputUnit) {
		this.signalOutputUnit = signalOutputUnit;
	}

	public int getSize() throws SeedException {
		String string = this.toSeedString();
		if (string == null || string.isEmpty()) {
			return 0;
		}
		return string.getBytes(StandardCharsets.US_ASCII).length;
	}
}
