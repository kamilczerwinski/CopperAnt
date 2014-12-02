package pl.edu.pk.iti.copperAnt.simulation;

import org.junit.Assert;
import org.junit.Test;

public class DistributionTimeIntervalGeneratorTest {

	@Test
	public void getTimeIntervalTest() {
		// given
		DistributionTimeIntervalGenerator generator = new DistributionTimeIntervalGenerator();
	
		Assert.assertNotEquals(generator.getTimeInterval(), generator.getTimeInterval());
	}
}
