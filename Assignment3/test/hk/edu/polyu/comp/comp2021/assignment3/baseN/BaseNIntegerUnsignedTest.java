package hk.edu.polyu.comp.comp2021.assignment3.baseN;

import org.junit.Before;
import org.junit.Test;
import static hk.edu.polyu.comp.comp2021.assignment3.baseN.BaseNIntegerUnsigned.*;

import static org.junit.Assert.*;

public class BaseNIntegerUnsignedTest {
    int v0 = 0;
    int v1 = 0;
    int v2 = 1;
    int v3 = 160;
    int v4 = 95;
    int v5 = 32;

    int base1 = 11;
    int base2 = 5;

    int invalidBase1 = -3;
    int invalidBase2 = 1;
    int invalidBase3 = 27;

    BaseNIntegerUnsigned u0;
    BaseNIntegerUnsigned u1;
    BaseNIntegerUnsigned u2;
    BaseNIntegerUnsigned u3;
    BaseNIntegerUnsigned u4;
    BaseNIntegerUnsigned u5;

    BaseNIntegerUnsigned bigU1;
    BaseNIntegerUnsigned bigU2;
    BaseNIntegerUnsigned bigU3;

    String u1Magnitude = "A";
    String u2Magnitude = "B";
    String u3Magnitude = "BDG";
    String u4Magnitude = "DEA";
    String u5Magnitude = "BBC";

    String invalidMagnitude1 = null;
    String invalidMagnitude2 = "";
    String invalidMagnitude3 = "B8A";

    String u1String = "A(11)";
    String u2String = "B(11)";
    String u3String = "BDG(11)";
    String u4String = "DEA(5)";
    String u5String = "BBC(5)";

    @Before
    public void before(){
        u0 = new BaseNIntegerUnsigned(encode(v0, base1), base1);
        u1 = new BaseNIntegerUnsigned(DIGIT_ZERO + encode(v1, base1), base1);
        u2 = new BaseNIntegerUnsigned(DIGIT_ZERO + (DIGIT_ZERO + encode(v2, base1)), base1);
        u3 = new BaseNIntegerUnsigned(encode(v3, base1), base1);

        u4 = new BaseNIntegerUnsigned(encode(v4, base2), base2);
        u5 = new BaseNIntegerUnsigned(encode(v5, base2), base2);

        bigU1 = new BaseNIntegerUnsigned("FGHIJKLMNOPQ", BASE_MAXIMUM);
        bigU2 = new BaseNIntegerUnsigned("VUTSRXYZABCD", BASE_MAXIMUM);
        bigU3 = new BaseNIntegerUnsigned("BBBBBBIKLNPRT", BASE_MAXIMUM);
    }

    @Test
    public void testConstructor01(){
        assertEquals(v0, u0.toInteger());
        assertEquals(v1, u1.toInteger());
        assertEquals(v2, u2.toInteger());
        assertEquals(v3, u3.toInteger());
        assertEquals(v4, u4.toInteger());
        assertEquals(v5, u5.toInteger());
    }

    @Test
    public void testConstructor02(){
        assertEquals(u0, u1);
        assertNotEquals(u1, u2);
        assertNotEquals(u1, new Object());
    }

    @Test
    public void testIsValidBase01() {
        assertTrue(BaseNIntegerUnsigned.isValidBase(base1));
        assertTrue(BaseNIntegerUnsigned.isValidBase(base2));
    }

    @Test
    public void testIsValidBase02(){
        assertFalse(BaseNIntegerUnsigned.isValidBase(invalidBase1));
        assertFalse(BaseNIntegerUnsigned.isValidBase(invalidBase2));
        assertFalse(BaseNIntegerUnsigned.isValidBase(invalidBase3));
    }

    @Test
    public void testIsValidMagnitude01() {
        assertTrue(BaseNIntegerUnsigned.isValidMagnitude(u1Magnitude, base1));
        assertFalse(BaseNIntegerUnsigned.isValidMagnitude(u3Magnitude, base2));
        System.out.println("111111111111111111111111111111111");
    }

    @Test
    public void testIsValidMagnitude02(){
        //assertFalse(BaseNIntegerUnsigned.isValidMagnitude(invalidMagnitude1, base1));
        //assertFalse(BaseNIntegerUnsigned.isValidMagnitude(invalidMagnitude2, base1));
        assertFalse(BaseNIntegerUnsigned.isValidMagnitude(invalidMagnitude3, base1));
        //assertFalse(BaseNIntegerUnsigned.isValidMagnitude(u1Magnitude, invalidBase1));
        //assertFalse(BaseNIntegerUnsigned.isValidMagnitude(u1Magnitude, invalidBase2));
        //assertFalse(BaseNIntegerUnsigned.isValidMagnitude(u1Magnitude, invalidBase3));
    }
/**
    @Test
    public void testToInteger01(){
        assertEquals(v3, u3.toInteger());
        assertEquals(v4, u4.toInteger());
    }

    @Test
    public void testToInteger02(){
        assertEquals(v1, u1.toInteger());
        assertEquals(v2, u2.toInteger());
    }

    @Test
    public void testToString01(){
        assertEquals(u1String, u1.toString());
        assertEquals(u2String, u2.toString());
        assertEquals(u3String, u3.toString());
    }


    @Test
    public void testCompare01(){
        assertTrue(u1.compare(u2) < 0);
        assertTrue(u2.compare(u1) > 0);
        assertTrue(u0.compare(u1) == 0);
    }

    @Test
    public void testCompare02(){
        assertTrue(bigU1.compare(bigU2) < 0);
        assertTrue(bigU2.compare(bigU1) > 0);
    }

    @Test
    public void testEncode01(){
        assertEquals(u2Magnitude, BaseNIntegerUnsigned.encode(v2, base2));
        assertEquals(u3Magnitude, BaseNIntegerUnsigned.encode(v3, base1));
    }

    @Test
    public void testEncode02(){
        assertEquals(u1Magnitude, BaseNIntegerUnsigned.encode(v1, base1));
    }

    @Test
    public void testAdd01(){
        assertEquals(v2 + v3, u2.add(u3).toInteger());
        assertEquals(v1 + v2, u1.add(u2).toInteger());
    }

    @Test
    public void testAdd02(){
        // test carry
        assertEquals(v4 + v5, u4.add(u5).toInteger());
    }

    @Test
    public void testAdd03(){
        // test big BaseNIntegerUnsigned
        assertEquals(bigU3, bigU1.add(bigU2));
    }

    @Test
    public void testSubtract01(){
        assertEquals(v2 - v1, u2.subtract(u1).toInteger());
        assertEquals(v4 - v5, u4.subtract(u5).toInteger());
    }

    @Test
    public void testSubtract02(){
        // test borrow
        assertEquals(v3 - v2, u3.subtract(u2).toInteger());
    }

    @Test
    public void testSubtract03(){
        // test big BaseNIntegerUnsigned
        assertEquals(bigU1, bigU3.subtract(bigU2));
    }
*/
}