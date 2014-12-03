package pl.edu.pk.iti.copperAnt.simulation.generators.factory;

import cern.jet.random.AbstractDistribution;

public interface DistributionFactory {
	public AbstractDistribution create(String name, double arg0);
}
