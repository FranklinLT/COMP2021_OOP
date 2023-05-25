package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;

import java.util.HashMap;


public class IfStatement implements Executable{
    String condition;
    String trueStatement;
    String falseStatement;
    String point;
    public HashMap<String,String> b_instrument;
    public HashMap<String,String> a_instrument;

    public String getCondition() {
        return condition;
    }

    public String getTrueStatement() {
        return trueStatement;
    }

    public String getFalseStatement() {
        return falseStatement;
    }

    public String getPoint() {
        return point;
    }

    public IfStatement(String condition, String trueStatement, String falseStatement, String point){
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
        this.point=point;
        this.b_instrument=new HashMap<>();
        this.a_instrument=new HashMap<>();
    }
    @Override
    public void after_instrument(String program_name,String instrument){
        this.a_instrument.put(program_name,instrument);
    }
    @Override
    public void before_instrument(String program_name,String instrument){
        this.b_instrument.put(program_name,instrument);
    }
    @Override
    public void execute(){
        Executable exp;
        Utils.check_before_instrument(this.point,b_instrument);
        Utils.check_Bp(this.point);
        if(Utils.getBooleanValue(condition)){
            exp = Simple.executables.get(trueStatement);
        }
        else{
            exp = Simple.executables.get(falseStatement);
        }
        exp.execute();
        Utils.check_after_instrument(this.point,a_instrument);
    }

    public static IfStatement fromString(String instruction) throws SyntaxErrorException {
        String[] tokens = instruction.split(" ");
        try {
            if(!isValid(tokens)) return null;
        }
        catch (SyntaxErrorException e) {
            throw e;
        }
        String condition = tokens[2];
        String trueStatement = tokens[3];
        String falseStatement = tokens[4];
        return new IfStatement(condition, trueStatement, falseStatement,tokens[1]);
    }

    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        if(tokens.length != 5){
            throw new InvalidFormatException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens");
        }
        try {
            // check statement reference
            if (!Utils.isValidRef(tokens[1])) {
                return false;
            }
            String trueStatement = tokens[3];
            String falseStatement = tokens[4];
            if(!Simple.executables.containsKey(trueStatement)){
                throw new InvalidIdentifierException("UndeclaredStatementError: \'"+trueStatement+"\' is not declared as executable statement in the scope. IfStatement only allows executable statements");
            }
            if(!Simple.executables.containsKey(falseStatement)){
                throw new InvalidIdentifierException("UndeclaredStatementError: \'"+falseStatement+"\' is not declared as executable statement in the scope. IfStatement only allows executable statements");
            }


        }
        catch (SyntaxErrorException e){
            throw e;
        }
        return true;
    }

}
