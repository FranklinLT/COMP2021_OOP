package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;

import java.util.HashMap;

public class PrintStatement implements Executable{
    String statement;
    private String point;
    public HashMap<String,String> b_instrument;
    public HashMap<String,String> a_instrument;
    public PrintStatement(String statement,String point){
        this.statement = statement;
        this.point=point;
        this.b_instrument=new HashMap<>();
        this.a_instrument=new HashMap<>();
    }
    @Override
    public void before_instrument(String program_name,String instrument){
        this.b_instrument.put(program_name,instrument);
    }
    @Override
    public void after_instrument(String program_name,String instrument){
        this.a_instrument.put(program_name,instrument);
    }
    @Override
    public void execute(){
        Utils.check_Bp(this.point);
        Utils.check_before_instrument(this.point,b_instrument);
        if(Utils.isBoolean(this.statement)){
            System.out.print("["+ Utils.getBooleanValue(statement) +"]");
        }
        else if (Utils.isInteger(statement)) {
            System.out.print("[" + Utils.getIntValue(statement) + "]");
        }
        else{
            throw new IllegalArgumentException(statement+ " is undefined in the scope");
        }
        Utils.check_after_instrument(this.point,a_instrument);
    }
    public static PrintStatement fromString(String instruction) throws SyntaxErrorException {
        String[] tokens = instruction.split(" ");
        try {
            if(!isValid(tokens)) return null;
        }
        catch (SyntaxErrorException e) {
            throw e;
        }
        String statement = tokens[2];
        return new PrintStatement(statement,tokens[1]);
    }
    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        if(tokens.length != 3){
            throw new InvalidFormatException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens");
        }
        try {
            // check statement reference
            if (!Utils.isValidRef(tokens[1])) {
                return false;
            }
        }
        catch (SyntaxErrorException e){
            throw e;
        }
        return true;
    }


}
