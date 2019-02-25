package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.dictionary.B034;
import edu.iris.dmc.seed.control.index.B010;
import edu.iris.dmc.seed.control.index.B011;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.B052;
import edu.iris.dmc.seed.control.station.B054;
import edu.iris.dmc.seed.control.station.ResponseBlockette;
import edu.iris.dmc.seed.control.station.SeedResponseStage;

public class VolumeTest {

	static Volume volume;

	@BeforeClass
	public static void onceExecutedBeforeAll() throws Exception {
		File source = new File(
				VolumeTest.class.getClassLoader().getResource("II_NNA.20180906T205825.datales").getFile());

		volume = TestUtil.load(source);
	}

	@Test
	public void getAll() {
		List<Blockette> blockettes = volume.getAll();
		assertNotNull(blockettes);
		assertEquals(2876, blockettes.size());

		volume.getNumberOfStations();

	}

	@Test
	public void b050s() {
		List<B050> blockettes = volume.getB050s();
		assertNotNull(blockettes);
		assertEquals(1, blockettes.size());

		assertEquals(1, volume.getNumberOfStations());
	}

	@Test
	public void b052s() {
		List<B050> list = volume.getB050s();
		assertNotNull(list);
		assertEquals(1, list.size());

		assertEquals(1, volume.getNumberOfStations());

		B050 b050 = list.get(0);

		List<B052> b052s = b050.getB052s();
		assertNotNull(b052s);
		assertEquals(188, b052s.size());
	}

	@Test
	public void find() {

		List<Blockette> list = volume.find("II", "NNA", "BHE", null);
		assertNotNull(list);
		assertEquals(8, list.size());

		list = volume.find("II", "NNA", "BHE", "00");
		assertNotNull(list);
		assertEquals(6, list.size());

		list = volume.find("II", "NNA", "BHE", "XX");
		assertNotNull(list);
		assertEquals(1, list.size());
	}

