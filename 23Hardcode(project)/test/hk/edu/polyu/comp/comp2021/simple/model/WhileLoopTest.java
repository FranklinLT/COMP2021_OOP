package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.WhileLoop;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class WhileLoopTest {
    Simple simple;
    String vardef1 = "vardef vardef1 int x 1";
    String vardef2 = "vardef vardef2 int y 5";
    String exp1 = "binexpr exp2 x + 1";
    String exp2 = "binexpr exp3 y - 1";
    String bool1 = "binexpr exp4 x < 5";
    String assign1 = "assign assign1 x exp2";
    String assign2 = "assign assign2 y exp3";
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
        Simple.addInstruction(vardef1.split(" ")[0], vardef1);
        Simple.addInstruction(vardef2.split(" ")[0], vardef2);
        Simple.addInstruction(exp1.split(" ")[0], exp1);
        Simple.addInstruction(exp2.split(" ")[0], exp2);
        Simple.addInstruction(bool1.split(" ")[0], bool1);
        Simple.addInstruction(assign1.split(" ")[0], assign1);
        Simple.addInstruction(assign2.split(" ")[0], assign2);

        Simple.executables.get("vardef1").execute();
        Simple.executables.get("vardef2").execute();

    }
    @After
    public void reset(){
        Simple.reset();
    }

    @Test
    public void testConstructor() throws SyntaxErrorException {
        String whileStr = "while while1 exp4 assign1 assign2";
        WhileLoop loop = WhileLoop.fromString(whileStr);

        assertEquals("exp4", loop.getCondition());
        assertTrue(Arrays.asList(loop.getStatements()).contains("assign1"));
        assertTrue(Arrays.asList(loop.getStatements()).contains("assign2"));
    }


    @Test
    public void testExecute() throws SyntaxErrorException {
        String whileStr = "while while1 exp4 assign1 assign2";
        WhileLoop loop = WhileLoop.fromString(whileStr);

        loop.execute();

        assertEquals((Integer)5, Simple.int_vars.get("x").getvalue());
        assertEquals((Integer)1, Simple.int_vars.get("y").getvalue());
    }

    @Test
    public void testFromStringWitIncompleteCommand() throws SyntaxErrorException {
        String exp = "while while1 exp4";
        String message = "InvalidFormat: The command is malformed! The statement has unexpected number of tokens";
        exception.expect(InvalidFormatException.class);
        exception.expectMessage(message);
        WhileLoop.fromString(exp);
    }

    @Test
    public void testFromStringWithInvalidRef() throws SyntaxErrorException {
        String exp = "while int exp4 assign1 assign3";
        String message = "InvalidIdentifierException: \'" + exp.split(" ")[1] + "\' is a reserved word";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        WhileLoop.fromString(exp);
    }

    @Test
    public void testFromStringWithUndeclaredStatement() throws SyntaxErrorException {
        String undeclaredStatement = "assign3";
        String exp = "while while1 exp4 "+ undeclaredStatement;
        String message = "UndeclaredStatementError: \'"+undeclaredStatement+"\' is not declared as executable statement in the scope. WhileLoop only allows executable statements";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        WhileLoop.fromString(exp);
    }

}
