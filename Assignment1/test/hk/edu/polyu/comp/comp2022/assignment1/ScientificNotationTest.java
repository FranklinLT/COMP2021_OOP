package hk.edu.polyu.comp.comp2022.assignment1;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScientificNotationTest {
    public static float DELTA = 1E-6f;
    @Test(timeout = 2000)
    public void test1() {
        assertEquals(854.9, ScientificNotation.getValueFromAeB("8.549e2"), DELTA);
    }
    @Test(timeout = 2000)
    public void test2() {
        assertEquals(804.9, ScientificNotation.getValueFromAeB("8.049e2"), DELTA);
    }
    @Test(timeout = 2000)
    public void test3() {
        assertEquals(1.33, ScientificNotation.getValueFromAeB("1.33e0"), DELTA);
    }
    @Test(timeout = 2000)
    public void test4() {
        assertEquals(-1.33, ScientificNotation.getValueFromAeB("-1.33e0"), DELTA);
    }
    @Test(timeout = 2000)
    public void test5() {
        assertEquals(-0.133, ScientificNotation.getValueFromAeB("-1.33e-1"), DELTA);
    }
    @Test(timeout = 2000)
    public void test6() {
        assertEquals(-888888, ScientificNotation.getValueFromAeB("-8.88888e5"), DELTA);
    }
}
