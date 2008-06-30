package com.google.htmm.util;

public class Pair<T1 extends Comparable<T1>, T2> implements Comparable<Pair<T1,T2>>{

	private final T1 first;
	private final T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	public T2 getSecond() {
		return second;
	}

	public T1 getFirst() {
		return first;
	}

	@Override
	public int compareTo(Pair<T1, T2> o) {
		return first.compareTo(o.first);
	}

}
