package cz.martlin.jrest.test.pi.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PiCalculatorTest {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final PiCalculator calc = new PiCalculator();

	@Test
	public void testCalculatePiApprox() {
		testCalculatePiApprox(1, 1.0);
		testCalculatePiApprox(10, 0.1);
		testCalculatePiApprox(100, 0.01);
		testCalculatePiApprox(1000, 0.001);
		testCalculatePiApprox(10000, 0.0001);
		testCalculatePiApprox(100000, 0.0001);
		testCalculatePiApprox(1000000, 0.00001);

	}

	private void testCalculatePiApprox(int n, double epsilon) {

		double pi = calc.calculatePiAprox(n);

		double diff = (pi - Math.PI) / Math.PI * 100.0;
		log.debug("n: {}: \t pi: {} \t ({}%)", //
				String.format("%8d", n), //
				String.format("%2.10f", pi), //
				String.format("%2.8f", diff));

		assertEquals(Math.PI, pi, epsilon);
	}

}
