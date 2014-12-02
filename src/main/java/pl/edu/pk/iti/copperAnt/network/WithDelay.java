package pl.edu.pk.iti.copperAnt.network;

import java.util.Properties;

import cern.jet.random.AbstractDistribution;
import pl.edu.pk.iti.copperAnt.simulation.generators.factory.ContinousDistributionFactory;
import pl.edu.pk.iti.copperAnt.simulation.generators.factory.DiscreteDistributionFactory;
import pl.edu.pk.iti.copperAnt.simulation.generators.factory.DistributionFactory;


public  abstract class WithDelay {
	protected DistributionFactory factory;
	protected AbstractDistribution distribution;
	public WithDelay() {
		factory = new DiscreteDistributionFactory();
		distribution = factory.create("Poission", 100);
		
	}
	public WithDelay (Properties prop) {
		if (prop.getProperty("delay-type") == "CContinousDistribution") {
			factory = new ContinousDistributionFactory();
		} else {
			factory = new DiscreteDistributionFactory();
		}
		distribution = factory.create(prop.getProperty("delay-distribution"), Double.parseDouble(prop.getProperty("delay-arg0")));
		
	}
	
	public  int getDelay() {
		return distribution.nextInt();
		
	}
}
