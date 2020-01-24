package edu.iris.seed.io;

import java.io.IOException;
import java.io.InputStream;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedInputStream;
import edu.iris.seed.SeedVolume;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.record.AbbreviationRecord;

public class SeedIOUtils {

	public static SeedInputStream toSeedInputStream(final InputStream inputStream) throws SeedException, IOException {
		return new SeedInputStream(inputStream);
	}

	public static SeedVolume toSeedVolume(final InputStream inputStream) throws SeedException, IOException {

		SeedVolume volume = new SeedVolume();

		try (SeedBlocketteIterator it = toBlocketteIterator(inputStream);) {
			while (it.hasNext()) {
				volume.add(it.next());
			}
		}
		return volume;
	}

	public static SeedBlocketteIterator toBlocketteIterator(final InputStream inputStream) {
		return toBlocketteIterator(inputStream, false);
	}

	public static SeedBlocketteIterator toBlocketteIterator(final InputStream inputStream, boolean relax) {
		return new SeedBlocketteIterator(new SeedInputStream(inputStream), relax);
	}

	public static AbbreviationRecord toAbbreviationRecord(final InputStream inputStream)
			throws SeedException, IOException {
		return toAbbreviationRecord(inputStream, false);
	}

	public static AbbreviationRecord toAbbreviationRecord(final InputStream inputStream, boolean relax)
			throws SeedException, IOException

	{

		AbbreviationRecord r = new AbbreviationRecord();

		try (SeedBlocketteIterator it = new SeedBlocketteIterator(new SeedInputStream(inputStream));) {
			while (it.hasNext()) {
				Blockette b = it.next();
				if (b.getType() >= 50) {
					break;
				}
				r.add((AbbreviationBlockette) it.next());
			}
		}
		return r;
	}

}
