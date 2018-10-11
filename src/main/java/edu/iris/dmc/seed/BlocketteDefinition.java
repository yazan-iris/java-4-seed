package edu.iris.dmc.seed;



public class BlocketteDefinition {

	/**
	 * Get blockette definitions. All information is provided as a set of static
	 * String returns in a rudimentary selection branch so that no time is
	 * wasted loading data, since this is a statically invoked class...the data
	 * is effectively bound with the code. The first record is the general
	 * blockette definition, followed by subsequent records detailing each
	 * blockette field. Each record is separated by a newline character and the
	 * columns of each record are tab-separated. The <i>Number Fields</i> column
	 * is a specially formatted string of the form a0=b0,a1=b1,... where (a) is
	 * the SEED version and (b) is the number of fields available at that
	 * version. Newer versions always append fields to the end of the blockette.
	 * The first version number (a0) always represents the initial version of
	 * the blockette. This method is kept public so that applications have the
	 * option of dumping the entire definition outward for their own purposes,
	 * such as some form of documentation.
	 */
	public static String describe(int blkType) throws SeedException {
		switch (blkType) {

		// VOLUME INDEX CONTROL HEADER BLOCKETTES

		case 5:
			return
			// Type Name Category Number Fields
			"005	Field Volume Identifier Blockette	Volume Index	2.0=5\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 005		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Version of format		D	4	\"00.0\"		0\n"
					+ "4	Logical record length		D	2	\"00\"			0\n"
					+ "5	Beginning of volume		V	1-22	TIME			0\n";
		case 8:
			return
			// Type Name Category Number Fields
			"008	Telemetry Volume Identifier Blockette	Volume Index	2.1=11\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 008		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Version of format		D	4	\"00.0\"		0\n"
					+ "4	Logical record length		D	2	\"00\"			0\n"
					+ "5	Station identifier		A	5	[UN]			0\n"
					+ "6	Location identifier		A	2	[UNS]			0\n"
					+ "7	Channel identifier		A	3	[UN]			0\n"
					+ "8	Beginning of volume		V	1-22	TIME			0\n"
					+ "9	End of volume			V	1-22	TIME			0\n"
					+ "10	Station info effective date	V	1-22	TIME			0\n"
					+ "11	Channel info effective date	V	1-22	TIME			0\n"
					+ "12	Network Code			A	2	[UN]			0\n";
		case 10:
			return
			// Type Name Category Number Fields
			"010	Volume Identifier Blockette		Volume Index	2.0=6,2.3=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 010		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Version of format		D	4	\"00.0\"		0\n"
					+ "4	Logical record length		D	2	\"00\"			0\n"
					+ "5	Beginning time			V	1-22	TIME			0\n"
					+ "6	End time			V	1-22	TIME			0\n"
					+ "7	Volume Time			V	1-22	TIME			0\n"
					+ "8	Originating Organization	V	1-80	[]			0\n"
					+ "9	Label				V	1-80	[]			0\n";
		case 11:
			return
			// Type Name Category Number Fields
			"011	Volume Station Header Index Blockette	Volume Index	2.0=5\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 011		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Number of stations		D	3	\"000\"			0\n" +
					// REPEAT fields 4-5 for the Number of stations:
					"4	Station identifier code		A	5	[]			3\n"
					+ "5	Sequence no. of station header	D	6	\"000000\"		3\n";
		case 12:
			return
			// Type Name Category Number Fields
			"012	Volume Time Span Index Blockette	Volume Index	2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 012		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Number of spans in table	D	4	\"0000\"		0\n" +
					// REPEAT fields 4-6 for the Number of spans in table:
					"4	Beginning of span		V	1-22	TIME			3\n"
					+ "5	End of span			V	1-22	TIME			3\n"
					+ "6	Sequence no. of time span hdr.	D	6	\"000000\"		3\n";

		// ABBREVIATION DICTIONARY CONTROL HEADER BLOCKETTES

		case 30:
			return
			// Type Name Category Number Fields
			"030	Data Format Dictionary Blockette	Abbreviation Dictionary		2.0=7\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 030		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Short descriptive name		V	1-50	[UNLPS]			0\n"
					+ "4	Data format identifier code	D	4	\"0000\"		0\n"
					+ "5	Data family type		D	3	\"000\"			0\n"
					+ "6	Number of decoder keys		D	2	\"00\"			0\n" +
					// REPEAT field 7 for the Number of decoder keys:
					"7	Decoder keys			V	1-9999	[UNLPS]			6\n";
		case 31:
			return
			// Type Name Category Number Fields
			"031	Comment Description Blockette		Abbreviation Dictionary		2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 031		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Comment code key		D	4	\"0000\"		0\n"
					+ "4	Comment class code		A	1	[U]			0\n"
					+ "5	Description of comment		V	1-70	[UNLPS]			0\n"
					+ "6	Units of comment level		D	3	\"000\"			0\n";
		case 32:
			return
			// Type Name Category Number Fields
			"032	Cited Source Dictionary Blockette	Abbreviation Dictionary		2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 032		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Source lookup code		D	2	\"00\"			0\n"
					+ "4	Name of publication/author	V	1-70	[UNLPS]			0\n"
					+ "5	Date published/catalog		V	1-70	[UNLPS]			0\n"
					+ "6	Publisher name			V	1-50	[UNLPS]			0\n";
		case 33:
			return
			// Type Name Category Number Fields
			"033	Generic Abbreviation Blockette		Abbreviation Dictionary		2.0=4\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 033		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Abbreviation lookup code	D	3	\"000\"			0\n"
					+ "4	Abbreviation description	V	1-50	[UNLPS]			0\n";
		case 34:
			return
			// Type Name Category Number Fields
			"034	Units Abbreviations Blockette		Abbreviation Dictionary		2.0=5\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 034		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Unit lookup code		D	3	\"000\"			0\n"
					+ "4	Unit name			V	1-20	[UNP]			0\n"
					+ "5	Unit description		V	0-50	[UNLPS]			0\n";
		case 35:
			return
			// Type Name Category Number Fields
			"035	Beam Configuration Blockette		Abbreviation Dictionary		2.0=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 035		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Unit lookup code		D	3	\"000\"			0\n"
					+ "4	Number of components		D	4	\"0000\"		0\n" +
					// REPEAT fields 5-9 for the Number of components
					"5	Station identifier		A	5	[UN]			4\n"
					+ "6	Location identifier		A	2	[UNS]			4\n"
					+ "7	Channel identifier		A	3	[UN]			4\n"
					+ "8	Sub-channel identifier		D	4	\"0000\"		4\n"
					+ "9	Component weight		D	5	\"0.000\"		4\n";
		case 41:
			return
			// Type Name Category Number Fields
			"041	FIR Dictionary Blockette		Abbreviation Dictionary		2.2=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 041		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response Lookup Key		D	4	\"0000\"		0\n"
					+ "4	Response Name			V	1-25	[UNL_]			0\n"
					+ "5	Symmetry Code			A	1	[U]			0\n"
					+ "6	Signal In Units			D	3	\"000\"			0\n"
					+ "7	Signal Out Units		D	3	\"000\"			0\n"
					+ "8	Number of Factors		D	4	\"0000\"		0\n" +
					// REPEAT field 9 for Number of Coefficients
					"9	FIR Coefficient			F	14	\"0.0000000E00\"	8\n";
		case 42:
			return
			// Type Name Category Number Fields
			"042	Response (Polynomial) Dictionary Blockette	Abbreviation Dictionary	2.3=17\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 042		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response Lookup Key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Transfer Function Type		A	1	[U]			0\n"
					+ "6	Stage signal input units	D	3	\"000\"			0\n"
					+ "7	Stage signal output units	D	3	\"000\"			0\n"
					+ "8	Polynomial Approximation Type	A	1	[U]			0\n"
					+ "9	Valid Frequency Units   	A	1	[U]			0\n"
					+ "10	Lower Valid Frequency Bound		F	12	\"0.00000E00\"		0\n"
					+ "11	Upper Valid Frequency Bound		F	12	\"0.00000E00\"		0\n"
					+ "12	Lower Bound of Approximation	F	12	\"0.00000E00\"		0\n"
					+ "13	Upper Bound of Approximation	F	12	\"0.00000E00\"		0\n"
					+ "14	Maximum Absolute Error		F	12	\"0.00000E00\"		0\n"
					+ "15	Number of Polynomial Coeff.	D	3	\"000\"			0\n" +
					// REPEAT fields 16-17 for each polynomial coefficient:
					"16	Polynomial Coefficient		F	12	\"0.00000E00\"		15\n"
					+ "17	Polynomial Coefficient Error	F	12	\"0.00000E00\"		15\n";
		case 43:
			return
			// Type Name Category Number Fields
			"043	Response (Poles & Zeros) Dictionary Blockette	Abbreviation Dictionary	2.1=19\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 043		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response Lookup Key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Response type			A	1	[U]			0\n"
					+ "6	Stage signal input units	D	3	\"000\"			0\n"
					+ "7	Stage signal output units	D	3	\"000\"			0\n"
					+ "8	AO normalization factor		F	12	\"0.00000E00\"		0\n"
					+ "9	Normalization frequency (Hz)	F	12	\"0.00000E00\"		0\n"
					+ "10	Number of complex zeros		D	3	\"000\"			0\n" +
					// REPEAT fields 11-14 for the Number of complex zeros:
					"11	Real zero			F	12	\"0.00000E00\"		10\n"
					+ "12	Imaginary zero			F	12	\"0.00000E00\"		10\n"
					+ "13	Real zero error			F	12	\"0.00000E00\"		10\n"
					+ "14	Imaginary zero error		F	12	\"0.00000E00\"		10\n"
					+ "15	Number of complex poles		D	3	\"000\"			0\n" +
					// REPEAT fields 16-19 for the Number of complex poles:
					"16	Real pole			F	12	\"0.00000E00\"		15\n"
					+ "17	Imaginary pole			F	12	\"0.00000E00\"		15\n"
					+ "18	Real pole error			F	12	\"0.00000E00\"		15\n"
					+ "19	Imaginary pole error		F	12	\"0.00000E00\"		15\n";
		case 44:
			return
			// Type Name Category Number Fields
			"044	Response (Coefficients) Dictionary Blockette	Abbreviation Dictionary	2.1=13\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 044		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response lookup key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UN_]			0\n"
					+ "5	Response type			A	1	[U]			0\n"
					+ "6	Signal input units		D	3	\"000\"			0\n"
					+ "7	Signal output units		D	3	\"000\"			0\n"
					+ "8	Number of numerators		D	4	\"0000\"		0\n" +
					// REPEAT fields 9-10 for the Number of numerators:
					"9	Numerator coefficient		F	12	\"0.00000E00\"		8\n"
					+ "10	Numerator error			F	12	\"0.00000E00\"		8\n"
					+ "11	Number of denominators		D	4	\"0000\"		0\n" +
					// REPEAT fields 12-13 for the Number of denominators:
					"12	Denominator coefficient		F	12	\"0.00000E00\"		11\n"
					+ "13	Denominator error		F	12	\"0.00000E00\"		11\n";
		case 45:
			return
			// Type Name Category Number Fields
			"045	Response List Dictionary Blockette	Abbreviation Dictionary		2.1=12\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 045		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response lookup key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Signal input units		D	3	\"000\"			0\n"
					+ "6	Signal output units		D	3	\"000\"			0\n"
					+ "7	Number of responses listed	D	4	\"0000\"		0\n" +
					// REPEAT fields 8-12 for the Number of responses listed:
					"8	Frequency (Hz)			F	12	\"0.00000E00\"		7\n"
					+ "9	Amplitude			F	12	\"0.00000E00\"		7\n"
					+ "10	Amplitude error			F	12	\"0.00000E00\"		7\n"
					+ "11	Phase angle (degrees)		F	12	\"0.00000E00\"		7\n"
					+ "12	Phase error (degrees)		F	12	\"0.00000E00\"		7\n";
		case 46:
			return
			// Type Name Category Number Fields
			"046	Generic Response Dictionary Blockette	Abbreviation Dictionary		2.1=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 046		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response lookup key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Signal input units		D	3	\"000\"			0\n"
					+ "6	Signal output units		D	3	\"000\"			0\n"
					+ "7	Number of corners listed	D	4	\"0000\"		0\n" +
					// REPEAT fields 8-9 for the Number of corners listed:
					"8	Corner frequency (Hz)		F	12	\"0.00000E00\"		7\n"
					+ "9	Corner slope (db/decade)	F	12	\"0.00000E00\"		7\n";
		case 47:
			return
			// Type Name Category Number Fields
			"047	Decimation Dictionary Blockette		Abbreviation Dictionary		2.1=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 047		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response lookup key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Input sample rate		F	10	\"0.0000E00\"		0\n"
					+ "6	Decimation factor		D	5	\"00000\"		0\n"
					+ "7	Decimation offset		D	5	\"00000\"		0\n"
					+ "8	Estimated delay (seconds)	F	11	\"0.0000E00\"		0\n"
					+ "9	Correction applied (seconds)	F	11	\"0.0000E00\"		0\n";
		case 48:
			return
			// Type Name Category Number Fields
			"048	Channel Sensitivity/Gain Dictionary Blockette	Abbreviation Dictionary	2.1=10\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 048		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response lookup key		D	4	\"0000\"		0\n"
					+ "4	Response name			V	1-25	[UNL_]			0\n"
					+ "5	Sensitivity/gain		F	12	\"0.00000E00\"		0\n"
					+ "6	Frequency (Hz)			F	12	\"0.00000E00\"		0\n"
					+ "7	Number of history values	D	2	\"00\"			0\n" +
					// REPEAT fields 8-10 for the Number of history values:
					"8	Sensitivity for calibration	F	12	\"0.00000E00\"		7\n"
					+ "9	Freq. of calib. sensitivity	F	12	\"0.00000E00\"		7\n"
					+ "10	Time of above calibration	V	1-22	TIME			7\n";

		// STATION CONTROL HEADER BLOCKETTES

		case 50:
			return
			// Type Name Category Number Fields
			"050	Station Identifier Blockette		Station		2.0=15,2.3=16\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 050		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Station call letters		A	5	[UN]			0\n"
					+ "4	Latitude (degrees)		D	10	\"00.000000\"		0\n"
					+ "5	Longitude (degrees)		D	11	\"000.000000\"		0\n"
					+ "6	Elevation (m)			D	7	\"0000.0\"		0\n"
					+ "7	Number of channels		D	4	\"0000\"		0\n"
					+ "8	Number of station comments	D	3	\"000\"			0\n"
					+ "9	Site name			V	1-60	[UNLPS]			0\n"
					+ "10	Network identifier code		D	3	\"000\"			0\n"
					+ "11	32 bit word order		D	4	\"0000\"		0\n"
					+ "12	16 bit word order		D	2	\"00\"			0\n"
					+ "13	Start effective date		V	1-22	TIME			0\n"
					+ "14	End effective date		V	0-22	TIME			0\n"
					+ "15	Update flag			A	1	[]			0\n"
					+ "16	Network Code			A	2	[UN]			0\n";
		case 51:
			return
			// Type Name Category Number Fields
			"051	Station Comment Blockette		Station		2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 051		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Beginning effective time	V	1-22	TIME			0\n"
					+ "4	End effective time		V	1-22	TIME			0\n"
					+ "5	Comment code key		D	4	\"0000\"		0\n"
					+ "6	Comment level			D	6	\"000000\"		0\n";
		case 52:
			return
			// Type Name Category Number Fields
			"052	Channel Identifier Blockette		Station		2.0=24\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 052		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Location identifier		A	2	[UNS]			0\n"
					+ "4	Channel identifier		A	3	[UN]			0\n"
					+ "5	Subchannel identifier		D	4	\"0000\"		0\n"
					+ "6	Instrument identifier		D	3	\"000\"			0\n"
					+ "7	Optional comment		V	0-30	[UNLPS]			0\n"
					+ "8	Units of signal response	D	3	\"000\"			0\n"
					+ "9	Units of calibration input	D	3	\"000\"			0\n"
					+ "10	Latitude (degrees)		D	10	\"00.000000\"		0\n"
					+ "11	Longitude (degrees)		D	11	\"000.000000\"		0\n"
					+ "12	Elevation (m)			D	7	\"0000.0\"		0\n"
					+ "13	Local depth (m)			D	5	\"000.0\"		0\n"
					+ "14	Azimuth (degrees)		D	5	\"000.0\"		0\n"
					+ "15	Dip (degrees)			D	5	\"00.0\"		0\n"
					+ "16	Data format identifier code	D	4	\"0000\"		0\n"
					+ "17	Data record length		D	2	\"00\"			0\n"
					+ "18	Sample rate (Hz)		F	10	\"0.0000E00\"		0\n"
					+ "19	Max clock drift (seconds)	F	10	\"0.0000E00\"		0\n"
					+ "20	Number of comments		D	4	\"0000\"		0\n"
					+ "21	Channel flags			V	0-26	[U]			0\n"
					+ "22	Start date			V	1-22	TIME			0\n"
					+ "23	End date			V	0-22	TIME			0\n"
					+ "24	Update flag			A	1	[]			0\n";
		case 53:
			return
			// Type Name Category Number Fields
			"053	Response (Poles & Zeros) Blockette	Station		2.0=18\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 053		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Transfer function type		A	1	[U]			0\n"
					+ "4	Stage sequence number		D	2	\"00\"			0\n"
					+ "5	Stage signal input units	D	3	\"000\"			0\n"
					+ "6	Stage signal output units	D	3	\"000\"			0\n"
					+ "7	AO normalization factor		F	12	\"0.00000E00\"		0\n"
					+ "8	Normalization freq. f(n) (Hz)	F	12	\"0.00000E00\"		0\n"
					+ "9	Number of complex zeros		D	3	\"000\"			0\n" +
					// REPEAT fields 10-13 for the Number of complex zeros:
					"10	Real zero			F	12	\"0.00000E00\"		9\n"
					+ "11	Imaginary zero			F	12	\"0.00000E00\"		9\n"
					+ "12	Real zero error			F	12	\"0.00000E00\"		9\n"
					+ "13	Imaginary zero error		F	12	\"0.00000E00\"		9\n"
					+ "14	Number of complex poles		D	3	\"000\"			0\n" +
					// REPEAT fields 15-18 for the Number of complex poles:
					"15	Real pole			F	12	\"0.00000E00\"		14\n"
					+ "16	Imaginary pole			F	12	\"0.00000E00\"		14\n"
					+ "17	Real pole error			F	12	\"0.00000E00\"		14\n"
					+ "18	Imaginary pole error		F	12	\"0.00000E00\"		14\n";
		case 54:
			return
			// Type Name Category Number Fields
			"054	Response (Coefficients) Blockette	Station		2.0=12\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 054		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Response type			A	1	[U]			0\n"
					+ "4	Stage sequence number		D	2	\"00\"			0\n"
					+ "5	Signal input units		D	3	\"000\"			0\n"
					+ "6	Signal output units		D	3	\"000\"			0\n"
					+ "7	Number of numerators		D	4	\"0000\"		0\n" +
					// REPEAT fields 8-9 for the Number of numerators:
					"8	Numerator coefficient		F	12	\"0.00000E00\"		7\n"
					+ "9	Numerator error			F	12	\"0.00000E00\"		7\n"
					+ "10	Number of denominators		D	4	\"0000\"		0\n" +
					// REPEAT fields 11-12 for the Number of denominators:
					"11	Denominator coefficient		F	12	\"0.00000E00\"		10\n"
					+ "12	Denominator error		F	12	\"0.00000E00\"		10\n";
		case 55:
			return
			// Type Name Category Number Fields
			"055	Response List Blockette			Station		2.0=11\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 055		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Stage sequence number		D	2	\"00\"			0\n"
					+ "4	Signal input units		D	3	\"000\"			0\n"
					+ "5	Signal output units		D	3	\"000\"			0\n"
					+ "6	Number of responses listed	D	4	\"0000\"		0\n" +
					// REPEAT fields 7-11 for the Number of responses listed:
					"7	Frequency (Hz)			F	12	\"0.00000E00\"		6\n"
					+ "8	Amplitude			F	12	\"0.00000E00\"		6\n"
					+ "9	Amplitude error			F	12	\"0.00000E00\"		6\n"
					+ "10	Phase angle (degrees)		F	12	\"0.00000E00\"		6\n"
					+ "11	Phase error (degrees)		F	12	\"0.00000E00\"		6\n";
		case 56:
			return
			// Type Name Category Number Fields
			"056	Generic Response Blockette		Station		2.0=8\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 056		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Stage sequence number		D	2	\"00\"			0\n"
					+ "4	Signal input units		D	3	\"000\"			0\n"
					+ "5	Signal output units		D	3	\"000\"			0\n"
					+ "6	Number of corners listed	D	4	\"0000\"		0\n" +
					// REPEAT fields 7-8 for the Number of responses listed:
					"7	Corner frequency (Hz)		F	12	\"0.00000E00\"		6\n"
					+ "8	Corner slope (db/decade)	F	12	\"0.00000E00\"		6\n";
		case 57:
			return
			// Type Name Category Number Fields
			"057	Decimation Blockette			Station		2.1=8\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 057		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Stage sequence number		D	2	\"00\"			0\n"
					+ "4	Input sample rate (Hz)		F	10	\"0.0000E00\"		0\n"
					+ "5	Decimation factor		D	5	\"00000\"		0\n"
					+ "6	Decimation offset		D	5	\"00000\"		0\n"
					+ "7	Estimated delay (seconds)	F	11	\"0.0000E00\"		0\n"
					+ "8	Correction applied (seconds)	F	11	\"0.0000E00\"		0\n";
		case 58:
			return
			// Type Name Category Number Fields
			"058	Channel Sensitivity/Gain Blockette	Station		2.0=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 058		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Stage sequence number		D	2	\"00\"			0\n"
					+ "4	Sensitivity/gain S(d)		F	12	\"0.00000E00\"		0\n"
					+ "5	Frequency (Hz) f(s)		F	12	\"0.00000E00\"		0\n"
					+ "6	Number of history values	D	2	\"00\"			0\n" +
					// REPEAT fields 7-9 for the Number of history values:
					"7	Sensitivity for calibration	F	12	\"0.00000E00\"		6\n"
					+ "8	Frequency of calibration (Hz)	F	12	\"0.00000E00\"		6\n"
					+ "9	Time of above calibration	V	1-22	TIME			6\n";
		case 59:
			return
			// Type Name Category Number Fields
			"059	Channel Comment Blockette		Station		2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 059		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Beginning effective time	V	1-22	TIME			0\n"
					+ "4	End effective time		V	0-22	TIME			0\n"
					+ "5	Comment code key		D	4	\"0000\"		0\n"
					+ "6	Comment level			D	6	\"000000\"		0\n";
		case 60:
			return
			// Type Name Category Number Fields
			"060	Response Reference Blockette		Station		2.1=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 060		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Number of stages		D	2	\"00\"			0\n" +
					// REPEAT field 4-5, for Number of stages:
					"4	Stage sequence number		D	2	\"00\"			3\n"
					+ "5	Number of responses		D	2	\"00\"			3\n" +
					// REPEAT field 6, for Number of responses (within each
					// stage):
					// note special type flag and mask, special to Blockette 60:
					// L = List
			"6	Response lookup key		L	4	/0000/			5\n";
		case 61:
			return
			// Type Name Category Number Fields
			"061	FIR Response Blockette			Station		2.2=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 061		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Stage sequence number		D	2	\"00\"			0\n"
					+ "4	Response Name			V	1-25	[ULN_]			0\n"
					+ "5	Symmetry Code			A	1	[U]			0\n"
					+ "6	Signal In Units			D	3	\"000\"			0\n"
					+ "7	Signal Out Units		D	3	\"000\"			0\n"
					+ "8	Number of Coefficients		D	4	\"0000\"		0\n" +
					// REPEAT field 9 for the Number of Coefficients
					"9	FIR Coefficient			F	14	\"0.0000000E00\"	8\n";
		case 62:
			return
			// Type Name Category Number Fields
			"062	Response Polynomial Blockette		Station		2.3=16\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 062		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"			0\n"
					+ "3	Transfer Function Type	A	1	[U]				0\n"
					+ "4	Stage Sequence Number		D	2	\"00\"			0\n"
					+ "5	Stage Signal Input Units	D	3	\"000\"			0\n"
					+ "6	Stage Signal Output Units	D	3	\"000\"			0\n"
					+ "7	Polynomial Approx'n Type	A	1	[U]				0\n"
					+ "8	Valid Frequency Units		A	1	[U]				0\n"
					+ "9	Lower Valid Freq Bound	F	12	\"0.00000E00\"	0\n"
					+ "10	Upper Valid Freq Bound	F	12	\"0.00000E00\"	0\n"
					+ "11	Lower Bound of Approx'n	F	12	\"0.00000E00\"	0\n"
					+ "12	Upper Bound of Approx'n	F	12	\"0.00000E00\"	0\n"
					+ "13	Maximum Absolute Error	F	12	\"0.00000E00\"	0\n"
					+ "14	Number of Polynomial Coeff	D	3	\"000\"		0\n" +
					// REPEAT fields 15 and 16 for the Number of Polynomial
					// Coefficients
					"15	Polynomial Coefficient	F	12	\"0.00000E00\"	14\n"
					+ "16	Polynomial Coeff Error	F	12	\"0.00000E00\"	14\n";

		// TIME SPAN CONTROL HEADER BLOCKETTES

		case 70:
			return
			// Type Name Category Number Fields
			"070	Time Span Identifier Blockette		Time Span	2.0=5\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 070		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Time span flag			A	1	[U]			0\n"
					+ "4	Beginning time of data span	V	1-22	TIME			0\n"
					+ "5	End time of data span		V	1-22	TIME			0\n";
		case 71:
			return
			// Type Name Category Number Fields
			"071	Hypocenter Information Blockette	Time Span	2.0=11,2.3=14\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 071		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Origin time of event		V	1-22	TIME			0\n"
					+ "4	Hypocenter source identifier	D	2	\"00\"			0\n"
					+ "5	Latitude of event (degrees)	D	10	\"00.000000\"		0\n"
					+ "6	Longitude of event (degrees)	D	11	\"000.000000\"		0\n"
					+ "7	Depth (Km)			D	7	\"0000.00\"		0\n"
					+ "8	Number of magnitudes		D	2	\"00\"			0\n" +
					// REPEAT fields 9-11 for the Number of magnitudes:
					"9	Magnitude			D	5	\"00.00\"		8\n"
					+ "10	Magnitude type			V	1-10	[UNLPS]			8\n"
					+ "11	Magnitude source		D	2	\"00\"			8\n"
					+ "12	Seismic region			D	3	\"000\"			0\n"
					+ "13	Seismic Location		D	4	\"0000\"		0\n"
					+ "14	Region Name			V	1-40	[UNLPS]			0\n";
		case 72:
			return
			// Type Name Category Number Fields
			"072	Event Phases Blockette			Time Span	2.0=10,2.3=12\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 072		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Station identifier		A	5	[UN]			0\n"
					+ "4	Location identifier		A	2	[UNS]			0\n"
					+ "5	Channel identifier		A	3	[UN]			0\n"
					+ "6	Arrival time of phase		V	1-22	TIME			0\n"
					+ "7	Amplitude of signal		F	10	\"0.0000E00\"		0\n"
					+ "8	Period of signal (seconds)	F	10	\"0.0000E00\"		0\n"
					+ "9	Signal-to-noise ratio		F	10	\"0.0000E00\"		0\n"
					+ "10	Name of phase			V	1-20	[UNLP]			0\n"
					+ "11	Source				D	2	\"00\"			0\n"
					+ "12	Network Code			A	2	[UN]			0\n";
		case 73:
			return
			// Type Name Category Number Fields
			"073	Time Span Data Start Index Blockette	Time Span	2.0=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 073		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Number of data pieces		D	4	\"0000\"		0\n" +
					// REPEAT fields 4-9 for the Number of data pieces:
					"4	Station identifier		A	5	[UN]			3\n"
					+ "5	Location identifier		A	2	[UNS]			3\n"
					+ "6	Channel identifier		A	3	[UN]			3\n"
					+ "7	Time of record			V	1-22	TIME			3\n"
					+ "8	Sequence number of first record	D	6	\"000000\"		3\n"
					+ "9	Sub-sequence number		D	2	\"00\"			3\n";
		case 74:
			return
			// Type Name Category Number Fields
			"074	Time Series Index Blockette		Time Span	2.1=15,2.3=16\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 074		D	3	\"000\"			0\n"
					+ "2	Length of blockette		D	4	\"0000\"		0\n"
					+ "3	Station identifier		A	5	[UN]			0\n"
					+ "4	Location identifier		A	2	[UNS]			0\n"
					+ "5	Channel identifier		A	3	[UN]			0\n"
					+ "6	Series start time		V	1-22	TIME			0\n"
					+ "7	Sequence number of first data	D	6	\"000000\"		0\n"
					+ "8	Sub-sequence number		D	2	\"00\"			0\n"
					+ "9	Series end time			V	1-22	TIME			0\n"
					+ "10	Sequence number of last record	D	6	\"000000\"		0\n"
					+ "11	Sub-sequence number		D	2	\"00\"			0\n"
					+ "12	Number of accelerator repeats	D	3	\"000\"			0\n" +
					// REPEAT fields 13-15 for the Number of accelerator
					// repeats:
					"13	Record start time		V	1-22	TIME			12\n"
					+ "14	Sequence number of record	D	6	\"000000\"		12\n"
					+ "15	Sub-sequence number		D	2	\"00\"			12\n"
					+ "16	Network Code			A	2	[UN]			0\n";

		// DATA RECORD BLOCKETTES

		case 999: // Fixed Section Data Header -- assigned special number
					// internal to application
			// FSDH has special structure assigned here to make it fit with
			// Blockette model.
			// The first three fields are non-standard to the specification to
			// make it more
			// Blockette-like. Length is 60 bytes.
			return
			// Type Name Category Number Fields
			"999	Fixed Section of Data Header	Data Record	2.0=18\n" +
					// Fld Name Type Length Mask or Flags Repeat
					// NONSTANDARD - FSDH is given a numeric designation of 999
			"1	Blockette type - 999		D	2	UWORD			0\n" +
					// NONSTANDARD - letters indicating the level of quality
					// control of the data, which
					// is extracted from field 2 and possibly field 3 of the
					// FSDH, currently. Each letter
					// should have a unique meaning. Current proposed letters
					// are:
					// field 2:
					// D = default data (might be QC, might be Real Time
					// un-QC'd)
					// R = real time data (un-QC'd)
					// Q = quality controlled data (QC'd)
					// M = merged data
					// field 3:
					// P = primary data source
					// S = secondary data source
					//
					// space characters may be used for any leftover space to
					// the right of the flags
					// (left-justified)
					//
			"2	Data Quality Flags		A	8	[UNS]			0\n" +
					// NONSTANDARD - time stamp to indicate approximately when
					// the data record was
					// synthesized, NOT the time of object creation, NOT the
					// start time of the data
					// in this record. This helps distinguish data records that
					// cover the same time span
					// but are generated at different times, possibly reflecting
					// resubmitted, corrected data.
			"3	Data Arrival Time Stamp		B	10	BTIME			0\n" +
					// BEGIN standard FSDH fields here
					"4	Station identifier code		A	5	[UN]			0\n"
					+ "5	Location identifier		A	2	[UNS]			0\n"
					+ "6	Channel identifier		A	3	[UN]			0\n"
					+ "7	Network Code			A	2	[]			0\n"
					+ "8	Record start time		B	10	BTIME			0\n"
					+ "9	Number of samples		B	2	UWORD			0\n"
					+ "10	Sample rate factor		B	2	WORD			0\n"
					+ "11	Sample rate multiplier		B	2	WORD			0\n"
					+ "12	Activity flags			B	1	UBYTE			0\n"
					+ "13	I/O and clock flags		B	1	UBYTE			0\n"
					+ "14	Data quality flags		B	1	UBYTE			0\n"
					+ "15	No. of blockettes that follow	B	1	UBYTE			0\n"
					+ "16	Time correction			B	4	LONG			0\n"
					+ "17	Beginning of data		B	2	UWORD			0\n"
					+ "18	First blockette			B	2	UWORD			0\n";
		case 100:
			return
			// Type Name Category Number Fields
			"100	Sample Rate Blockette			Data Record	2.3=5\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 100		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Actual Sample Rate		B	4	FLOAT			0\n"
					+ "4	Flags (to be defined)		B	1	BYTE			0\n"
					+ "5	Reserved byte			B	3	UBYTE			0\n";
		case 200:
			return
			// Type Name Category Number Fields
			"200	Generic Event Detection Blockette	Data Record	2.0=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 200		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Signal amplitude		B	4	FLOAT			0\n"
					+ "4	Signal period			B	4	FLOAT			0\n"
					+ "5	Background estimate		B	4	FLOAT			0\n"
					+ "6	Event detection flags		B	1	UBYTE			0\n"
					+ "7	Reserved byte			B	1	UBYTE			0\n"
					+ "8	Signal onset time		B	10	BTIME			0\n"
					+ "9	Detector Name			A	24	[]			0\n";
		case 201:
			return
			// Type Name Category Number Fields
			"201	Murdock Event Detection Blockette	Data Record	2.0=12\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 201		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Signal amplitude		B	4	FLOAT			0\n"
					+ "4	Signal period			B	4	FLOAT			0\n"
					+ "5	Background estimate		B	4	FLOAT			0\n"
					+ "6	Event detection flags		B	1	UBYTE			0\n"
					+ "7	Reserved byte			B	1	UBYTE			0\n"
					+ "8	Signal onset time		B	10	BTIME			0\n"
					+ "9	Signal-to-noise ratio values	B	6	UBYTE			0\n"
					+ "10	Lookback value			B	1	UBYTE			0\n"
					+ "11	Pick algorithm			B	1	UBYTE			0\n"
					+ "12	Detector name			A	24	[]			0\n";
		case 300:
			return
			// Type Name Category Number Fields
			"300	Step Calibration Blockette		Data Record	2.0=13\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 300		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Beginning of calibration time	B	10	BTIME			0\n"
					+ "4	Number of step calibrations	B	1	UBYTE			0\n"
					+ "5	Calibration flags		B	1	UBYTE			0\n"
					+ "6	Step duration			B	4	ULONG			0\n"
					+ "7	Interval duration		B	4	ULONG			0\n"
					+ "8	Calibration signal amplitude	B	4	FLOAT			0\n"
					+ "9	Channel with calibration input	A	3	[]			0\n"
					+ "10	Reserved byte			B	1	UBYTE			0\n"
					+ "11	Reference amplitude		B	4	ULONG			0\n"
					+ "12	Coupling			A	12	[]			0\n"
					+ "13	Rolloff				A	12	[]			0\n";
		case 310:
			return
			// Type Name Category Number Fields
			"310	Sine Calibration Blockette		Data Record	2.0=13\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 310		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Beginning of calibration time	B	10	BTIME			0\n"
					+ "4	Reserved byte			B	1	UBYTE			0\n"
					+ "5	Calibration flags		B	1	UBYTE			0\n"
					+ "6	Calibration duration		B	4	ULONG			0\n"
					+ "7	Period of signal (seconds)	B	4	FLOAT			0\n"
					+ "8	Amplitude of signal		B	4	FLOAT			0\n"
					+ "9	Channel with calibration input	A	3	[]			0\n"
					+ "10	Reserved byte			B	1	UBYTE			0\n"
					+ "11	Reference amplitude		B	4	ULONG			0\n"
					+ "12	Coupling			A	12	[]			0\n"
					+ "13	Rolloff				A	12	[]			0\n";
		case 320:
			return
			// Type Name Category Number Fields
			"320	Pseudo-random Calibration Blockette	Data Record	2.0=13\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 320		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Beginning of calibration time	B	10	BTIME			0\n"
					+ "4	Reserved byte			B	1	UBYTE			0\n"
					+ "5	Calibration flags		B	1	UBYTE			0\n"
					+ "6	Calibration duration		B	4	ULONG			0\n"
					+ "7	Peak-to-peak amplitude of steps	B	4	FLOAT			0\n"
					+ "8	Channel with calibration output	A	3	[]			0\n"
					+ "9	Reserved byte			B	1	UBYTE			0\n"
					+ "10	Reference amplitude		B	4	ULONG			0\n"
					+ "11	Coupling			A	12	[]			0\n"
					+ "12	Rolloff				A	12	[]			0\n"
					+ "13	Noise type			A	8	[]			0\n";
		case 390:
			return
			// Type Name Category Number Fields
			"390	Generic Calibration Blockette		Data Record	2.0=9\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 390		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Beginning of calibration time	B	10	BTIME			0\n"
					+ "4	Reserved byte			B	1	UBYTE			0\n"
					+ "5	Calibration flags		B	1	UBYTE			0\n"
					+ "6	Calibration duration		B	4	ULONG			0\n"
					+ "7	Calibration signal amplitude	B	4	FLOAT			0\n"
					+ "8	Channel with calibration input	A	3	[]			0\n"
					+ "9	Reserved byte			B	1	UBYTE			0\n";
		case 395:
			return
			// Type Name Category Number Fields
			"395	Calibration Abort Blockette		Data Record	2.0=4\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 395		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	End of calibration time		B	10	BTIME			0\n"
					+ "4	Reserved bytes			B	2	UBYTE			0\n";
		case 400:
			return
			// Type Name Category Number Fields
			"400	Beam Blockette				Data Record	2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 400		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Beam azimuth (degrees)		B	4	FLOAT			0\n"
					+ "4	Beam slowness (sec/degree)	B	4	FLOAT			0\n"
					+ "5	Beam configuration		B	2	UWORD			0\n"
					+ "6	Reserved bytes			B	2	UWORD			0\n";
		case 405:
			return
			// Type Name Category Number Fields
			"405	Beam Delay Blockette			Data Record	2.0=3\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 405		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Array of delay values		B	2	UWORD			0\n";
		case 500:
			return
			// Type Name Category Number Fields
			"500	Timing Blockette			Data Record	2.0=10\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 500		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	VCO correction			B	4	FLOAT			0\n"
					+ "4	Time of exception		B	10	BTIME			0\n"
					+ "5	Microseconds			B	1	UBYTE			0\n"
					+ "6	Reception Quality		B	1	UBYTE			0\n"
					+ "7	Exception count			B	4	ULONG			0\n"
					+ "8	Exception type			A	16	[]			0\n"
					+ "9	Clock model			A	32	[]			0\n"
					+ "10	Clock status			A	128	[]			0\n";
		case 1000:
			return
			// Type Name Category Number Fields
			"1000	Data Only SEED Blockette		Data Record	2.3=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 1000		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Encoding format			B	1	BYTE			0\n"
					+ "4	Word order			B	1	UBYTE			0\n"
					+ "5	Data Record Length		B	1	UBYTE			0\n"
					+ "6	Reserved			B	1	UBYTE			0\n";
		case 1001:
			return
			// Type Name Category Number Fields
			"1001	Data Extension Blockette		Data Record	2.0=6\n" +
					// Fld Name Type Length Mask or Flags Repeat
					"1	Blockette type - 1001		B	2	UWORD			0\n"
					+ "2	Next blockette's byte number	B	2	UWORD			0\n"
					+ "3	Timing quality			B	1	UBYTE			0\n"
					+ "4	Microseconds			B	1	UBYTE			0\n"
					+ "5	Reserved			B	1	UBYTE			0\n"
					+ "6	Frame Count			B	1	UBYTE			0\n";

		// UNKNOWN BLOCKETTE

		default:
			throw new SeedException("Blockette type " + blkType + " not defined");
		}
	}
}
