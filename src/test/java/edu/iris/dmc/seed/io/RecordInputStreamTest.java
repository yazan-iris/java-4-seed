package edu.iris.dmc.seed.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.IncompleteBlockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.blockette.util.BItrator;

public class RecordInputStreamTest {

	@Test
	public void dd() throws Exception {
		File source = new File(RecordInputStreamTest.class.getClassLoader()
				.getResource("AU.MILA.dataless.fromHughGlanville.20181018").getFile());
		try (RecordInputStream seedRecordInputStream = new RecordInputStream(new FileInputStream(source))) {

			Record r = null;
			while ((r = seedRecordInputStream.next()) != null) {
				if (r.isContinuation()) {
					throw new Exception("Did not expect continuation record!");
				}

				System.out.println(r);

				Iterator<Blockette> it = new BItrator(r);
				while (it.hasNext()) {
					Blockette blockette = it.next();
					System.out.println(blockette.toSeedString());
					if (blockette instanceof IncompleteBlockette) {
						IncompleteBlockette incompleteBlockette = (IncompleteBlockette) blockette;

						while (incompleteBlockette.numberOfRequiredBytesToComplete() > 0) {
							r = seedRecordInputStream.next();
							if (r == null) {
								throw new Exception("Expected a new record but was null.");
							} else if (!r.isContinuation()) {
								throw new Exception("Expected a continuation record but received a new record.");
							} else {
								byte[] bytes = r.get(incompleteBlockette.numberOfRequiredBytesToComplete());
								incompleteBlockette.append(bytes);
							}
						}
						blockette = BlocketteFactory.create(incompleteBlockette.getBytes());
						it = new BItrator(r);
					}
				}
			}
		} catch (RuntimeException e) {
			if (e.getCause() instanceof SeedException) {
				e.printStackTrace();
				SeedException s=(SeedException)e.getCause();
				System.out.println(s.getIndex()+":::"+new String(s.getBytes()));
			}
		}
	}
}
