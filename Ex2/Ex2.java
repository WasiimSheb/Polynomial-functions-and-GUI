package exe.ex2;

import java.util.Arrays;

/**
 * Introduction to Computer Science 2023, Ariel University, Ex2: arrays, static
 * functions and JUnit
 *
 * This class represents a set of functions on a polynom - represented as array
 * of doubles. The array {0.1, 0, -3, 0.2} represents the following polynom:
 * 0.2x^3-3x^2+0.1 This is the main Class you should implement (see "add your
 * code here")
 *
 * @author boaz.benmoshe
 */
public class Ex2 {
	/**
	 * Epsilon value for numerical computation, it serves as a "close enough"
	 * threshold.
	 */
	public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
	/** The zero polynom is represented as an array with a single (0) entry. */
	public static final double[] ZERO = { 0 };

	/**
	 * Computes the f(x) value of the polynom at x.
	 * 
	 * @param poly
	 * @param x
	 * @return f(x) - the polynom value at x.
	 */
	public static double f(double[] poly, double x) {
		double ans = 0;
		for (int i = 0; i < poly.length; i++) {
			double c = Math.pow(x, i);
			ans += c * poly[i];
		}
		return ans;
	}

//	public static void main(String[] args) {
//		double [] arr= {1,4,3};
//		System.out.println(f(arr, 2));
//	}
	/**
	 * Given a polynom (p), a range [x1,x2] and an epsilon eps. This function
	 * computes an x value (x1<=x<=x2) for which |p(x)| < eps, assuming p(x1)*p(x1)
	 * <= 0. This function should be implemented recursively.
	 * 
	 * @param p   - the polynom
	 * @param x1  - minimal value of the range
	 * @param x2  - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
	 */
	public static double root_rec(double[] p, double x1, double x2, double eps) {
		double f1 = f(p, x1);
		double f2 = f(p, x2);
		double x12 = (x1 + x2) / 2;
		double f12 = f(p, x12);
		if (f1 * f2 <= 0 && Math.abs(f12) < eps) {
			return x12;
		}
		if (f12 * f1 <= 0) {
			return root_rec(p, x1, x12, eps);
		} else {
			return root_rec(p, x12, x2, eps);
		}
	}

