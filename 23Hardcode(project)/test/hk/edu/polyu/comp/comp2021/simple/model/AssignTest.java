package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.Assign;
import hk.edu.polyu.comp.comp2021.simple.model.statements.VarDef;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class AssignTest {
    Simple simple;
    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
    }
    @Test
    public void testConstructor() throws SyntaxErrorException {
        Assign assign = Assign.fromString("assign assign1 x 20");
        assertFalse(assign == null);
        assertEquals("x" , assign.getVarName());
        assertEquals("20", assign.getRawVal());
    }

    @Test
    public void testExecute() throws SyntaxErrorException {
        VarDef varDef = VarDef.fromString("vardef varded1 int x 100");
        varDef.execute();
        assertEquals((Integer) 100, Simple.int_vars.get(varDef.getVarName()).getvalue());

        Assign assign = Assign.fromString("assign assign1 x 20");
        assign.execute();
        assertEquals((Integer) 20, Simple.int_vars.get(varDef.getVarName()).getvalue());

    }

    @Test
    public void testExecuteBoolean() throws SyntaxErrorException {
        VarDef varDef = VarDef.fromString("vardef varded1 bool b false");
        varDef.execute();
        assertEquals((Boolean) false, Simple.bool_vars.get(varDef.getVarName()).getvalue());

        Assign assign = Assign.fromString("assign assign1 b true");
        assign.execute();
        assertEquals((Boolean) true, Simple.bool_vars.get(varDef.getVarName()).getvalue());
    }

    @Test
    public void testFromStringWitIncompleteCommand() throws SyntaxErrorException {
        String exp = "assign assign1 a";
        String message = "InvalidFormat: The command is malformed! The statement has unexpected number of tokens";
        exception.expect(InvalidFormatException.class);
        exception.expectMessage(message);
        Assign.fromString(exp);
    }

    @Test
    public void testFromStringWithInvalidRef() throws SyntaxErrorException {
        String exp = "assign int a 10";
        String message = "InvalidIdentifierException: \'" + exp.split(" ")[1] + "\' is a reserved word";
        exception.expect(InvalidIdentifierException.class);
        exception.expectMessage(message);
        Assign.fromString(exp);
    }


}
