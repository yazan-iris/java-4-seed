package edu.iris.seed;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public interface SeedContainer<E extends SeedObject> {

	public List<E> blockettes();

	public E add(E e) throws SeedException;

	public boolean addAll(Collection<E> c) throws SeedException;

	public boolean isEmpty();

	public int size();

	void clear();

	public E remove(E e);

	public ListIterator<E> listIterator();

	public ListIterator<E> listIterator(int index);

}
