package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B060 extends AbstractResponseBlockette<B060> implements Appendable<B060> {

	private List<Stage> stages = new ArrayList<>();

	public B060() {
		super(60, "Response Reference Blockette");

	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public void add(Stage stage) {
		this.stages.add(stage);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.getStages().size(), 2);
		for (Stage stage : this.getStages()) {
			builder.append(stage.getSequence(), 2);
			builder.append(stage.getResponses().size(), 2);
			for (Integer lookup : stage.getResponses()) {
				builder.append(lookup, 4);
			}
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public B060 append(B060 t) {
		if(t==null) {
			return null;
		}
		this.stages.addAll(t.getStages());
		return this;
	}

	public static class Builder extends BlocketteBuilder<B060> {

		public Builder() {
			super(60);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B060 build() throws SeedException {

			int offset = 7;
			B060 b = new B060();

			int numberOfStages = SeedStrings.parseInt(bytes, offset, 2);
			offset = offset + 2;

			for (int i = 0; i < numberOfStages; i++) {
				int stageSequenceNumber = SeedStrings.parseInt(bytes, offset, 2);
				offset = offset + 2;
				Stage stage = b.new Stage();
				stage.setSequence(stageSequenceNumber);
				b.add(stage);
				int numberOfResponses = SeedStrings.parseInt(bytes, offset, 2);
				offset = offset + 2;
				for (int x = 0; x < numberOfResponses; x++) {
					stage.add(SeedStrings.parseInt(bytes, offset, 4));
					offset = offset + 4;
				}
			}
			return b;
		}
	}

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

}
