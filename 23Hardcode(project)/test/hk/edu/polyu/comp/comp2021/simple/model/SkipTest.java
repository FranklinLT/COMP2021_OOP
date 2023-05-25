package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.Skip;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SkipTest {
    Simple simple;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void prepare(){
        simple = Simple.getSimpleInstance();
    }

    @Test
    public void testFromString() throws SyntaxErrorException {
        String exp = "skip skip1";
        Skip skip = Skip.fromString(exp);
        assertEquals("skip1", skip.getPoint());
    }

    @Test
    public void testFromStringWithMissingRef() throws SyntaxErrorException {
        String exp = "skip";
        String message = "InvalidFormat: The command is malformed! The statement has unexpected number of tokens";
        exception.expect(InvalidFormatException.class);
        exception.expectMessage(message);
        Skip.fromString(exp);
    }
    @Test
    public void testExecute() throws SyntaxErrorException {
        String exp = "skip skip1";
        Skip skip = Skip.fromString(exp);
        skip.execute();
    }
}
