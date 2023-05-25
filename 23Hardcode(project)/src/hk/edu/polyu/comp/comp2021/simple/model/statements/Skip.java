package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidFormatException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;

import java.util.HashMap;

public class Skip implements Executable
{
    String point;
    public HashMap<String,String> b_instrument;
    public HashMap<String,String> a_instrument;
    public Skip(String point){
        this.point=point;
        this.b_instrument=new HashMap<>();
        this.a_instrument=new HashMap<>();
    }

    public String getPoint() {
        return point;
    }

    @Override
    public void execute(){
        Utils.check_before_instrument(this.point,b_instrument);
        Utils.check_Bp(this.point);
        Utils.check_after_instrument(this.point,a_instrument);
    }
    @Override
    public void before_instrument(String program_name,String instrument){
        this.b_instrument.put(program_name,instrument);
    }
    @Override
    public void after_instrument(String program_name,String instrument){
        this.a_instrument.put(program_name,instrument);
    }
    public static Skip fromString(String instruction) throws SyntaxErrorException {
        String[] tokens=instruction.split(" ");
        try{
            isValid(tokens);
        }
        catch(SyntaxErrorException e){
            throw e;
        }
        return new Skip(tokens[1]);
    }
    public static boolean isValid(String[] tokens) throws SyntaxErrorException {
        if(tokens.length != 2){
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