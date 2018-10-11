package edu.iris.dmc.seed.control.station;

import edu.iris.dmc.seed.AbstractBlockette;

public abstract class AbstractResponseBlockette extends AbstractBlockette implements ResponseBlockette {

	private int stageSequence;
	private int signalInputUnit;
	private int signalOutputUnit;

	public AbstractResponseBlockette(int type, String name) {
		super(type, name, 9999);
	}

	public int getStageSequence() {
		return stageSequence;
	}

	public void setStageSequence(int stageSequence) {
		this.stageSequence = stageSequence;
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
}
