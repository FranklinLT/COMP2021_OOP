package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;

import java.util.HashMap;

public class Assign implements Executable{
    private String varName;
    private String rawVal;
    private String point;
    public HashMap<String,String> b_instrument;
    public HashMap<String,String>  a_instrument;
    public Assign(String varName, String rawVal,String point) {
        this.varName = varName;
        this.rawVal = rawVal;
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
    public String getVarName() {
        return varName;
    }

    public String getRawVal() {
        return rawVal;
    }
    @Override
    public void execute(){
        Utils.check_before_instrument(this.point,b_instrument);
        Utils.check_Bp(this.point);
        if(Simple.int_vars.get(varName) != null){
            Integer actualValue = Utils.getIntValue(rawVal);
            Simple.int_vars.get(varName).setvalue(actualValue);
        }
        else if (Simple.bool_vars.get(varName) != null){
            Boolean actualValue = Utils.getBooleanValue(rawVal);
            Simple.bool_vars.get(varName).setvalue(actualValue);
        }
        else{
            throw new RuntimeException(varName + "variable is not declared in the scope.");
        }
        Utils.check_after_instrument(this.point,a_instrument);
    }

    public static Assign fromString(String expString) throws SyntaxErrorException {
        String[] tokens = expString.split(" ");
        try{
            isValid(tokens);
        }
        catch(SyntaxErrorException e){
            throw e;
        }
        String varName = tokens[2];
        String rawVal = tokens[3];
        return new Assign(varName, rawVal,tokens[1]);
    }

    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        if(tokens.length != 4){
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
