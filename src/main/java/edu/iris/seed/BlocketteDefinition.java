package edu.iris.seed;

import java.util.ArrayList;
import java.util.List;

public class BlocketteDefinition {
	int number;
	String type;
	String description;
	String since;
	int minumumLength;
	List<SeedField> fields = new ArrayList<>();

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public List<SeedField> getFields() {
		return fields;
	}

	public void setFields(List<SeedField> fields) {
		this.fields = fields;
	}

	public int getMinumumLength() {
		int l = 0;
		for (SeedField f : fields) {
			l = l + f.getMinumumLength();
		}
		return l;
	}

	@Override
	public String toString() {
		return "BlocketteDefinition [number=" + number + ", type=" + type + ", description=" + description + ", since="
				+ since + ", fields=" + fields + "]";
	}

}
