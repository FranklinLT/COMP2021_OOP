package hk.edu.polyu.comp.comp2021.simple.model;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.Block;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BlockTest {
    Simple simple;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void prepare(){
        simple =Simple.getSimpleInstance();
        String var1 = "vardef vardef1 int x 0";
        String assign1 = "assign assign1 x 1";
        String assign2 = "assign assign2 x 3";

        Simple.addInstruction(var1.split(" ")[0], var1);
        Simple.addInstruction(assign1.split(" ")[0], assign1);
        Simple.addInstruction(assign2.split(" ")[0], assign2);
    }

    @Test
    public void testConstructor() throws SyntaxErrorException {
        String bStr = "block b1 vardef1 assign1 assign2";
        Block b1 = Block.fromString(bStr);
        assertTrue(Arrays.asList(b1.getStatements()).contains("vardef1"));
        assertTrue(Arrays.asList(b1.getStatements()).contains("assign1"));
        assertTrue(Arrays.asList(b1.getStatements()).contains("assign2"));

    }

    @Test
    public void testExecute() throws SyntaxErrorException {
        String bStr = "block b1 vardef1 assign1 assign2";
        Block b1 = Block.fromString(bStr);

        b1.execute();

        assertEquals((Integer)3, Simple.int_vars.get("x").getvalue());
    }

    @Test
    public void testFromStringWitIncompleteCommand() throws SyntaxErrorException {
        String exp = "block b1";
        String message = "InvalidFormat: The command is malformed! The statement has unexpected number of tokens";
        exception.expect(InvalidFormatException.class);
        exception.expectMessage(message);
        Block.fromString(exp);
    }

    @Test
    public void testFromStringWithInvalidRef() throws SyntaxErrorException {
        String exp = "block int exp1";
        String message = "InvalidIdentifierException: \'" + exp.split(" ")[1] + "\' is a reserved word";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Block.fromString(exp);
    }

    @Test
    public void testFromStringWithUndeclaredStatement() throws SyntaxErrorException {
        String undeclaredStatement = "exp";
        String exp = "block b1 vardef1 "+ undeclaredStatement;
        String message = "UndeclaredStatementError: \'"+undeclaredStatement+"\' is not declared as executable statement in the scope. Block only allows executable statements";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Block.fromString(exp);
    }




}