	@Test
	public void b054Short() {
		List<Blockette> list = volume.find("II", "NNA", "BHE", "00");
		assertNotNull(list);
		assertEquals(6, list.size());

		assertEquals(1, volume.getNumberOfStations());
		B052 theChannel = null;
		for (Blockette b : list) {
			assertTrue(b instanceof B052);
			B052 b052 = (B052) b;
			if (2002 == b052.getStartTime().getYear()) {
				theChannel = b052;
				break;
			}
		}
		assertNotNull(theChannel);

		List<SeedResponseStage> responseStages = theChannel.getResponseStages();
		assertNotNull(responseStages);
		assertEquals(5, responseStages.size());

		SeedResponseStage stage4 = theChannel.getResponseStage(4);
		assertNotNull(stage4);
		assertEquals(3, stage4.size());
		
		B054 b054 = null;
		for (ResponseBlockette r : stage4.getBlockettes()) {
			if (54 == r.getType()) {
				b054 = (B054) r;
				break;
			}
		}

		assertNotNull(b054);
		assertEquals(99, b054.getNumerators().size());

		assertEquals(-0.000000337741, b054.getNumerators().get(0).getValue(), 0.000000000001);
		assertEquals(-0.000000862909, b054.getNumerators().get(1).getValue(), 0.000000000001);
		assertEquals(-0.00000154942, b054.getNumerators().get(2).getValue(), 0.000000000001);
		assertEquals(-0.00000204037, b054.getNumerators().get(3).getValue(), 0.000000000001);
		assertEquals(-0.00000160361, b054.getNumerators().get(4).getValue(), 0.000000000001);
		assertEquals(0.000000876701, b054.getNumerators().get(5).getValue(), 0.000000000001);
		assertEquals(0.00000673039, b054.getNumerators().get(6).getValue(), 0.000000000001);
		assertEquals(0.0000170900, b054.getNumerators().get(7).getValue(), 0.000000000001);
		assertEquals(0.0000322292, b054.getNumerators().get(8).getValue(), 0.000000000001);
		assertEquals(0.0000507341, b054.getNumerators().get(9).getValue(), 0.000000000001);
		assertEquals(0.0000687046, b054.getNumerators().get(10).getValue(), 0.0000000001);
		assertEquals(0.0000792878, b054.getNumerators().get(11).getValue(), 0.0000000001);
		assertEquals(0.0000729083, b054.getNumerators().get(12).getValue(), 0.0000000001);
		assertEquals(0.0000385256, b054.getNumerators().get(13).getValue(), 0.0000000001);
		assertEquals(-0.0000338990, b054.getNumerators().get(14).getValue(), 0.0000000001);
		assertEquals(-0.000149824, b054.getNumerators().get(15).getValue(), 0.0000000001);
		assertEquals(-0.000305915, b054.getNumerators().get(16).getValue(), 0.0000000001);
		assertEquals(-0.000486084, b054.getNumerators().get(17).getValue(), 0.0000000001);
		assertEquals(-0.000658988, b054.getNumerators().get(18).getValue(), 0.0000000001);
		assertEquals(-0.000778327, b054.getNumerators().get(19).getValue(), 0.0000000001);
		assertEquals(-0.000787102, b054.getNumerators().get(20).getValue(), 0.0000000001);

		assertEquals(-0.000626427, b054.getNumerators().get(21).getValue(), 0.0000000001);
		assertEquals(-0.000248597, b054.getNumerators().get(22).getValue(), 0.0000000001);
		assertEquals(0.000367088, b054.getNumerators().get(23).getValue(), 0.0000000001);
		assertEquals(0.00119836, b054.getNumerators().get(24).getValue(), 0.0000000001);
		assertEquals(0.00216751, b054.getNumerators().get(25).getValue(), 0.000000001);
		assertEquals(0.00313647, b054.getNumerators().get(26).getValue(), 0.000000001);
		assertEquals(0.00391289, b054.getNumerators().get(27).getValue(), 0.000000001);
		assertEquals(0.00426971, b054.getNumerators().get(28).getValue(), 0.000000001);
		assertEquals(0.00397829, b054.getNumerators().get(29).getValue(), 0.000000001);
		assertEquals(0.00285239, b054.getNumerators().get(30).getValue(), 0.000000001);

		assertEquals(0.000797673, b054.getNumerators().get(31).getValue(), 0.0000000001);
		assertEquals(-0.00214136, b054.getNumerators().get(32).getValue(), 0.0000000001);
		assertEquals(-0.00574629, b054.getNumerators().get(33).getValue(), 0.0000000001);
		assertEquals(-0.00961061, b054.getNumerators().get(34).getValue(), 0.0000000001);
		assertEquals(-0.0131538, b054.getNumerators().get(35).getValue(), 0.0000001);
		assertEquals(-0.0156670, b054.getNumerators().get(36).getValue(), 0.00000001);
		assertEquals(-0.0163887, b054.getNumerators().get(37).getValue(), 0.00000001);
		assertEquals(-0.0146032, b054.getNumerators().get(38).getValue(), 0.00000001);
		assertEquals(-0.00975057, b054.getNumerators().get(39).getValue(), 0.00000001);
		assertEquals(-0.00153046, b054.getNumerators().get(40).getValue(), 0.00000001);

		assertEquals(0.0100149, b054.getNumerators().get(41).getValue(), 0.000000001);
		assertEquals(0.0244562, b054.getNumerators().get(42).getValue(), 0.000000001);
		assertEquals(0.0409804, b054.getNumerators().get(43).getValue(), 0.00000001);
		assertEquals(0.0584477, b054.getNumerators().get(44).getValue(), 0.00000001);
		assertEquals(0.0754974, b054.getNumerators().get(45).getValue(), 0.00000001);
		assertEquals(0.0906920, b054.getNumerators().get(46).getValue(), 0.00000001);
		assertEquals(0.102680, b054.getNumerators().get(47).getValue(), 0.000001);
		assertEquals(0.110357, b054.getNumerators().get(48).getValue(), 0.000001);
		assertEquals(0.113000, b054.getNumerators().get(49).getValue(), 0.000001);
		assertEquals(0.110357, b054.getNumerators().get(50).getValue(), 0.000001);

		assertEquals(0.102680, b054.getNumerators().get(51).getValue(), 0.000001);
		assertEquals(0.0906920, b054.getNumerators().get(52).getValue(), 0.000001);
		assertEquals(0.0754974, b054.getNumerators().get(53).getValue(), 0.000001);
		assertEquals(0.0584477, b054.getNumerators().get(54).getValue(), 0.000001);
		assertEquals(0.0409804, b054.getNumerators().get(55).getValue(), 0.000001);
		assertEquals(0.0244562, b054.getNumerators().get(56).getValue(), 0.000001);
		assertEquals(0.0100149, b054.getNumerators().get(57).getValue(), 0.000001);
		assertEquals(-0.00153046, b054.getNumerators().get(58).getValue(), 0.00000001);
		assertEquals(-0.00975057, b054.getNumerators().get(59).getValue(), 0.00000001);
		assertEquals(-0.0146032, b054.getNumerators().get(60).getValue(), 0.0000001);

		assertEquals(-0.0163887, b054.getNumerators().get(61).getValue(), 0.000000001);
		assertEquals(-0.0156670, b054.getNumerators().get(62).getValue(), 0.000000001);
		assertEquals(-0.0131538, b054.getNumerators().get(63).getValue(), 0.000000001);
		assertEquals(-0.00961061, b054.getNumerators().get(64).getValue(), 0.000000001);
		assertEquals(-0.00574629, b054.getNumerators().get(65).getValue(), 0.000000001);
		assertEquals(-0.00214136, b054.getNumerators().get(66).getValue(), 0.000000001);
		assertEquals(0.000797673, b054.getNumerators().get(67).getValue(), 0.000000001);
		assertEquals(0.00285239, b054.getNumerators().get(68).getValue(), 0.000000001);
		assertEquals(0.00397829, b054.getNumerators().get(69).getValue(), 0.000000001);
		assertEquals(0.00426971, b054.getNumerators().get(70).getValue(), 0.000000001);

		assertEquals(0.00391289, b054.getNumerators().get(71).getValue(), 0.00000001);
		assertEquals(0.00313647, b054.getNumerators().get(72).getValue(), 0.00000001);
		assertEquals(0.00216751, b054.getNumerators().get(73).getValue(), 0.00000001);
		assertEquals(0.00119836, b054.getNumerators().get(74).getValue(), 0.00000001);
		assertEquals(0.000367088, b054.getNumerators().get(75).getValue(), 0.00000001);
		assertEquals(-0.000248597, b054.getNumerators().get(76).getValue(), 0.00000001);
		assertEquals(-0.000626427, b054.getNumerators().get(77).getValue(), 0.00000001);
		assertEquals(-0.000787102, b054.getNumerators().get(78).getValue(), 0.00000001);
		assertEquals(-0.000778327, b054.getNumerators().get(79).getValue(), 0.00000001);
		assertEquals(-0.000658988, b054.getNumerators().get(80).getValue(), 0.00000001);

		assertEquals(-0.000486084, b054.getNumerators().get(81).getValue(), 0.0000001);
		assertEquals(-0.000305915, b054.getNumerators().get(82).getValue(), 0.0000001);
		assertEquals(-0.000149824, b054.getNumerators().get(83).getValue(), 0.0000001);
		assertEquals(-0.0000338990, b054.getNumerators().get(84).getValue(), 0.0000001);
		assertEquals(0.0000385256, b054.getNumerators().get(85).getValue(), 0.0000001);
		assertEquals(0.0000729083, b054.getNumerators().get(86).getValue(), 0.0000001);
		assertEquals(0.0000792878, b054.getNumerators().get(87).getValue(), 0.0000001);
		assertEquals(0.0000687046, b054.getNumerators().get(88).getValue(), 0.0000001);
		assertEquals(0.0000507341, b054.getNumerators().get(89).getValue(), 0.0000001);
		assertEquals(0.0000322292, b054.getNumerators().get(90).getValue(), 0.0000001);

		assertEquals(0.0000170900, b054.getNumerators().get(91).getValue(), 0.0000001);
		assertEquals(0.00000673039, b054.getNumerators().get(92).getValue(), 0.0000001);
		assertEquals(0.000000876701, b054.getNumerators().get(93).getValue(), 0.0000001);
		assertEquals(-0.00000160361, b054.getNumerators().get(94).getValue(), 0.0000001);
		assertEquals(-0.00000204037, b054.getNumerators().get(95).getValue(), 0.0000001);
		assertEquals(-0.00000154942, b054.getNumerators().get(96).getValue(), 0.0000001);
		assertEquals(-0.000000862909, b054.getNumerators().get(97).getValue(), 0.0000001);
		assertEquals(-0.000000337741, b054.getNumerators().get(98).getValue(), 0.0000001);
	}

