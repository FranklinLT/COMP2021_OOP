package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.expressions.BooleanExpression;
import hk.edu.polyu.comp.comp2021.simple.model.expressions.IntegerExpression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import static junit.framework.TestCase.assertEquals;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleTest {
    String exp1="3 + 4";
    String Exp1="exp1";
    String exp2="~ 2";
    String Exp2="exp2";
    String exp3="20 + exp2";
    String Exp3="exp3";
    IntegerExpression int1;
    BooleanExpression bool1;
    Simple s;
    String exp4="exp2 >= exp1";
    String Exp4="Exp4";
    @Before
    public  void before(){
        //Integer part
        s = Simple.getSimpleInstance();
    }
    @After
    public void reset(){
        Simple.reset();
    }

    @Test
    public void testSimpleConstructor(){
        assertTrue(Simple.int_vars instanceof HashMap);
    }

    @Test
    public void test1(){
        String vardef1 = "vardef vardef1 int x 0";
        String binexpr1 = "binexpr exp1 x % 2";
        String binexpr2 = "binexpr exp2 exp1 == 0";
        String print1 = "print print1 x";
        String skip1 = "skip skip1";
        String if1 = "if if1 exp2 print1 skip1";
        String binexpr3 = "binexpr exp3 x + 1";
        String assign1 = "assign assign1 x exp3";
        String block1 = "block block1 if1 assign1";
        String binexpr4 = "binexpr exp4 x <= 10";
        String while1 = "while while1 exp4 block1";
        String block2 = "block block2 vardef1 while1";
        String program1 = "program printeven block2";
        String execute1 = "execute printeven";


        Simple.addInstruction(vardef1.split(" ")[0], vardef1);
        assertTrue(Simple.executables.containsKey(vardef1.split(" ")[1]));

        Simple.addInstruction(binexpr1.split(" ")[0], binexpr1);
        assertTrue(Simple.int_exps.containsKey(binexpr1.split(" ")[1]));

        Simple.addInstruction(binexpr2.split(" ")[0], binexpr2);
        assertTrue(Simple.bool_exps.containsKey(binexpr2.split(" ")[1]));

        Simple.addInstruction(print1.split(" ")[0], print1);
        assertTrue(Simple.executables.containsKey(print1.split(" ")[1]));

        Simple.addInstruction(skip1.split(" ")[0], skip1);
        assertTrue(Simple.executables.containsKey(skip1.split(" ")[1]));

        Simple.addInstruction(if1.split(" ")[0], if1);
        assertTrue(Simple.executables.containsKey(if1.split(" ")[1]));

        Simple.addInstruction(binexpr3.split(" ")[0], binexpr3);
        assertTrue(Simple.int_exps.containsKey(binexpr3.split(" ")[1]));

        Simple.addInstruction(assign1.split(" ")[0], assign1);
        assertTrue(Simple.executables.containsKey(assign1.split(" ")[1]));

        Simple.addInstruction(block1.split(" ")[0], block1);
        assertTrue(Simple.executables.containsKey(block1.split(" ")[1]));

        Simple.addInstruction(binexpr4.split(" ")[0], binexpr4);
        assertTrue(Simple.bool_exps.containsKey(binexpr4.split(" ")[1]));

        Simple.addInstruction(while1.split(" ")[0], while1);
        assertTrue(Simple.executables.containsKey(while1.split(" ")[1]));

        Simple.addInstruction(block2.split(" ")[0], block2);
        assertTrue(Simple.executables.containsKey(block2.split(" ")[1]));

        Simple.addInstruction(program1.split(" ")[0], program1);
        assertTrue(Simple.executables.containsKey(program1.split(" ")[1]));

        Simple.addInstruction(execute1.split(" ")[0], execute1);

        assertEquals((Integer) 11, Simple.int_vars.get("x").getvalue());

    }
}