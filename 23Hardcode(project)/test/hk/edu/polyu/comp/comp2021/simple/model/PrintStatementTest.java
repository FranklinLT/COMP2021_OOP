package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.statements.PrintStatement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class PrintStatementTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    Simple simple;
    @Before
    public void prepare() {
        simple = Simple.getSimpleInstance();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void out() throws SyntaxErrorException {
        PrintStatement print = PrintStatement.fromString("print print1 123");
        print.execute();
        assertEquals("[123]", outContent.toString());
    }

}
