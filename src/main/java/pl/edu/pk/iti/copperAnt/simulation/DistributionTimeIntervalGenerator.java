package pl.edu.pk.iti.copperAnt.simulation;

import pl.edu.pk.iti.copperAnt.network.WithDelay;

public class DistributionTimeIntervalGenerator extends WithDelay implements
		TimeIntervalGenerator {

	public DistributionTimeIntervalGenerator(String type, String name,
			double arg0) {
		super(type, name, arg0);

	}

	public DistributionTimeIntervalGenerator() {
		this("DiscreteDistribution", "Poisson", 10);

	}

	@Override
	public int getTimeInterval() {
		return this.getDelay();
	}
}
