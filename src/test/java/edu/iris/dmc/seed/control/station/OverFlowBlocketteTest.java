package edu.iris.dmc.seed.control.station;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;



import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class OverFlowBlocketteTest {

	@Test
	public void b061() throws Exception {
		String text = "061058404FIR_FLBELOW100_40~A0030030039+4.1895179E-13+3.3031761E-04+1.0292126E-03-3.1412280E-03+2.0570927E-04+1.5252131E-03-6.2319267E-03+1.0480133E-02-1.3120247E-02+1.0782143E-02-1.4445500E-03-1.5872946E-02+3.9507404E-02-6.5103630E-02+8.5371559E-02-8.9191342E-02+5.0061889E-02+8.3723276E-01+2.6672305E-01-1.6669311E-01+9.5283986E-02-5.0921772E-02+1.6145837E-02+7.0636240E-03-1.8387713E-02+1.9941410E-02-1.5489507E-02+8.5273541E-03-2.5578868E-03-1.8110264E-03+2.4264926E-03-3.7576946E-03+4.6729273E-04+6.3307212E-04-1.5687414E-06-1.2547978E-05+3.2104054E-07-2.6332410E-08-5.0999748E-08";
		OverFlowBlockette b061 = (OverFlowBlockette) BlocketteFactory.create(text.getBytes());
		assertFalse(b061.isOverFlown());

		List<Blockette> list = b061.split();
		assertEquals(1, list.size());
	}

	@Test
	public void overFlowB061() throws Exception {
		String text = "061058404FIR_FLBELOW100_40~A0030030039+4.1895179E-13+3.3031761E-04+1.0292126E-03-3.1412280E-03+2.0570927E-04+1.5252131E-03-6.2319267E-03+1.0480133E-02-1.3120247E-02+1.0782143E-02-1.4445500E-03-1.5872946E-02+3.9507404E-02-6.5103630E-02+8.5371559E-02-8.9191342E-02+5.0061889E-02+8.3723276E-01+2.6672305E-01-1.6669311E-01+9.5283986E-02-5.0921772E-02+1.6145837E-02+7.0636240E-03-1.8387713E-02+1.9941410E-02-1.5489507E-02+8.5273541E-03-2.5578868E-03-1.8110264E-03+2.4264926E-03-3.7576946E-03+4.6729273E-04+6.3307212E-04-1.5687414E-06-1.2547978E-05+3.2104054E-07-2.6332410E-08-5.0999748E-08";
		B061 b061 = (B061) BlocketteFactory.create(text.getBytes());
		int size = 20;
		if (b061.getName() != null) {
			size += b061.getName().getBytes().length;
		}
		// How many coef can we fit?
		int numberOfCoefThatCanFit = (9999 - size) / 14;

		for (int i = 0; i < numberOfCoefThatCanFit + 10; i++) {
			b061.addCoefficient(-5.0999748E-08);
		}

		OverFlowBlockette o = (OverFlowBlockette) b061;
		assertTrue(b061.isOverFlown());
		assertEquals(760, b061.getCoefficients().size());
		List<Blockette> list = b061.split();
		assertEquals(2, list.size());

		B061 first = (B061) list.get(0);
		assertEquals(9992,first.getSize());
		assertFalse(first.isOverFlown());
		assertEquals(711, first.getCoefficients().size());

		B061 second = (B061) list.get(1);
		assertFalse(second.isOverFlown());
		assertEquals(49, second.getCoefficients().size());

	}

	@Test
	public void overFlowB053() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) BlocketteFactory.create(text.getBytes());
		assertEquals(3,b053.getZeros().size());
		assertEquals(4,b053.getPoles().size());
		assertFalse(b053.isOverFlown());
		for (int i = 0; i < 210; i++) {
			Zero z = new Zero();
			Number n = new Number();
			n.setValue(1.0);
			z.setReal(n);
			z.setImaginary(n);
			b053.add(z);
		}
		assertTrue(b053.isOverFlown());
		List<Blockette> list = b053.split();
		assertEquals(2, list.size());
		B053 first = (B053) list.get(0);
		B053 second = (B053) list.get(1);
		assertEquals(207,first.getZeros().size());
		assertEquals(0,first.getPoles().size());
		
		assertEquals(6,second.getZeros().size());
		assertEquals(4,second.getPoles().size());
	}


	@Test
	public void overFlowB053_Zeros() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) BlocketteFactory.create(text.getBytes());
		assertEquals(3,b053.getZeros().size());
		assertEquals(4,b053.getPoles().size());
		assertFalse(b053.isOverFlown());
		for (int i = 0; i < 210; i++) {
			Zero z = new Zero();
			Number n = new Number();
			n.setValue(1.0);
			z.setReal(n);
			z.setImaginary(n);
			b053.add(z);
		}
		assertTrue(b053.isOverFlown());
		List<Blockette> list = b053.split();
		assertEquals(2, list.size());
		B053 first = (B053) list.get(0);
		B053 second = (B053) list.get(1);
		assertEquals(207,first.getZeros().size());
		assertEquals(0,first.getPoles().size());

		assertEquals(6,second.getZeros().size());
		assertEquals(4,second.getPoles().size());

	}

	// Poles are always written to the continuation blockette if zeros fill up the first blockette what does that do? 
	@Test
	public void overFlowB053_Poles() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) BlocketteFactory.create(text.getBytes());
		assertEquals(3,b053.getZeros().size());
		assertEquals(4,b053.getPoles().size());
		assertFalse(b053.isOverFlown());
		int size = 46; // Size in bytes of b053 without fields 10-13 and 15-18
		size += b053.getPoles().size()*48;
		size += b053.getZeros().size()*48;
		Number Image_Value = new Number(0.1, 0.0);
		Number Real_Value = new Number(0.1, 0.0);
		Pole p = new Pole(Real_Value,Image_Value);
		int numberOfPZThatCanFit = (9999 - size) / 48;
		for (int i = 0; i < numberOfPZThatCanFit + 20; i++) {
			b053.add(p);
		}

		OverFlowBlockette o = (OverFlowBlockette) b053;
		assertTrue(b053.isOverFlown());

		List<Blockette> list = b053.split();
		assertEquals(2, list.size());

		B053 first = (B053) list.get(0);
		assertFalse(first.isOverFlown());
		assertEquals(3, first.getZeros().size());
		assertEquals(204, first.getPoles().size());


		B053 second = (B053) list.get(1);
		assertFalse(second.isOverFlown());
		assertEquals(0, second.getZeros().size());
		assertEquals(20, second.getPoles().size());

	}
	
	
	// Poles are always written to the continuation blockette if zeros fill up the first blockette what does that do? 
	@Test
	public void overFlowB053_3blocks() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) BlocketteFactory.create(text.getBytes());
		assertEquals(3,b053.getZeros().size());
		assertEquals(4,b053.getPoles().size());
		assertFalse(b053.isOverFlown());
		int size = 46; // Size in bytes of b053 without fields 10-13 and 15-18
		size += b053.getPoles().size()*48;
		size += b053.getZeros().size()*48;
		Number Image_Value = new Number(0.1, 0.0);
		Number Real_Value = new Number(0.1, 0.0);
		Pole p = new Pole(Real_Value,Image_Value);
		Zero z = new Zero(Real_Value,Image_Value);

		int numberOfPZThatCanFit = (9999 - size) / (48*2);
		for (int i = 0; i < numberOfPZThatCanFit*2 + 20; i++) {
			b053.add(p);
			b053.add(z);

		}

		OverFlowBlockette o = (OverFlowBlockette) b053;
		assertTrue(b053.isOverFlown());

		List<Blockette> list = b053.split();
		assertEquals(3, list.size());

		B053 first = (B053) list.get(0);
		assertFalse(first.isOverFlown());
		assertEquals(207, first.getZeros().size());
		assertEquals(0, first.getPoles().size());


		B053 second = (B053) list.get(1);
		assertFalse(second.isOverFlown());
		assertEquals(16, second.getZeros().size());
		assertEquals(191, second.getPoles().size());
	

		B053 third = (B053) list.get(2);
		assertFalse(third.isOverFlown());
		assertEquals(0, third.getZeros().size());
		assertEquals(33, third.getPoles().size());
		
	}
	

	
	@Test
	public void overFlowB054() throws Exception {


	}




}






