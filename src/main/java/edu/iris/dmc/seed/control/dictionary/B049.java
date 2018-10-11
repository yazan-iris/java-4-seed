package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.seed.control.station.Calibration;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B049 extends AbstractDictionaryBlockette implements ResponseBlockette  {

	private String responseName;
	private Double sensitivity;
	private Double frequency;
	private List<Calibration> history = new ArrayList<Calibration>();

	public B049() {
		super(48, "Channel Sensitivity/Gain Dictionary Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public Double getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(Double sensitivity) {
		this.sensitivity = sensitivity;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public List<Calibration> getHistory() {
		return history;
	}

	public void setHistory(List<Calibration> history) {
		this.history = history;
	}

	public void add(Calibration calibration) {
		this.history.add(calibration);
	}

	@Override
	public String toSeedString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStageSequence() {
		// TODO Auto-generated method stub
		return 0;
	}
}
