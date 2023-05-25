package hk.edu.polyu.comp.comp2021.assignment2.complex;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class RationalTest {
    @Test
    public void testConstructor_01() {
        Rational r1 = new Rational(1, 2);
        assertEquals(r1.toString(), "1/2");
    }

    @Test
    public void testConstrcutor_02() {
        Rational r2 = new Rational(0, 6);
        assertEquals(r2.toString(), "0/6");
    }

    @Test
    public void testSimplify_01() {
        Rational r1 = new Rational(4, 10);
        r1.simplify();
        assertEquals(r1.toString(), "2/5");
    }

    @Test
    public void testSimplify_02() {
        Rational r2 = new Rational(12, 3);
        r2.simplify();
        assertEquals(r2.toString(), "4/1");
    }

    @Test
    public void testSimplify_03() {
        Rational r3 = new Rational(0, 6);
        r3.simplify();
        assertEquals(r3.toString(), "0/1");
    }

    @Test
    public void testSimplify_04() {
        Rational r4 = new Rational(2, -6);
        r4.simplify();
        assertEquals(r4.toString(), "-1/3");
    }

    @Test
    public void testSimplify_05() {
        Rational r5 = new Rational(-4, -16);
        r5.simplify();
        assertEquals(r5.toString(), "1/4");
    }

    @Test
    public void testAddition() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 3);

        Rational rSUm = r1.add(r2);
        rSUm.simplify();

        assertEquals(rSUm.toString(), "5/6");
    }

    @Test
    public void testSubstraction() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(1, 4);

        Rational rSub = r1.subtract(r2);
        rSub.simplify();

        assertEquals(rSub.toString(), "5/12");
    }

    @Test
    public void testMuliplication() {
        Rational r1 = new Rational(7, 8);
        Rational r2 = new Rational(5, 6);

        Rational rMul = r1.multiply(r2);
        rMul.simplify();

        assertEquals(rMul.toString(), "35/48");
    }

    @Test
    public void testMultiplication_02() {
        Rational r1 = new Rational(5, 6);
        Rational r2 = new Rational(0, 2);

        Rational rMul = r1.multiply(r2);
        rMul.simplify();

        assertEquals(rMul.toString(), "0/1");
    }

    @Test
    public void testDevision() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(3, 4);

        Rational rDiv = r1.divide(r2);
        rDiv.simplify();

        assertEquals(rDiv.toString(), "8/9");
    }

    @Test
    public void testDevision_02() {
        Rational r1 = new Rational(0, 1);
        Rational r2 = new Rational(5, 6);

        Rational rDiv = r1.divide(r2);
        rDiv.simplify();

        assertEquals(rDiv.toString(), "0/1");
    }
}
