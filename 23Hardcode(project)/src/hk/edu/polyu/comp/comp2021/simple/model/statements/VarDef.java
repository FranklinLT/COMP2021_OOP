package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.MissingKeywordException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;

import java.util.Arrays;
import java.util.HashMap;

import static hk.edu.polyu.comp.comp2021.simple.model.statements.Utils.isValidName;

public class VarDef implements Executable{
    String varName;
    String type;
    String value;
    String point;
    public VarDef(String varName, String type, String value,String point) {
        this.varName = varName;
        this.type = type;
        this.value = value;
        this.point=point;
        this.b_instrument=new HashMap<>();
        this.a_instrument=new HashMap<>();
    }
    public HashMap<String,String> b_instrument;
    public HashMap<String,String> a_instrument;

    public String getVarName() {
        return varName;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
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
        Utils.check_before_instrument(this.point,b_instrument);
        Utils.check_Bp(this.point);
        if(type.equals("int")){
            Integer actualValue = Utils.getIntValue(value);
            //System.out.println("value being inserted");
            Simple.int_vars.put(varName, new Variable<Integer>(actualValue));
        }
        else if (type.equals("bool")){
            Boolean actualValue = Utils.getBooleanValue(value);
            Simple.bool_vars.put(varName, new Variable<Boolean>(actualValue));
        }
        else {
            throw new IllegalArgumentException( value + "is not defined in the scope");
        }
        Utils.check_after_instrument(this.point,a_instrument);
    }
    public static VarDef fromString( String expString) throws SyntaxErrorException {
        String[] tokens = expString.split(" ");

        try {
            if(!isValid(tokens)) return null;
        }
        catch (SyntaxErrorException e) {
            throw e;
        }
        String type = tokens[2];
        String varName = tokens[3];
        String value = tokens[4];
        return new VarDef(varName, type, value, tokens[1]);

    }

    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        //command is misformatted
        if(tokens.length != 5){
            throw new InvalidFormatException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens.");
        }
        try {
            // check statement reference
            if (!Utils.isValidRef(tokens[1])) {
                return false;
            }
            // check variable name
            if (!VarDef.verifyVarname(tokens[3])) {
                return false;
            }
        }
        catch (SyntaxErrorException e){
            throw e;
        }

        String type = tokens[2];

        if( !(type.equals("int") || type.equals("bool")) ) {
            throw new MissingKeywordException("\'" + type + "\'" + "is not recognise at a datatype. Datatype can only be \'int\' or \'bool\'");
        }
        return true;
    }
    private static boolean verifyVarname(String varname) throws InvalidIdentifierException {
        try{
            isValidName(varname);
        }
        catch (SyntaxErrorException e){
            throw e;
        }
        if(Arrays.asList(Utils.keywords).contains(varname)){
            String message = "\'" + varname + "\' is a reserved word";
            throw new InvalidIdentifierException("InvalidIdentifierException: " + message);
        }
        if(Simple.bool_vars.containsKey(varname) || Simple.int_vars.containsKey(varname)){
            String message = "\'" + varname + "\' is already declared.";
            throw new InvalidIdentifierException("InvalidIdentifierException: " + message);
        }
        return true;
    }
}
