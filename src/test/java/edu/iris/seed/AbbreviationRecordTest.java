package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.record.AbbreviationRecord;
import edu.iris.seed.SeedHeader.Type;

public class AbbreviationRecordTest {

	@Test
	public void getAll() throws Exception {
		AbbreviationRecord r = new AbbreviationRecord();
		assertEquals(Type.A, r.getType());

		r.add((AbbreviationBlockette) SeedBlockette.builder(31)
				.fromString("03100860180SEdit configuration (channel epoch) Geologic site class code changed. O~000")
				.build());
		r.add((AbbreviationBlockette) SeedBlockette.builder(31)
				.fromString("03100860181SEdit configuration (channel epoch) Physical condition code changed. Ol~000")
				.build());
		r.add((AbbreviationBlockette) SeedBlockette.builder(31)
				.fromString("03100860182SEdit configuration (channel epoch) Station housing code changed. Old v~000")
				.build());
		r.add((AbbreviationBlockette) SeedBlockette.builder(31)
				.fromString("03100860183SEdit configuration (channel epoch) VS 30 changed. Old value: , new val~000")
				.build());
		r.add((AbbreviationBlockette) SeedBlockette.builder(31)
				.fromString("03100860183SEdit configuration (channel epoch) VS 30 changed. Old value: , new val~000")
				.build());
		r.add((AbbreviationBlockette) SeedBlockette.builder(31).fromString("03100390184SINSTALL NEW INSTRUMENTS~000")
				.build());

		assertEquals(5, r.blockettes().size());

	}
}
