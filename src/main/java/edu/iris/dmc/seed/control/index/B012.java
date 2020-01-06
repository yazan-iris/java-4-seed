package edu.iris.dmc.seed.control.index;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.Blockette;

public class B012 extends AbstractBlockette implements IndexBlockette {

	public B012() {
		super(12, "Volume Time Span Index Blockette");
	}

	Map<String, Span> spans;

	public void add(String sequence, Span span) {

		if (spans == null) {
			spans = new HashMap<String, Span>();
		}
		spans.put(sequence, span);
	}

	public Map<String, Span> getAvailableStations() {
		return spans;
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		int number = 0;
		if (spans != null) {
			number = spans.size();
		}
		builder.append(number, 4);

		if (spans != null) {
			Iterator<Entry<String, Span>> iterator = spans.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Span> mapEntry = (Map.Entry<String, Span>) iterator.next();
				Span span = mapEntry.getValue();
				if (span.getStart() != null) {
					builder.append(span.getStart());
				}
				builder.append("~");

				if (span.getEnd() != null) {
					builder.append(span.getEnd());
				}
				builder.append("~");
				builder.rightPad(mapEntry.getKey(), 6, ' ');
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
