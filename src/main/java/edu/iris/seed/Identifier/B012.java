package edu.iris.seed.Identifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B012 extends SeedBlockette<B012> implements IdentifierBlockette {

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

	public BlocketteBuilder<B012> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B012> {

		public Builder() {
			super(12);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B012 build(boolean relax) throws SeedException {

			validate(12, 11, bytes);
			B012 b = new B012();

			int offset = 7;
			int numberOfSpans = -1;
			numberOfSpans = SeedStrings.parseInt(bytes, 7, 4);
			offset = 11;
			for (int i = 0; i < numberOfSpans; i++) {
				int from = offset;
				for (;; offset++) {
					if (bytes[offset] == '~') {
						break;
					}
				}
				BTime start = BTime.valueOf(Arrays.copyOfRange(bytes, from, offset));
				offset++;

				from = offset;
				for (;; offset++) {
					if (bytes[offset] == '~') {
						break;
					}
				}
				BTime end = BTime.valueOf(Arrays.copyOfRange(bytes, from, offset));
				offset++;
				String sequence = new String(bytes, offset, 6);
				offset += 6;

				b.add(sequence, new Span(start, end));
			}

			return b;
		}
	}
}
