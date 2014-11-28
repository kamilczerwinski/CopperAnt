package pl.edu.pk.iti.copperAnt.simulation.generators;

public class Poisson implements BaseGenerator {

	@Override
	public int generate(int range) {
		double L = Math.exp(-range);
		double p = 1.0;
		int k = 0;

		do {
		  k++;
		  p *= Math.random(); // U ~[0,1]
		} while (p > L);

		return k - 1;
	}

}
