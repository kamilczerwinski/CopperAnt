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
		this(prop.getProperty("delay-type"), prop.getProperty("delay-name"), Double.parseDouble(prop.getProperty("delay-arg0")));
	}
	
	public WithDelay (String type, String name, double arg0) {
		if (name == "ContinousDistribution") {
			factory = new ContinousDistributionFactory();
		} else {
			factory = new DiscreteDistributionFactory();
		}
		distribution = factory.create(type, arg0);
		
	}
	
	public  int getDelay() {
		return distribution.nextInt();
		
	}
}