	@Test
	public void b054Long() {
		List<Blockette> list = volume.find("II", "NNA", "BHE", "00");
		assertNotNull(list);
		assertEquals(6, list.size());

		assertEquals(1, volume.getNumberOfStations());
		B052 theChannel = null;
		for (Blockette b : list) {
			assertTrue(b instanceof B052);
			B052 b052 = (B052) b;
			if (2007 == b052.getStartTime().getYear()) {
				theChannel = b052;
				break;
			}
		}
		assertNotNull(theChannel);

		List<SeedResponseStage> responseStages = theChannel.getResponseStages();
		assertNotNull(responseStages);

		
		SeedResponseStage stage5 = theChannel.getResponseStage(5);
		assertNotNull(stage5);
		assertEquals(3, stage5.size());
		
		

		B054 b054 = null;
		for (ResponseBlockette r : stage5.getBlockettes()) {
			if (54 == r.getType()) {
				b054 = (B054) r;
				break;
			}
		}

		assertNotNull(b054);
		assertEquals(241, b054.getNumerators().size());

		assertEquals(0.0000000242691, b054.getNumerators().get(0).getValue(), 0.000000000001);
		assertEquals(0.0000000242691, b054.getNumerators().get(240).getValue(), 0.0000001);
	}

	public void index() {

	}

