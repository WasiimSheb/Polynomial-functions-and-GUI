package exe.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  * Introduction to Computer Science 2023, Ariel University,
 *  * Ex2: arrays, static functions and JUnit
 *
 * This JUnit class represents a JUnit (unit testing) for Ex2 - 
 * It contains few testing functions for the polynum functions as define in Ex2.
 * Note: you should add additional JUnit testing functions to this class.
 *
 * @author boaz.ben-moshe
 */

class Ex2Test {
	static final double[] P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3};
	static double[] po1 = {2,2}, po2 = {-3, 0.61, 0.2};;
	static double[] po3 = {2,1,-0.7, -0.02,0.02};
	static double[] po4 = {-3, 0.61, 0.2};

	@Test
	/**
	 * Tests that f(x) == poly(x).
	 */
	void testF() {
		double fx0 = Ex2.f(po1, 0);
		double fx1 = Ex2.f(po1, 1);
		double fx2 = Ex2.f(po1, 2);
		assertEquals(fx0, 2, Ex2.EPS);
		assertEquals(fx1, 4, Ex2.EPS);
		assertEquals(fx2, 6, Ex2.EPS);
	}
	@Test
	/**
	 * Tests that p1(x) + p2(x) == (p1+p2)(x)
	 */
	void testF2() {
		double x = Math.PI;
		double[] po12 = Ex2.add(po1, po2);
		double[] p12 = Ex2.add(P1, P2);
		double f_x= Ex2.f(p12, x);
		double g_x = Ex2.f(P1, x);
		double h_x = Ex2.f(P2, x);
		double f1x = Ex2.f(po1, x);
		double f2x = Ex2.f(po2, x);
		double f12x = Ex2.f(po12, x);
		assertEquals(g_x + h_x, f_x, Ex2.EPS);
		assertEquals(f1x + f2x, f12x, Ex2.EPS);
	}
	@Test
	/**
	 * Tests that p1+p2+ (-1*p2) == p1
	 */
	void testAdd() {
		double[] p1p2 = Ex2.add(P1,P2);
		//double[] p2p = (-1 * P2);
		double[] p12 = Ex2.add(po1, po2);
		double[] minus1 = {-1};
		double[] pp2 = Ex2.mul(po2, minus1);
		double[] p1 = Ex2.add(p12, pp2);
		assertTrue(Ex2.equals(p1, po1));
	}
	@Test
	/**
	 * Tests that p1+p2 == p2+p1
	 */
	void testAdd2() {
		double [] po12 = Ex2.add(P1, P2);
		double [] po21 = Ex2.add(P2, P1);
		double[] p12 = Ex2.add(po1, po2);
		double[] p21 = Ex2.add(po2, po1);
		assertTrue(Ex2.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1+0 == p1
	 */
	void testAdd3() {
		double[] p1 = Ex2.add(po1, Ex2.ZERO);
		double[] p2 = Ex2.add(P1, Ex2.ZERO);
		assertTrue(Ex2.equals(p1, po1));
		assertTrue(Ex2.equals(p2, P1));

	}
	@Test
	/**
	 * Tests that p1*0 == 0
	 */
	void testMul1() {
		double[] p1 = Ex2.mul(po1, Ex2.ZERO);
		double[] p2 = Ex2.mul(p1, Ex2.ZERO);
		assertTrue(Ex2.equals(p2, Ex2.ZERO));
		assertTrue(Ex2.equals(p1, Ex2.ZERO));
	}
	@Test
	/**
	 * Tests that p1*p2 == p2*p1
	 */
	void testMul2() {
		double[] p12 = Ex2.mul(po1, po2);
		double[] p21 = Ex2.mul(po2, po1);
		double [] po1 = Ex2.mul(P1, P2);
		double [] po2 = Ex2.mul(P2, P1);
		assertTrue(Ex2.equals(po1, po2));
		assertTrue(Ex2.equals(p12, p21));
	}
	@Test
	/**
	 * Tests that p1(x) * p2(x) = (p1*p2)(x),
	 */
	void testMulDoubleArrayDoubleArray() {
		double[] xx = {0,1,2,3,4.1,-15.2222};
		double[] p12 = Ex2.mul(po1, po2);
		double [] p1p2 = Ex2.mul(P1, P2);
		for(int i = 0;i<xx.length;i=i+1) {
			double x = xx[i];
			double f1x = Ex2.f(po1, x);
			double f2x = Ex2.f(po2, x);
			double f12x = Ex2.f(p12, x);
			double f_x = Ex2.f(p1p2, x);
			double g_x = Ex2.f(P1, x);
			double h_x = Ex2.f(P2, x);
			assertEquals(f_x, g_x * h_x, Ex2.EPS);
			assertEquals(f12x, f1x*f2x, Ex2.EPS);
		}
	}
	@Test
	/**
	 * Tests a simple derivative examples - till ZERO.
	 */
	void testDerivativeArrayDoubleArray() {
		double[] p = {1,2,3}; //  2+2x+1
		double[] pt = {2,6}; // 6x+2
		double[] dp1 = Ex2.derivative(p); // 2x + 6
		double[] dp2 = Ex2.derivative(dp1); // 2
		double[] dp3 = Ex2.derivative(dp2); // 0
		double[] dp4 = Ex2.derivative(dp3); // 0
		assertTrue(Ex2.equals(dp1, pt));
		assertTrue(Ex2.equals(Ex2.ZERO, dp3));
		assertTrue(Ex2.equals(dp4, dp3));
	}
	@Test
	/** 
	 * Tests the parsing of a polynom in a String like form.
	 */
	public void testFromString() {
		double[] p = {-1.1,2.3,3.1}; // 3.1X^2+ 2.3x -1.1
		String sp2 = "3.1x^2 +2.3x -1.1";
		String sp1 = "-x^3 + 3x^2 + 2";
		String sp12 = "3x^4 + 0.1x^3 + x^2 + 0.1";
		String sp = Ex2.poly(p);
		double [] pp1 = Ex2.getPolynomFromString(sp1);
		double [] pp2 = Ex2.getPolynomFromString(sp12);
		double[] p1 = Ex2.getPolynomFromString(sp);
		double[] p2 = Ex2.getPolynomFromString(sp2);
		String spp = Ex2.poly(p1);
		String spp1 = Ex2.poly(p2);
		boolean isSame1 = Ex2.equals(p1, p);
		boolean isSame2 = Ex2.equals(p2, p);
		boolean isSame3 = Ex2.equals(pp1, p1);
		boolean isSame4 = Ex2.equals(pp2, p2);
		if(!isSame1) {fail();}
		if(!isSame2) {fail();}
		assertEquals(sp, Ex2.poly(p1));
		assertEquals(spp, Ex2.poly(p1));
		assertEquals(spp1, Ex2.poly(p2));

		//P1 ={2,0,3, -1,0}, P2 = {0.1,0,1, 0.1,3}
	}
	@Test
	/**
	 * Tests the equality of pairs of arrays.
	 */
	public void testEquals() {
		double[][] d1 = {{0}, {1}, {1,2,0,0}};
		double[][] d2 = {Ex2.ZERO, {1+Ex2.EPS/2}, {1,2}};
		double[][] xx = {{-2*Ex2.EPS}, {1+Ex2.EPS*1.2}, {1,2,Ex2.EPS/2}};
		for(int i=0;i<d1.length;i=i+1) {
			assertTrue(Ex2.equals(d1[i], d2[i]));
		}
		for(int i=0;i<d1.length;i=i+1) {
			assertFalse(Ex2.equals(d1[i], xx[i]));
		}
	}

	@Test
	/**
	 * Tests is the sameValue function is symmetric.
	 */
	public void testSameValue2() {
		double x1=0, x2=4;
		double rs1 = Ex2.sameValue(po1,po2, x1, x2, Ex2.EPS);
		double rs2 = Ex2.sameValue(po2,po1, x1, x2, Ex2.EPS);
		assertEquals(rs1,rs2,Ex2.EPS);
	}
	@Test
	/**
	 * Test the area function - it should be symmetric.
	 */
	public void testArea() {
		double x1=0, x2=4;
		double a1 = Ex2.area(po1, po2, x1, x2, 100);
		double a2 = Ex2.area(po2, po1, x1, x2, 100);
		assertEquals(a1,a2,Ex2.EPS);
	}
	@Test
	public void testPolynomfrompoints()
	{
		double[] xx = {1, 2, 3};
		double[] yy = {1, 4, 9};
		double[] result = Ex2.PolynomFromPoints(xx, yy);
		double[] expected = {0.0, 0.0, 1.0};
		assertArrayEquals(expected, result, Ex2.EPS);
	}
	@Test
	public void testpoly()
	{
		double[] arr= {2,2,3,4};
		String result4= Ex2.poly(arr);
		String expected="4.0x^3 +3.0x^2 +2.0x +2.0";
		assertEquals(expected,result4);
	}
	@Test
	void testequals4()
	{
		double [] e = {3,4,2};
		double [] v = {3,4,2,0,0,0,0,0,0};
		if (Ex2.equals(e, v) == true)
		{
			assertTrue(Ex2.equals(e, v));
		}
		else {
			fail();
		}
	}
}
