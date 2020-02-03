package edu.iris.seed.timespan;

import java.time.ZonedDateTime;

import edu.iris.seed.SeedException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class B070 implements TimeSpanBlockette{

	private ZonedDateTime startTime;
	private ZonedDateTime endTime;
	@Override
	public String toSeedString() throws SeedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
