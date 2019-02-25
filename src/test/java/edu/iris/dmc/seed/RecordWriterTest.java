package edu.iris.dmc.seed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import edu.iris.dmc.seed.io.RecordWriter;

public class RecordWriterTest {

	@Test
	public void write() throws Exception {
		// File tempFile = File.createTempFile("prefix-", "-suffix");
		Path path = Files.createTempFile("tempfiles", ".tmp");
		// tempFile.deleteOnExit();

		int recordLength = 200;
		int numberOfRecords = 0;
		try (OutputStream os = Files.newOutputStream(path)) {
			RecordWriter writer = new RecordWriter(os, 'V', recordLength);
			Blockette b = TestUtil.create(
					"0500154I58H1+28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM");

			writer.write(b);
			b = TestUtil.create("05100632012,275,06:52:37.0000~2014,317,01:06:40.0000~0001000000");
			writer.write(b);

			b = TestUtil.create(
					"0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N");
			writer.write(b);

			b = TestUtil.create(
					"0500154I58H1+28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM");
			writer.write(b);
			numberOfRecords = writer.getSequence();
		}

		int sequence = 2;
		char[] characters = new char[6];
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("/Users/Suleiman/seed.seed"),
					StandardCharsets.UTF_8)) {
				for (int i = 0; i < numberOfRecords; i++) {
					int result = reader.read(characters);
					// make sure characters
					writer.write(String.format("%06d", sequence));
					// read and write until end of record
					char[] buffer = new char[recordLength - 6];
					result = reader.read(buffer);
					writer.write(buffer);
					sequence++;
				}
			}
		}

	}
}