	/**
	 * This function computes a polynomial representation from a set of 2D points on
	 * the polynom. The solution is based on: //
	 * http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
	 * Note: this function only works for a set of points containing up to 3 points,
	 * else returns null.
	 * 
	 * @param xx
	 * @param yy
	 * @return an array of doubles representing the coefficients of the polynom.
	 */
	public static double[] PolynomFromPoints(double[] xx, double[] yy) {
		double[] ans = null;
		int lx = xx.length;
		int ly = yy.length;
		if (xx != null && yy != null && lx == ly && lx > 1 && lx < 4) {
			if (yy.length == 3 && xx.length == 3) { // Quadratic equation;
				ans = new double[3];
				double a,b,c;
				a = (xx[0] * (yy[2] - yy[1]) + xx[1] * (yy[0]-yy[2])+xx[2] * (yy[1]-yy[0])) / ((xx[0]-xx[1]) * (xx[0]-xx[2])*(xx[1]-xx[2]));
				b = ((yy[1]-yy[0])/(xx[1]-xx[0])-(a*(xx[0]+xx[1])));
				c = yy[0] - (a*(Math.pow(xx[0], 2))) - (b*xx[0]);
				ans[0] = c;
				ans[1] = b;
				ans[2] = a;
			}
			if (yy.length == 2 && xx.length == 2)
			{
				double x1 = xx[0];
				double x2 = xx[1];
				double y1 = yy[0];
				double y2 = yy[1];
				ans = new double [2];
				ans [0] = (y2 - y1) / (x2 - x1);
				ans [1] = (y1 - (ans[0] * x1));
 			}
		}
		return ans;
	}
	/**
	 * Two polynoms are equal if and only if the have the same values f(x) for 1+n
	 * values of x, where n is the max degree (over p1, p2) - up to an epsilon (aka
	 * EPS) value.
	 * 
	 * @param p1 first polynom
	 * @param p2 second polynom
	 * @return true iff p1 represents the same polynom as p2.
	 */
	public static boolean equals(double[] p1, double[] p2) {
		boolean ans = true; 
		int n = Math.max(p1.length, p2.length); // the range of checking the values of f
		for (int i = 0; i < n + 1; i++) {
			if ( Math.abs( f(p1, i) - f(p2, i)) >= EPS) { // checking the i values from 1 to n+1 range if their substraction is less than an epsilon
				ans = false; // if the sum is greater epsilon, changing the ans value into false
			}
		}
		return ans;
	}
//	public static void main(String[] args) {
//		double [] x = {2,1,0,0,0};
//		double [] y = {2,1};
//		System.out.println(equals(x,y));
//	}
	/**
	 * Computes a String representing the polynom. For example the array
	 * {2,0,3.1,-1.2} will be presented as the following String "-1.2x^3 +3.1x^2
	 * +2.0"
	 * 
	 * @param poly the polynom represented as an array of doubles
	 * @return String representing the polynom:
	 */
	public static String poly(double[] poly) {
		String ans = ""; // creating an empty String which would be the answer 
		if (poly.length == 0) { // checking if the the entered string is null
			ans = "0";                                                                                                                   
		} else {
			for (int i = poly.length - 1; i >= 0; i--) {
				if ((poly[i] != 0)) { // if the place does not contain x
					if (i == 0) {
						ans += poly[i]; // adding the the index of poly as it is
					}
					if (i == 1) {
						if (poly[i - 1] > 0) { // checking if the num is positive
							ans += poly[i] + "x" + " +";
						} else { // if the the coeff is negative
							ans += poly[i] + "x" + "";
						}
					} else {
						if (i != 0) { // anywhere else so we do not get an error
							if (poly[i - 1] > 0) { // 
								ans += poly[i] + "x" + "^" + i + " +";
							} else {
								ans += poly[i] + "x" + "^" + i + " ";
							}
						}
					}
				} else {
					if ((poly[i] == 0)) {
						if (i > 1) {
							ans += "+";
						}
					}
				}
			}
		}
		return ans;
	}
//	public static void main(String[] args) {
//		double [] x = {2, 2, 3, 4};
//		System.out.println(poly(x));
//	}

	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an epsilon eps. This function
	 * computes an x value (x1<=x<=x2) for which |p1(x) -p2(x)| < eps, assuming
	 * (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
	 * 
	 * @param p1  - first polynom
	 * @param p2  - second polynom
	 * @param x1  - minimal value of the range
	 * @param x2  - maximal value of the range
	 * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
	 * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
	 */
	public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
		double fx11= f(p1,x1); // checking the func p1 in x1
		double fx12 = f(p1,x2); // checking the func p1 in x2
		double fx21 = f(p2,x1); // checking the func p2 in x1
		double fx22 = f(p2,x2); // checking the func p2 in x2
		double x12 = (x1+x2)/2; // creating a mid value 
		double f112 = f(p1,x12); // checking the func p1 in x12
		double f212 = f(p2,x12); // checking the func p2 in x12
		double d1 = fx11- fx21; // checking the distance between f(p1, x1) - f(p2, x1)
		double d2 = fx12 - fx22; // checking the distance between f(p1, x2) - f(p2, x2)
		double disc = f112 - f212; // checking the distance between f(p1, x12) - f(p2, x12)
		while (d1 * d2 <= 0 && Math.abs(disc) >= eps) { // while the disc is greater than eps and the distance between x's is minus or 0
			if(disc * d1 <= 0) { // if the there's a value between d1 and disc is negative
			x2 = x12; // make x2 closer and check from the new range
			fx12 = f(p1,x2); // checking the func p1 in x2
			fx22 = f(p2,x2); // checking the func p2 in x2
			}
			else { // if the there's a value between d2 and disc is negative
			x1 = x12; // make x1 closer and update the new range 
			fx11 = f(p1,x1); // updating the new value of x1 in p1 
			fx21 = f(p2,x1); // updating the new value of x1 in p2
			}
			x12 = (x1 + x2) / 2; // updating x12 to be a mid value of the new vlaues
			d1 = fx11 - fx21; // updating the distance between f(p1, x1) - f(p2, x1)
			d2 = fx12 - fx22; // updating the distance between f(p1, x2) - f(p2, x2)
			f112 = f(p1,x12);
			f212 = f(p2,x12);
			disc = f112 - f212;
		}
		return x12;
	}
	/**
	 * Given a polynom (p), a range [x1,x2] and an integer with the number (n) of
	 * sample points. This function computes an approximation of the length of the
	 * function between f(x1) and f(x2) using n inner sample points and computing
	 * the segment-path between them. assuming x1 < x2. This function should be
	 * implemented iteratively (none recursive).
	 * 
	 * @param p                - the polynom
	 * @param x1               - minimal value of the range
	 * @param x2               - maximal value of the range
	 * @param numberOfSegments - (A positive integer value (1,2,...).
	 * @return the length approximation of the function between f(x1) and f(x2).
	 */
	public static double length(double[] p, double x1, double x2, int numberOfSegments) {
		double ans = 0;
		double len =(x1 - x2) / numberOfSegments;
		double [] xx = new double [numberOfSegments + 1];
		double [] yy = new double [numberOfSegments + 1];
		for ( int i = 0; i <= numberOfSegments; i++)
			{
				xx[i] = x1 + i * len;
				yy[i] = f(p, xx[i]);
			}
		for ( int i = 0; i < numberOfSegments; i++)
		{
			double seglen = Math.sqrt(Math.pow(xx[i+1] - xx[i], 2) + Math.pow(yy[i+1] - yy[i], 2));
			ans += seglen;
		}
		return ans;
	}
	public static void main(String[] args) {
		double[] p = {1.0, 2.0, 3.0};
        double x1 = 0.0;
        double x2 = 2.0;
        int numberOfSegments = 4;
        System.out.println(length(p, x1, x2, numberOfSegments));
	}
	/**
	 * Given two polynoms (p1,p2), a range [x1,x2] and an integer representing the
	 * number of Trapezoids between the functions (number of samples in on each
	 * polynom). This function computes an approximation of the area between the
	 * polynoms within the x-range. The area is computed using Riemann's like
	 * integral (https://en.wikipedia.org/wiki/Riemann_integral)
	 * 
	 * @param p1                - first polynom
	 * @param p2                - second polynom
	 * @param x1                - minimal value of the range
	 * @param x2                - maximal value of the range
	 * @param numberOfTrapezoid - a natural number representing the number of
	 *                          Trapezoids between x1 and x2.
	 * @return the approximated area between the two polynoms within the [x1,x2]
	 *         range.
	 */
	public static double area(double[] p1, double[] p2, double x1, double x2, int numberOfTrapezoid) {
		 double ans = 0;
			double x12 = x1;
			double width = (Math.abs(x1 - x2)) / numberOfTrapezoid;
			for (double i = x1; i < x2; i+=width)
			{
			double temp = 0;
		    x12 += width;
			double base1 = Math.abs(f(p1, i) - f(p2, i));
			double base2 = Math.abs(f(p1, x12) - f(p2, x12));
			double xx = sameValue(p2, p1, x12, x12, EPS);
			double yy = sameValue(p2, p1, i, i, EPS);
			if ( xx == 0) {
				temp = (base1 * width) / 2; 
			}
			if ( yy == 0 ){
				temp = (base2 * width) / 2;
			}
			temp = ((base1 + base2) * width) / 2;
			ans += temp;
			}
			return ans;
	}
//		double base1 = Math.abs(f(p1, x1) - f(p2, x1));
//		double base2 = Math.abs(f(p1, x2) - f(p2, x2));
	/**
	 * this is an assistance function to get the coefficients of the polynom
	 * @param s
	 * @return
	 */
	private static double getCoeff(String s) {
		if (s.startsWith("x")) {
			return 1;
		}
		else if (s.startsWith("-x")) {
			return -1;
		}
		else
			return Double.parseDouble(s.substring(0, s.indexOf("x")));
	}
	/**
	 * This function computes the array representation of a polynom from a String
	 * representation. Note:given a polynom represented as a double array,
	 * getPolynomFromString(poly(p)) should return an array equals to p.
	 * 
	 * @param p - a String representing polynom.
	 * @return
	 */
	public static double [] getPolynomFromString(String p) {
		if ((p != null) && (p != "")) {
			p = p.replaceAll("-", "+-");
			p = p.replaceAll(" ", "");
			if (p.startsWith("+-"))
				p = p.substring(1);
			String[] s = p.split("\\+");
			int size;
			if (p.contains("^")) {
				size = Integer.parseInt(s[0].substring(s[0].indexOf("^") + 1)) + 1;
			}
			else if (p.contains("x")) {
				size = 2;
			} else {
				size = 1;
			}
			double[] ans = new double[size];
			double coeff;
			int index;
			for (int i = 0; i < s.length; i++) {
				if (s[i].contains("^")) {
					index = Integer.parseInt(s[i].substring(s[i].indexOf("^") + 1));
					coeff = getCoeff(s[i]);
				} else if (s[i].contains("x")) {
					index = 1;
					coeff = getCoeff(s[i]);
				} else {
					coeff = Double.parseDouble(s[i]);
					index = 0;
				}
				ans[index] = coeff;
			}
			return ans;
		}
		return null;
	}
	/**
	 * This function computes the polynom which is the sum of two polynoms (p1,p2)
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] add(double[] p1, double[] p2) {
		int s = Math.max(p1.length, p2.length);
		double[] ans = new double[s];
		for ( int i = 0; i < s; i++)
		{
			double coeff = 0;
			if ( i < p1.length)
			{
				coeff = p1[i];
			}
			double coeff1 = 0;
			if ( i < p2.length)
			{
				coeff1 = p2[i];
			}
			ans [i] = coeff + coeff1;
		}
		return ans;
	}
//	public static void main(String[] args) {
//		double [] x = {1, 2, 3, 5};
//		double [] y = {2, 1, 5, 3};
//		System.out.println(Arrays.toString(add(y,x)));
//	}
		
	/**
	 * This function computes the polynom which is the multiplication of two
	 * polynoms (p1,p2)
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static double[] mul(double[] p1, double[] p2) {
		int c = p1.length + p2.length - 1;
		double[] ans = new double[c];
		for (int i = 0; i < p1.length; i++) {
			for (int j = 0; j < p2.length; j++) {
				ans[i + j] += p1[i] * p2[j];
			}
		}
		return ans;
	}
	/**
	 * This function computes the derivative polynom:.
	 * 
	 * @param po
	 * @return
	 */
	public static double[] derivative(double[] po) {
		double[] ans = ZERO;
		if ( po.length >= 1 )
		{
		int c = po.length - 1;
		 ans = new double[c];
		for (int i = po.length - 1; i > 0; i--) {
			ans[i - 1] = po[i] * i;
			}
			return ans;
		}
		return ans;
	}
}
