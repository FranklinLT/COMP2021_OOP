package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.VarDef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VardefTest {
    Simple simple;
    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
;    }
    @After
    public void reset(){
        Simple.reset();
    }
    @Test
    public void testFromString() throws SyntaxErrorException {
        VarDef varDef = VarDef.fromString("vardef varded1 int x 100");
        assertFalse(varDef == null);
        assertEquals("int",varDef.getType());
        assertEquals("x", varDef.getVarName());
        assertEquals("100", varDef.getValue());

    }
    @Test
    public void testExecute() throws SyntaxErrorException {
        VarDef varDef = VarDef.fromString("vardef varded1 int x 100");
        varDef.execute();
        assertFalse(Simple.int_vars.get(varDef.getVarName()) == null);
        assertEquals((Integer) 100, Simple.int_vars.get(varDef.getVarName()).getvalue());
    }
}
