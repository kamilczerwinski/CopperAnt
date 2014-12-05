package pl.edu.pk.iti.copperAnt.simulation.generators;
import org.junit.Assert;
import org.junit.Test;

import cern.jet.random.HyperGeometric;
import cern.jet.random.Poisson;
import pl.edu.pk.iti.copperAnt.simulation.generators.factory.DiscreteDistributionFactory;
import pl.edu.pk.iti.copperAnt.simulation.generators.factory.DistributionFactory;

public class DiscreteDistributionFactoryTest {
	@Test
	public void correctCreateTest() {
		DistributionFactory factory = new DiscreteDistributionFactory();
		Assert.assertEquals(Poisson.class, factory.create("noname", 100).getClass());
		Assert.assertEquals(Poisson.class, factory.create("Poisson", 100).getClass());
		Assert.assertEquals(HyperGeometric.class, factory.create("HyperGeometric", 1000).getClass());

		
	}
	

}
