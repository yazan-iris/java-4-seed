package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B045 extends AbstractDictionaryBlockette implements ResponseBlockette {

	private String responseName;
	private int signalInputUnit;
	private int signalOutputUnit;
	private List<Response> responses = new ArrayList<Response>();

	public B045() {
		super(45, "Response List Dictionary Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
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

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public void add(Response response) {
		this.responses.add(response);
	}

	public class Response {
		private Double frequency;
		private Double amplitude;
		private Double amplitudeError;
		private Double phaaeAngle;
		private Double phaseError;

		public Response() {
		}

		public Response(Double frequency, Double amplitude, Double amplitudeError, Double phaaeAngle,
				Double phaseError) {
			super();
			this.frequency = frequency;
			this.amplitude = amplitude;
			this.amplitudeError = amplitudeError;
			this.phaaeAngle = phaaeAngle;
			this.phaseError = phaseError;
		}

		public Double getFrequency() {
			return frequency;
		}

		public void setFrequency(Double frequency) {
			this.frequency = frequency;
		}

		public Double getAmplitude() {
			return amplitude;
		}

		public void setAmplitude(Double amplitude) {
			this.amplitude = amplitude;
		}

		public Double getAmplitudeError() {
			return amplitudeError;
		}

		public void setAmplitudeError(Double amplitudeError) {
			this.amplitudeError = amplitudeError;
		}

		public Double getPhaaeAngle() {
			return phaaeAngle;
		}

		public void setPhaaeAngle(Double phaaeAngle) {
			this.phaaeAngle = phaaeAngle;
		}

		public Double getPhaseError() {
			return phaseError;
		}

		public void setPhaseError(Double phaseError) {
			this.phaseError = phaseError;
		}

	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.responses.size(), 4);

		for (Response response : this.responses) {
			builder.append(response.getFrequency(), "-0.00000E-00", 12);
			builder.append(response.getAmplitude(), "-0.00000E-00", 12);
			builder.append(response.getAmplitudeError(), "-0.00000E-00", 12);
			builder.append(response.getPhaaeAngle(), "-0.00000E-00", 12);
			builder.append(response.getPhaseError(), "-0.00000E-00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageSequence() {
		// TODO Auto-generated method stub
		return 0;
	}
}
