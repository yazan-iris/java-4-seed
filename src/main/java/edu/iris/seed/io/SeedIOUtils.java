package edu.iris.seed.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iris.seed.Blockette;
import edu.iris.seed.DataBlockette;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedInputStream;
import edu.iris.seed.SeedVolume;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.data.DataSection;
import edu.iris.seed.record.AbbreviationRecord;
import edu.iris.seed.record.DataRecord;
import edu.iris.seed.timeseries.Timeseries;
import edu.iris.seedcodec.CodecException;

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

	public static List<Timeseries> toTimeseries(final InputStream inputStream, boolean reduce)
			throws SeedException, IOException {

		Map<String, Timeseries> map = new HashMap<>();

		try (SeedBlocketteIterator it = toBlocketteIterator(inputStream);) {
			DataRecord dataRecord = null;
			while (it.hasNext()) {
				Blockette b = it.next();
				if (b instanceof SeedDataHeader) {
					SeedDataHeader header = (SeedDataHeader) b;
					String key = header.getNetwork() + header.getStation() + header.getLocation() + header.getChannel();
					Timeseries ts = map.get(key);
					
					if (ts == null) {
						ts = Timeseries.from(header.getNetwork(), header.getStation(), header.getLocation(),
								header.getChannel());
						map.put(key, ts);
					}else {
						if(dataRecord!=null) {
							ts.add(dataRecord, reduce);
						}
					}
					dataRecord = DataRecord.Builder.newInstance().header(header).build();
				} else if (b instanceof DataBlockette) {
					dataRecord.add((DataBlockette) b);
				}
			}
			if(dataRecord!=null) {
				SeedDataHeader header = (SeedDataHeader) dataRecord.getHeader();
				String key = header.getNetwork() + header.getStation() + header.getLocation() + header.getChannel();
				Timeseries ts = map.get(key);
				
				if (ts == null) {
					ts = Timeseries.from(header.getNetwork(), header.getStation(), header.getLocation(),
							header.getChannel());
					map.put(key, ts);
				}
				ts.add(dataRecord, reduce);
			}
			return new ArrayList<>(map.values());
		} catch (CodecException e) {
			throw new SeedException(e);
		}
	}

	public static SeedBlocketteIterator toBlocketteIterator(final InputStream inputStream) {
		return new SeedBlocketteIterator(new SeedInputStream(inputStream));
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
