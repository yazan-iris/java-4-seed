package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B045 extends AbstractAbbreviationBlockette implements AbbreviationBlockette,ResponseDictionaryBlockette {

	private int lookupKey;
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
		builder.append(this.responseName).append("~");
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
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static B045 build(byte[] bytes) throws SeedException {
		int offset = 7;
		B045 b = new B045();

		b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfNumerators = SeedStrings.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (int i = 0; i < numberOfNumerators; i++) {
			double frequency = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitude = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitudeError = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngle = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngleError = SeedStrings.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			b.add(b.new Response(frequency, amplitude, amplitudeError, phaseAngle, phaseAngleError));
		}

		return b;
	}
}
