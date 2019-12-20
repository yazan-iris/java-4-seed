package edu.iris.dmc.seed.builder;

import java.io.IOException;
import java.util.List;

import edu.iris.dmc.seed.SeedException;

public interface Builder<T> {

	public List<T> createAll(byte[] bytes) throws SeedException, IOException;
	public T create(byte[] bytes) throws SeedException, IOException;
}
