package edu.iris.dmc.seed.control.timespan;

import edu.iris.dmc.seed.AbstractBlockette;

public class B072 extends AbstractBlockette implements TimeSpanHeader{

	public B072() {
		super(72, "Event Phases Blockette");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toSeedString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//3 Time span flag 					A 		1 [U]
		//Indicates whether the data are for a station network volume’s accounting period or for an event network volume.
			//E — Data are event oriented
			//P — Data are for a given period
	//4 Beginning time of data span 	V 		1—22 TIME
	//5 End time of data span 			V 		1—22 TIME
}
