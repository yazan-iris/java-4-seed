package edu.iris.seed.io;

import java.io.IOException;
import java.io.InputStream;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecordInputStream;
import edu.iris.seed.SeedVolume;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.record.EmptyRecord;
import edu.iris.seed.station.IncompleteBlockette;

public class SeedIOUtils {

	public static SeedVolume toSeedVolume(final InputStream inputStream) throws SeedException, IOException {

		SeedVolume volume = new SeedVolume();
		long index = 0;
		try (SeedRecordInputStream stream = new SeedRecordInputStream(inputStream);) {
			Record<? extends Blockette> r = null;
			while ((r = stream.next()) != null) {
				if (r.isContinuation()) {
					int type = SeedStrings.parseInt(r.getBytes(), 8, 3);
					if (SeedBlockette.controlMap.get(type) == null) {
						throw new SeedException("Did not expect continuation record! index {}", index);
					}
				}
				if (r instanceof EmptyRecord) {
					continue;
				}
				index += r.getBytes().length;
				SeedRecordBlocketteIterator it = new SeedRecordBlocketteIterator(r);

				while (it.hasNext()) {
					Blockette blockette = it.next();
					if (blockette instanceof IncompleteBlockette) {
						IncompleteBlockette incompleteBlockette = (IncompleteBlockette) blockette;

						while (incompleteBlockette.numberOfRequiredBytesToComplete() > 0) {
							r = stream.next();
							if (r == null) {
								throw new SeedException(
										"Error completing blockette of type {} received {},expected {}, expected a new record but was null. Index {}",
										incompleteBlockette.getType(), incompleteBlockette.getBytes().length,
										incompleteBlockette.getExpected(), index);
							} else if (!r.isContinuation()) {
								throw new SeedException("Expected a continuation record but received a new record.");
							} else {
								index += r.getBytes().length;
								it = new SeedRecordBlocketteIterator(r);
								byte[] bytes = it.skip(incompleteBlockette.numberOfRequiredBytesToComplete());
								incompleteBlockette.append(bytes);
							}
						}
						blockette = SeedBlockette.Builder.newInstance().build(incompleteBlockette.getBytes());
					}
					volume.add(blockette);
				}
			}
		}
		return volume;
	}

	public static SeedBlocketteIterator blocketteIterator(final InputStream InputStream) throws IOException {
		return new SeedBlocketteIterator(new SeedRecordInputStream(InputStream));
	}

	public static SeedRecordIterator recordIterator(final InputStream InputStream) throws IOException {
		return new SeedRecordIterator(InputStream);
	}

}
