package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.IfStatement;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class IfStatementTest {
    Simple simple;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
        String var1 = "vardef vardef1 int x 0";
        String assign1 = "assign assign1 x 1";
        String assign2 = "assign assign2 x 3";

        Simple.addInstruction(var1.split(" ")[0], var1);
        Simple.addInstruction(assign1.split(" ")[0], assign1);
        Simple.addInstruction(assign2.split(" ")[0], assign2);
        Simple.executables.get("vardef1").execute();
    }
    @Test
    public void testConstructor() throws SyntaxErrorException {
        String if1 = "if if1 true assign1 assign2";
        IfStatement ifObj = IfStatement.fromString(if1);
        assertEquals("true", ifObj.getCondition());
        assertEquals("assign1", ifObj.getTrueStatement());
        assertEquals("assign2", ifObj.getFalseStatement());
    }

    @Test
    public void testExecuteTrue() throws SyntaxErrorException {
        String if1 = "if if1 true assign1 assign2";
        IfStatement ifObj = IfStatement.fromString(if1);
        ifObj.execute();
        assertEquals((Integer)1, Simple.int_vars.get("x").getvalue());


    }

    @Test
    public void testExecuteFalse() throws SyntaxErrorException {
        String if2 = "if if2 false assign1 assign2";
        IfStatement ifObj2 = IfStatement.fromString(if2);
        ifObj2.execute();
        assertEquals((Integer)3, Simple.int_vars.get("x").getvalue());
    }

    @Test
    public void testFromStringWitIncompleteCommand() throws SyntaxErrorException {
        String exp = "if if1 false assign1";
        String message = "InvalidFormat: The command is malformed! The statement has unexpected number of tokens";
        exception.expect(InvalidFormatException.class);
        exception.expectMessage(message);
        IfStatement.fromString(exp);
    }

    @Test
    public void testFromStringWithInvalidRef() throws SyntaxErrorException {
        String exp = "if int false assign1 assign2";
        String message = "InvalidIdentifierException: \'" + exp.split(" ")[1] + "\' is a reserved word";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        IfStatement.fromString(exp);
    }

    @Test
    public void testFromStringWithUndeclaredStatementOfTrueStatement() throws SyntaxErrorException {
        String undeclaredStatement = "exp";
        String exp = "if if1 false "+ undeclaredStatement +" assign1";
        String message = "UndeclaredStatementError: \'"+undeclaredStatement+"\' is not declared as executable statement in the scope. IfStatement only allows executable statements";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        IfStatement.fromString(exp);
    }
    @Test
    public void testFromStringWithUndeclaredStatementOfFalseStatement() throws SyntaxErrorException {
        String undeclaredStatement = "exp";
        String exp = "if if1 false assign1 "+ undeclaredStatement;
        String message = "UndeclaredStatementError: \'"+undeclaredStatement+"\' is not declared as executable statement in the scope. IfStatement only allows executable statements";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        IfStatement.fromString(exp);
    }
}
