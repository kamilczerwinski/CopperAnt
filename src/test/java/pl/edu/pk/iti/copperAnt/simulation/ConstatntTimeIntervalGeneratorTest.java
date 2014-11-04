package pl.edu.pk.iti.copperAnt.simulation;

import org.junit.Assert;
import org.junit.Test;

public class ConstatntTimeIntervalGeneratorTest {

	@Test
	public void getTimeIntervalTest() {
		// given
		ConstantTimeIntervalGenerator generator = new ConstantTimeIntervalGenerator(
				13);
		// when
		int actual = generator.getTimeInterval();
		// then
		Assert.assertEquals(13, actual);
	}

}
