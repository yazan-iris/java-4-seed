package edu.iris.dmc.seed.control.station;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.dictionary.B034;

public class B055Test {
	
	@Test
	public void longBlockette() throws Exception {
		String text = "0554219010010030070+1.00000E-02+7.00000E-03+0.00000E+00"
				+ "+1.48000E+02+0.00000E+00+1.20000E-02+9.00000E-03+0.00000E+00"
				+ "+1.43000E+02+0.00000E+00+1.30000E-02+1.00000E-02+0.00000E+00"
				+ "+1.41000E+02+0.00000E+00+1.50000E-02+1.18000E-02+0.00000E+00"
				+ "+1.38000E+02+0.00000E+00+1.60000E-02+1.30648E-02+0.00000E+00"
				+ "+1.37000E+02+0.00000E+00+1.80000E-02+1.51920E-02+0.00000E+00"
				+ "+1.32000E+02+0.00000E+00+2.10000E-02+1.87883E-02+0.00000E+00"
				+ "+1.27000E+02+0.00000E+00+2.30000E-02+2.05776E-02+0.00000E+00"
				+ "+1.24000E+02+0.00000E+00+2.60000E-02+2.39226E-02+0.00000E+00"
				+ "+1.20000E+02+0.00000E+00+2.90000E-02+2.66829E-02+0.00000E+00"
				+ "+1.18000E+02+0.00000E+00+2.56000E-02+2.36883E-02+0.00000E+00"
				+ "+1.18000E+02+0.00000E+00+2.63000E-02+2.44603E-02+0.00000E+00"
				+ "+1.18000E+02+0.00000E+00+2.70000E-02+2.52307E-02+0.00000E+00"
				+ "+1.17000E+02+0.00000E+00+2.78000E-02+2.61095E-02+0.00000E+00"
				+ "+1.16000E+02+0.00000E+00+2.86000E-02+2.69864E-02+0.00000E+00"
				+ "+1.15000E+02+0.00000E+00+2.94000E-02+2.78615E-02+0.00000E+00"
				+ "+1.15000E+02+0.00000E+00+3.03000E-02+2.88439E-02+0.00000E+00"
				+ "+1.14000E+02+0.00000E+00+3.13000E-02+2.99329E-02+0.00000E+00"
				+ "+1.13000E+02+0.00000E+00+3.23000E-02+3.10194E-02+0.00000E+00"
				+ "+1.12000E+02+0.00000E+00+3.33000E-02+3.21034E-02+0.00000E+00"
				+ "+1.12000E+02+0.00000E+00+3.45000E-02+3.34013E-02+0.00000E+00"
				+ "+1.11000E+02+0.00000E+00+3.57000E-02+3.46960E-02+0.00000E+00"
				+ "+1.10000E+02+0.00000E+00+3.70000E-02+3.60954E-02+0.00000E+00"
				+ "+1.09000E+02+0.00000E+00+3.85000E-02+3.77058E-02+0.00000E+00"
				+ "+1.08000E+02+0.00000E+00+4.00000E-02+3.93123E-02+0.00000E+00"
				+ "+1.08000E+02+0.00000E+00+4.17000E-02+4.11285E-02+0.00000E+00"
				+ "+1.07000E+02+0.00000E+00+4.35000E-02+4.30468E-02+0.00000E+00"
				+ "+1.06000E+02+0.00000E+00+4.55000E-02+4.51730E-02+0.00000E+00"
				+ "+1.05000E+02+0.00000E+00+4.76000E-02+4.74002E-02+0.00000E+00"
				+ "+1.04000E+02+0.00000E+00+5.00000E-02+4.99393E-02+0.00000E+00"
				+ "+1.03000E+02+0.00000E+00+5.26000E-02+5.26834E-02+0.00000E+00"
				+ "+1.02000E+02+0.00000E+00+5.56000E-02+5.58421E-02+0.00000E+00"
				+ "+1.01000E+02+0.00000E+00+5.88000E-02+5.92035E-02+0.00000E+00"
				+ "+1.00000E+02+0.00000E+00+6.25000E-02+6.30811E-02+0.00000E+00"
				+ "+9.90000E+01+0.00000E+00+6.67000E-02+6.74730E-02+0.00000E+00"
				+ "+9.80000E+01+0.00000E+00+7.14000E-02+7.23767E-02+0.00000E+00"
				+ "+9.70000E+01+0.00000E+00+7.69000E-02+7.81024E-02+0.00000E+00"
				+ "+9.60000E+01+0.00000E+00+8.33000E-02+8.47507E-02+0.00000E+00"
				+ "+9.50000E+01+0.00000E+00+9.09000E-02+9.26280E-02+0.00000E+00"
				+ "+9.40000E+01+0.00000E+00+1.00000E-01+1.02040E-01+0.00000E+00"
				+ "+9.30000E+01+0.00000E+00+1.25990E-01+1.28817E-01+0.00000E+00"
				+ "+9.00000E+01+0.00000E+00+2.00000E-01+2.04408E-01+0.00000E+00"
				+ "+8.40000E+01+0.00000E+00+3.68400E-01+3.72969E-01+0.00000E+00"
				+ "+7.30000E+01+0.00000E+00+5.00000E-01+5.01906E-01+0.00000E+00"
				+ "+6.60000E+01+0.00000E+00+6.83990E-01+6.81432E-01+0.00000E+00"
				+ "+5.60000E+01+0.00000E+00+8.00000E-01+7.96412E-01+0.00000E+00"
				+ "+5.00000E+01+0.00000E+00+8.61770E-01+8.58583E-01+0.00000E+00"
				+ "+4.70000E+01+0.00000E+00+9.28320E-01+9.26318E-01+0.00000E+00"
				+ "+4.30000E+01+0.00000E+00+1.00000E+00+1.00000E+00+0.00000E+00"
				+ "+3.90000E+01+0.00000E+00+1.14471E+00+1.14970E+00"
				+ "+0.00000E+00+3.20000E+01+0.00000E+00+1.31037E+00+1.31854E+00"
				+ "+0.00000E+00+2.30000E+01+0.00000E+00+1.50000E+00+1.50250E+00"
				+ "+0.00000E+00+1.30000E+01+0.00000E+00+1.65096E+00+1.64109E+00"
				+ "+0.00000E+00+5.00000E+00+0.00000E+00+1.81712E+00+1.79019E+00"
				+ "+0.00000E+00-3.00000E+00+0.00000E+00+2.00000E+00+1.95864E+00"
				+ "+0.00000E+00-1.30000E+01+0.00000E+00+2.15443E+00+2.10849E+00"
				+ "+0.00000E+00-2.10000E+01+0.00000E+00+2.32079E+00+2.27412E+00"
				+ "+0.00000E+00-3.00000E+01+0.00000E+00+2.50000E+00+2.44384E+00"
				+ "+0.00000E+00-4.00000E+01+0.00000E+00+2.65665E+00+2.57058E+00"
				+ "+0.00000E+00-4.80000E+01+0.00000E+00+2.82311E+00+2.67367E+00"
				+ "+0.00000E+00-5.80000E+01+0.00000E+00+3.00000E+00+2.74925E+00"
				+ "+0.00000E+00-6.70000E+01+0.00000E+00+3.30193E+00+2.83851E+00"
				+ "+0.00000E+00-8.40000E+01+0.00000E+00+3.63424E+00+2.94123E+00"
				+ "+0.00000E+00-1.02000E+02+0.00000E+00+4.00000E+00+3.01627E+00"
				+ "+0.00000E+00-1.21000E+02+0.00000E+00+4.30887E+00+2.99242E+00"
				+ "+0.00000E+00-1.37000E+02+0.00000E+00+4.64159E+00+2.93728E+00"
				+ "+0.00000E+00-1.53000E+02+0.00000E+00+5.00000E+00+2.91776E+00"
				+ "+0.00000E+00-1.70000E+02+0.00000E+00+5.31329E+00+2.87282E+00"
				+ "+0.00000E+00+1.75000E+02+0.00000E+00+5.64622E+00+2.73243E+00"
				+ "+0.00000E+00+1.60000E+02+0.00000E+00+6.00000E+00+2.56005E+00"
				+ "+0.00000E+00+1.44000E+02+0.00000E+00";

		B055 b055 = BlocketteBuilder.build055(text.getBytes());
		System.out.println(b055.toString());
		System.out.println("+++++++++++++++++++++++++++++++++++");
		System.out.println(text);
		
		//Blockette b055 = BlocketteFactory.create(text.getBytes());
		
		assertEquals(text, b055.toSeedString());
	}
}
