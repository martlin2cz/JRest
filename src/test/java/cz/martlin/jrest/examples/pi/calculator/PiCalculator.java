package cz.martlin.jrest.examples.pi.calculator;

/**
 * The calculator of Pi. Performs simple technique with aprroximation of pi.
 * 
 * The formulae used for pi computation is elementary liebnitz form, the sum:
 * 
 * <math xmlns='http://www.w3.org/1998/Math/MathML'> <mrow><mi>pi</mi>
 * <mo>=</mo></mrow><mrow> <munderover> <mo>&#8721;</mo> <mrow> <mi>k</mi>
 * <mo>=</mo> <mn>0</mn> </mrow> <mi>n</mi> </munderover>
 * <mfrac> <msup> <mrow> <mo>(</mo> <mrow> <mo>-</mo> <mn>1</mn> </mrow>
 * <mo>)</mo> </mrow> <mi>k</mi> </msup> <mrow> <mrow> <mn>2</mn> <mo></mo>
 * <mi>k</mi> </mrow> <mo>+</mo> <mn>1</mn> </mrow> </mfrac> </mrow> </math>
 * 
 * @author martin
 *
 */
public class PiCalculator {

	public PiCalculator() {
	}

	/**
	 * Calculate approximation of pi of given n.
	 * 
	 * @param n
	 * @return
	 */
	public double calculatePiAprox(int n) {
		double result = 0.0;

		for (int k = 0; k < n; k++) {
			double nom = Math.pow(-1, k);
			double den = 2 * k + 1;

			double frac = nom / den;

			result += frac;
		}

		return 4.0 * result;
	}
}
