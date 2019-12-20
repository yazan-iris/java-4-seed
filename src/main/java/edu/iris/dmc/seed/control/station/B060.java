package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.seed.SeedStringBuilder;


public class B060 extends AbstractResponseBlockette{

	private List<Stage> stages = new ArrayList<Stage>();

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
}
