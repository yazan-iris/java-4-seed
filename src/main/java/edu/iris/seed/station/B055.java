package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedStringBuilder;

public class B055 extends AbstractResponseBlockette{

	private List<Response> responses = new ArrayList<Response>();

	public B055() {
		super(55, "Response List Blockette");

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
		builder.append(this.getStageNumber(), 2);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.responses.size(), 4);

		for (Response response : this.responses) {
			builder.append(response.getFrequency(), "#0.00000E00", 12);
			builder.append(response.getAmplitude(), "#0.00000E00", 12);
			builder.append(response.getAmplitudeError(), "#0.00000E00", 12);
			builder.append(response.getPhaaeAngle(), "#0.00000E00", 12);
			builder.append(response.getPhaseError(), "#0.00000E00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
