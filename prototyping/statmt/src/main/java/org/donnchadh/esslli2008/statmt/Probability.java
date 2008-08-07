package org.donnchadh.esslli2008.statmt;

public abstract class Probability<T> {
	final T event;
	public Probability(T event) {
		this.event = event;
	}
	
	public T getEvent() {
		return event;
	}
}