	public void dictionary() {

	}

	public void station() {

	}

	public void stationComments() {

	}

	public void channelComments() {

	}

	// @Test
	public void d() throws Exception {
		Volume v = new Volume();

		B034 b034 = new B034();
		b034.setDescription("This is test 1");
		b034.setName("test 1");
		v.add(b034);

		b034 = new B034();
		b034.setDescription("This is test 1");
		b034.setName("test 1");
		v.add(b034);
		assertEquals(1, v.getDictionaryBlockettes().size());
	}

	// @Test
	public void dd() throws Exception {
		Volume v = new Volume();

		v.add(BlocketteBuilder.build010(
				"010008402.4121950,001,00:00:00.0000~2500,001,00:00:00.0000~2013,346,00:43:29.0000~~~".getBytes()));
		v.add(BlocketteBuilder.build011("0110021001ESCA 000003".getBytes()));
		v.add(BlocketteBuilder.build030(
				"0300205Steim1~000105006F1 P4 W4 D0-31 C2 R1 P8 W4 D0-1 C2~P0 W4 N15 S2,0,1~T0 X N0 W4 D0-31 C2~T1 N0 W1 D0-7 C2 N1 W1 D0-7 C2 N2 W1 D0-7 C2 N3 W1 D0-7 C2~T2 N0 W2 D0-15 C2 N1 W2 D0-15 C2~T3 N0 W4 D0-31 C2~"
						.getBytes()));
		v.add(BlocketteBuilder.build033("0330041001RESIF - OCA - Valbonne, France~".getBytes()));
		v.add(BlocketteBuilder.build033("0330017002CMG3-T~".getBytes()));
		v.add(BlocketteBuilder.build033("0330018003CMG40-T~".getBytes()));
		v.add(BlocketteBuilder.build033("0330026004KINEMETRICS EST~".getBytes()));
		v.add(BlocketteBuilder.build034("0340023001M/S~Velocity~".getBytes()));
		v.add(BlocketteBuilder.build034("0340018002V~Volts~".getBytes()));
		v.add(BlocketteBuilder.build034("0340032003COUNTS~Digital Counts~".getBytes()));
		v.add(BlocketteBuilder.build034("0340030004M/S**2~Acceleration~".getBytes()));

		v.add(BlocketteBuilder.build050(
				"0500140ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace  l'Escarne~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR"
						.getBytes()));
		v.add(BlocketteBuilder.build052(
				"052015000BHE00000020~001002+43.831000+007.374000+0550.0000.6090.0+00.00001122.5000E+011.0000E-060000CG~2006,018,02:30:00.0000~2008,163,11:06:00.0000~N"
						.getBytes()));
		v.add(BlocketteBuilder.build053(
				"0530382B01001002+2.30426E+06+1.00000E+00002+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00005-5.89000E-03-5.89000E-03+0.00000E+00+0.00000E+00-5.89000E-03+5.89000E-03+0.00000E+00+0.00000E+00-1.80000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.60000E+02+0.00000E+00+0.00000E+00+0.00000E+00-8.00000E+01+0.00000E+00+0.00000E+00+0.00000E+00"
						.getBytes()));
		v.add(BlocketteBuilder.build058("058003501+1.40600E+03+1.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build054("0540024D0200200300000000".getBytes()));
		v.add(BlocketteBuilder.build057("057005102+5.000E+020000100000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003502+1.19006E+06+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061011903~B0030030007+2.4414060E-04+2.9296880E-03+1.6113280E-02+5.3710940E-02+1.2084960E-01+1.9335940E-01+2.2558590E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005103+5.000E+020000200000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003503+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061073504~B0030030051-3.0998200E-06-2.9448290E-05-9.8002010E-05-1.6238290E-04-1.0002880E-04+1.2065450E-04+2.6193480E-04+2.5275460E-05-4.1048770E-04-3.6685180E-04+3.7627050E-04+8.5459660E-04-3.0521310E-05-1.2767680E-03-9.1134720E-04+1.2772450E-03+2.1525870E-03-4.6175400E-04-3.3391030E-03-1.4099410E-03+3.7723620E-03+4.1959640E-03-2.6440270E-03-7.2043400E-03-6.4428570E-04+9.1879870E-03+6.0870930E-03-8.5819720E-03-1.2745630E-02+3.9839840E-03+1.8634210E-02+5.2074600E-03-2.0949780E-02-1.8170790E-02+1.6674170E-02+3.2258760E-02-3.4673870E-03-4.2971500E-02-1.9334890E-02+4.4328260E-02+4.9812560E-02-2.9429220E-02-8.2643710E-02-9.3457200E-03+1.0759920E-01+8.1695880E-02-1.0315450E-01-2.0429700E-01-3.1236650E-05+3.9060220E-01+5.9021430E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005104+2.500E+020000200000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003504+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061114105~C0030030080+4.0324610E-05+7.4532800E-05+1.2345530E-04+1.7018870E-04+1.9731050E-04+1.8548910E-04+1.1934560E-04-5.7231010E-06-1.7792320E-04-3.6732590E-04-5.2951040E-04-6.1500850E-04-5.8323540E-04-4.1728370E-04-1.3495160E-04+2.0833300E-04+5.2770900E-04+7.2818990E-04+7.3125870E-04+5.0192020E-04+6.7831760E-05-4.7714930E-04-9.8915800E-04-1.3089180E-03-1.3073580E-03-9.3001680E-04-2.2625400E-04+6.4834760E-04+1.4617080E-03+1.9632220E-03+1.9566250E-03+1.3677250E-03+2.8546280E-04-1.0403870E-03-2.2506790E-03-2.9690690E-03-2.9127370E-03-1.9905830E-03-3.5735370E-04+1.5988400E-03+3.3409720E-03+4.3237640E-03+4.1556360E-03+2.7360020E-03+3.2343100E-04-2.4947520E-03-4.9349430E-03-6.2251970E-03-5.8361360E-03-3.6689660E-03-1.3940920E-04+3.8802280E-03+7.2612310E-03+8.9193560E-03+8.1402520E-03+4.8370500E-03-3.4347850E-04-6.1156650E-03-1.0847780E-02-1.2992720E-02-1.1549950E-02-6.4303770E-03+1.3911980E-03+1.0005710E-02+1.6980570E-02+1.9973400E-02+1.7406640E-02+9.0294630E-03-3.7949690E-03-1.8183040E-02-3.0222950E-02-3.5783330E-02-3.1468980E-02-1.5504440E-02+1.1672370E-02+4.7268330E-02+8.6508190E-02+1.2346680E-01+1.5219420E-01+1.6789390E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005105+1.250E+020000500000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003505+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build058("058003500+1.67323E+09+1.00000E+0000".getBytes()));

		// v.build();

		B010 b010 = v.getB010();
		assertNotNull(b010);
		assertEquals(12, b010.getNthPower());
		B011 b011 = v.getB011();
		assertNotNull(b011);
		assertNotNull(b011.getRows());
		assertEquals(1, b011.getRows().size());
		assertEquals(1, b011.getNumberOfStations());
		b010.setNthPower(8);
		assertEquals(8, v.getB010().getNthPower());
		v.build();
		b011 = v.getB011();
		assertNotNull(b011);
		assertEquals(1, b011.getNumberOfStations());
		List<B011.Row> records = b011.getRows();
		assertNotNull(records);
		assertEquals(1, records.size());
		// 000001V
		// 010008402.4121950,001,00:00:00.0000~2500,001,00:00:00.0000~2013,346,00:43:29.0000~~~
		// 0110021001ESCA 000003
	}

	// @Test
	public void t3() throws Exception {

		Volume v = new Volume();

		v.add(BlocketteBuilder.build010(
				"010008402.4121950,001,00:00:00.0000~2500,001,00:00:00.0000~2013,346,00:43:29.0000~~~".getBytes()));
		v.add(BlocketteBuilder.build011("0110021001ESCA 000003".getBytes()));
		v.add(BlocketteBuilder.build030(
				"0300205Steim1~000105006F1 P4 W4 D0-31 C2 R1 P8 W4 D0-1 C2~P0 W4 N15 S2,0,1~T0 X N0 W4 D0-31 C2~T1 N0 W1 D0-7 C2 N1 W1 D0-7 C2 N2 W1 D0-7 C2 N3 W1 D0-7 C2~T2 N0 W2 D0-15 C2 N1 W2 D0-15 C2~T3 N0 W4 D0-31 C2~"
						.getBytes()));
		v.add(BlocketteBuilder.build033("0330041001RESIF - OCA - Valbonne, France~".getBytes()));
		v.add(BlocketteBuilder.build033("0330017002CMG3-T~".getBytes()));
		v.add(BlocketteBuilder.build033("0330018003CMG40-T~".getBytes()));
		v.add(BlocketteBuilder.build033("0330026004KINEMETRICS EST~".getBytes()));
		v.add(BlocketteBuilder.build034("0340023001M/S~Velocity~".getBytes()));
		v.add(BlocketteBuilder.build034("0340018002V~Volts~".getBytes()));
		v.add(BlocketteBuilder.build034("0340032003COUNTS~Digital Counts~".getBytes()));
		v.add(BlocketteBuilder.build034("0340030004M/S**2~Acceleration~".getBytes()));

		v.add(BlocketteBuilder.build050(
				"0500140ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace  l'Escarne~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR"
						.getBytes()));
		v.add(BlocketteBuilder.build052(
				"052015000BHE00000020~001002+43.831000+007.374000+0550.0000.6090.0+00.00001122.5000E+011.0000E-060000CG~2006,018,02:30:00.0000~2008,163,11:06:00.0000~N"
						.getBytes()));
		v.add(BlocketteBuilder.build053(
				"0530382B01001002+2.30426E+06+1.00000E+00002+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00005-5.89000E-03-5.89000E-03+0.00000E+00+0.00000E+00-5.89000E-03+5.89000E-03+0.00000E+00+0.00000E+00-1.80000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.60000E+02+0.00000E+00+0.00000E+00+0.00000E+00-8.00000E+01+0.00000E+00+0.00000E+00+0.00000E+00"
						.getBytes()));
		v.add(BlocketteBuilder.build058("058003501+1.40600E+03+1.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build054("0540024D0200200300000000".getBytes()));
		v.add(BlocketteBuilder.build057("057005102+5.000E+020000100000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003502+1.19006E+06+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061011903~B0030030007+2.4414060E-04+2.9296880E-03+1.6113280E-02+5.3710940E-02+1.2084960E-01+1.9335940E-01+2.2558590E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005103+5.000E+020000200000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003503+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061073504~B0030030051-3.0998200E-06-2.9448290E-05-9.8002010E-05-1.6238290E-04-1.0002880E-04+1.2065450E-04+2.6193480E-04+2.5275460E-05-4.1048770E-04-3.6685180E-04+3.7627050E-04+8.5459660E-04-3.0521310E-05-1.2767680E-03-9.1134720E-04+1.2772450E-03+2.1525870E-03-4.6175400E-04-3.3391030E-03-1.4099410E-03+3.7723620E-03+4.1959640E-03-2.6440270E-03-7.2043400E-03-6.4428570E-04+9.1879870E-03+6.0870930E-03-8.5819720E-03-1.2745630E-02+3.9839840E-03+1.8634210E-02+5.2074600E-03-2.0949780E-02-1.8170790E-02+1.6674170E-02+3.2258760E-02-3.4673870E-03-4.2971500E-02-1.9334890E-02+4.4328260E-02+4.9812560E-02-2.9429220E-02-8.2643710E-02-9.3457200E-03+1.0759920E-01+8.1695880E-02-1.0315450E-01-2.0429700E-01-3.1236650E-05+3.9060220E-01+5.9021430E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005104+2.500E+020000200000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003504+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build061(
				"061114105~C0030030080+4.0324610E-05+7.4532800E-05+1.2345530E-04+1.7018870E-04+1.9731050E-04+1.8548910E-04+1.1934560E-04-5.7231010E-06-1.7792320E-04-3.6732590E-04-5.2951040E-04-6.1500850E-04-5.8323540E-04-4.1728370E-04-1.3495160E-04+2.0833300E-04+5.2770900E-04+7.2818990E-04+7.3125870E-04+5.0192020E-04+6.7831760E-05-4.7714930E-04-9.8915800E-04-1.3089180E-03-1.3073580E-03-9.3001680E-04-2.2625400E-04+6.4834760E-04+1.4617080E-03+1.9632220E-03+1.9566250E-03+1.3677250E-03+2.8546280E-04-1.0403870E-03-2.2506790E-03-2.9690690E-03-2.9127370E-03-1.9905830E-03-3.5735370E-04+1.5988400E-03+3.3409720E-03+4.3237640E-03+4.1556360E-03+2.7360020E-03+3.2343100E-04-2.4947520E-03-4.9349430E-03-6.2251970E-03-5.8361360E-03-3.6689660E-03-1.3940920E-04+3.8802280E-03+7.2612310E-03+8.9193560E-03+8.1402520E-03+4.8370500E-03-3.4347850E-04-6.1156650E-03-1.0847780E-02-1.2992720E-02-1.1549950E-02-6.4303770E-03+1.3911980E-03+1.0005710E-02+1.6980570E-02+1.9973400E-02+1.7406640E-02+9.0294630E-03-3.7949690E-03-1.8183040E-02-3.0222950E-02-3.5783330E-02-3.1468980E-02-1.5504440E-02+1.1672370E-02+4.7268330E-02+8.6508190E-02+1.2346680E-01+1.5219420E-01+1.6789390E-01"
						.getBytes()));
		v.add(BlocketteBuilder.build057("057005105+1.250E+020000500000+0.0000E+00+0.0000E+00".getBytes()));
		v.add(BlocketteBuilder.build058("058003505+1.00000E+00+0.00000E+0000".getBytes()));
		v.add(BlocketteBuilder.build058("058003500+1.67323E+09+1.00000E+0000".getBytes()));

		// v.build();

		B010 b010 = v.getB010();
		assertNotNull(b010);
		assertEquals(12, b010.getNthPower());
		B011 b011 = v.getB011();
		assertNotNull(b011);
		assertNotNull(b011.getRows());
		assertEquals(1, b011.getRows().size());
		assertEquals(1, b011.getNumberOfStations());
		b010.setNthPower(8);
		assertEquals(8, v.getB010().getNthPower());
		v.build();
		b011 = v.getB011();
		assertNotNull(b011);
		assertEquals(1, b011.getNumberOfStations());
		List<B011.Row> records = b011.getRows();
		assertNotNull(records);
		assertEquals(1, records.size());
		assertEquals(2, records.get(0).getSequence());
		// 000001V
		// 010008402.4121950,001,00:00:00.0000~2500,001,00:00:00.0000~2013,346,00:43:29.0000~~~
		// 0110021001ESCA 000003
	}

}
