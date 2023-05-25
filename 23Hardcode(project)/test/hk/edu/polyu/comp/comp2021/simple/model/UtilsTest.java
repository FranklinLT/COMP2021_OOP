package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class UtilsTest {
    Simple simple;
    String exp1 = "vardef vardef1 int x 100";
    String exp2 = "binexpr exp2 x + 3";

    String booldef = "vardef vardef2 bool b false";
    String boolexp = "binexpr bool2 x == 3";
    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
        Simple.addInstruction(exp1.split(" ")[0],exp1);
        Simple.addInstruction(exp2.split(" ")[0],exp2);
        Simple.executables.get(exp1.split(" ")[1]).execute();
        Simple.addInstruction(booldef.split(" ")[0], booldef);
        Simple.executables.get(booldef.split(" ")[1]).execute();

        Simple.addInstruction(boolexp.split(" ")[0], boolexp);

    }
    @After
    public void reset(){
        Simple.reset();
    }

    @Test
    public void testIsIntegerExp(){
        assertEquals(true,Utils.isIntegerExp(exp2));
        assertEquals(false, Utils.isIntegerExp(exp1));
    }

    @Test
    public void testGetIntegerValue(){
        assertFalse(Simple.executables.get(exp1.split(" ")[1]) == null);
        assertFalse(Simple.int_exps.get(exp2.split(" ")[1]) == null);

        assertEquals(100, Utils.getIntValue(exp1.split(" ")[3]));
        assertEquals(103, Utils.getIntValue(exp2.split(" ")[1]));
        assertEquals(Utils.MAX_INTEGER, Utils.getIntValue("999999999"));
        assertEquals(Utils.MIN_INTEGER, Utils.getIntValue("-999999999"));
    }
    @Test
    public void testExceptionIsInteger(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("the integer value is invalid");
        Utils.getIntValue("a909999");
    }

    @Test
    public void testIsBooleanExpression(){
        String b1 = "binexpr b1 false || true";
        String b2 = "binexpr b2 b1 && true";
        String b3 = "binexpr b3 3 < 4";
        String exp1 = "binexpr exp1 3 + 4";
        String exp2 = "binexpr b5 exp1 - 3";

        assertTrue(Utils.isBooleanExp(b1));
        assertTrue(Utils.isBooleanExp(b2));
        assertTrue(Utils.isBooleanExp(b3));
        assertFalse(Utils.isBooleanExp(exp1));
        assertFalse(Utils.isBooleanExp(exp2));
    }

    @Test
    public void testIsIntegerExpression(){
        String b1 = "binexpr b1 false || true";
        String b2 = "binexpr b2 b1 && true";
        String b3 = "binexpr b3 3 < 4";
        String exp1 = "binexpr exp1 3 + 4";
        String exp2 = "binexpr b5 exp1 - 3";

        assertFalse(Utils.isIntegerExp(b1));
        assertFalse(Utils.isIntegerExp(b2));
        assertFalse(Utils.isIntegerExp(b3));
        assertTrue(Utils.isIntegerExp(exp1));
        assertTrue(Utils.isIntegerExp(exp2));
    }

    @Test
    public void testIsValidIntegerString(){
        String s1 = "999999999";
        String s2 = "-999999999";
        String s3 = "a999";
        String s4 = "+123";
        String s5 = "99e0";


        assertTrue(Utils.isValidIntegerString(s1));
        assertTrue(Utils.isValidIntegerString(s2));
        assertTrue(Utils.isValidIntegerString(s4));
        assertFalse(Utils.isValidIntegerString(s3));
    }

    @Test
    public void testIsBoolean(){
        String s1 = "false";
        String s2 = "true";

        assertTrue(Utils.isBoolean(s1));
        assertTrue(Utils.isBoolean(s2));
        assertTrue(Utils.isBoolean("b"));//variable
        assertTrue(Utils.isBoolean("bool2"));//expression
        assertFalse(Utils.isBoolean("bollll"));//invalid input
    }

    @Test
    public void isValidRef() throws SyntaxErrorException {
        assertTrue(Utils.isValidRef("var"));
    }

    @Test
    public void testInvalidNameWithNumber() throws InvalidIdentifierException {
        String name=  "1exp";
        String message = "InvalidIdentifierException: Expression/Statement reference, \'" + name + "\' cannot be started by a number.";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Utils.isValidName(name);
    }

    @Test
    public void testInvalidNameWithManyCharacters() throws InvalidIdentifierException {
        String name=  "verylongvarableaname";
        String message = "InvalidIdentifierException: Expression/Statement reference,  \'"+ name +"\' exceeded maximum length! Expression/Statement labels or references should be less than 8 characters.";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Utils.isValidName(name);
    }

    @Test
    public void testIsValidRefWithKeyword() throws SyntaxErrorException {
        String name=  "int";
        String message = "InvalidIdentifierException: \'" + name + "\' is a reserved word";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Utils.isValidRef(name);
    }

    @Test
    public void testIsValidRefWithAnAlreadyDeclaredRef() throws SyntaxErrorException {
        String name = "vardef1";
        String message = "InvalidIdentifierException: Reference,  \'"+ name + "\' has already been declared";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Utils.isValidRef(name);
    }

    }
