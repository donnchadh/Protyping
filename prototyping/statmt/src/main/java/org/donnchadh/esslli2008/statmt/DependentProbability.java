package org.donnchadh.esslli2008.statmt;

public class DependentProbability<G, T> extends Probability<T> {

	private final G given;

	public DependentProbability(G given, T event) {
		super(event);
		this.given = given;
	}
	
	public G getGiven() {
		return given;
	}
}
