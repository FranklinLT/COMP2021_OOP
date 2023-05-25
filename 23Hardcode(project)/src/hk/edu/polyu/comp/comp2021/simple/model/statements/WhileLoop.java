package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;

import java.util.Arrays;
import java.util.HashMap;

public class WhileLoop implements Executable{
    String[] statements;
    String condition;

    public String[] getStatements() {
        return statements;
    }

    public String getCondition() {
        return condition;
    }

    String point;
    public HashMap<String,String> b_instrument;
    public HashMap<String,String> a_instrument;
    @Override
    public void before_instrument(String program_name,String instrument){
        this.b_instrument.put(program_name,instrument);
    }
    public WhileLoop(String condition, String[] statements,String point){
        this.condition = condition;
        this.statements = statements;
        this.point=point;
        this.b_instrument=new HashMap<>();
        this.a_instrument=new HashMap<>();
    }
    @Override
    public void after_instrument(String program_name,String instrument){
        this.a_instrument.put(program_name,instrument);
    }
    @Override
    public void execute(){
        Utils.check_before_instrument(this.point,b_instrument);
        Utils.check_Bp(this.point);
        while(Utils.getBooleanValue(condition)){
            for(String expref: statements){
                Executable exp = Simple.executables.get(expref);
                exp.execute();
            }
        }
        Utils.check_after_instrument(this.point,a_instrument);
    }

    public static WhileLoop fromString(String instruction) throws SyntaxErrorException {
        String tokens[] = instruction.split(" ");
        try {
            if(!isValid(tokens)) return null;
        }
        catch (SyntaxErrorException e) {
            throw e;
        }
        String condition = tokens[2];
        String[] statements = Arrays.copyOfRange(tokens,3, tokens.length);

        return new WhileLoop(condition, statements,tokens[1]);
    }

    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        if(tokens.length < 4){
            throw new InvalidFormatException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens");
        }
        try {
            // check statement reference
            if (!Utils.isValidRef(tokens[1])) {
                return false;
            }
            // Check if the specified expressions are valid executable statements
            String[] statements = Arrays.copyOfRange(tokens,3, tokens.length);
            for(String statement : statements){
                if(!Simple.executables.containsKey(statement)){
                    throw new InvalidIdentifierException("UndeclaredStatementError: \'"+statement+"\' is not declared as executable statement in the scope. WhileLoop only allows executable statements");
                }
            }
        }
        catch (SyntaxErrorException e){
            throw e;
        }
        return true;
    }

}
