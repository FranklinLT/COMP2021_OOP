package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.InvalidIdentifierException;
import hk.edu.polyu.comp.comp2021.simple.model.exceptions.SyntaxErrorException;
import hk.edu.polyu.comp.comp2021.simple.model.expressions.Bool_exp;
import hk.edu.polyu.comp.comp2021.simple.model.expressions.Int_exp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Utils {
    public static final int MAX_INTEGER=99999;
    public static final int MIN_INTEGER=-99999;
    public static final String[]  keywords = {
            "int", "bool", "after", "before", "vardef", "assign", "binexpr", "unexpr", "if", "while", "block", "print", "program", "debug", "togglebreakpoint",
            "instrument", "skip", "execute", "list", "load", "store", "inspect", "false", "true"
    };

    public static int getIntValue( String value){
        Integer actualValue;
        if( Simple.int_vars.get(value) != null){
            actualValue = Simple.int_vars.get(value).getvalue();
        }
        else if( Simple.int_exps.get(value) != null){
            actualValue = Simple.int_exps.get(value).recurCalculate();
        }
        else{
            if(!isValidIntegerString(value)){
                throw new IllegalArgumentException("the integer value is invalid");
            }
            if(is_over_flow(value)){
                if(value.charAt(0)=='-'){
                    return MIN_INTEGER;
                }
                else {
                    return MAX_INTEGER;
                }
            }
            actualValue = Integer.valueOf(value);
        }
        return actualValue;
    }

    private static boolean is_over_flow(String value){
        if(value.charAt(0)=='-'){
            if(value.length()>6){
                return true;
            }
        }
        else if(value.length()>5){
            return true;
        }
        return false;
    }
    public static boolean getBooleanValue(String value){
        Boolean actualValue;
        if( Simple.bool_vars.get(value) != null){
            actualValue = Simple.bool_vars.get(value).getvalue();
        }
        else if( Simple.bool_exps.get(value) != null){
            actualValue = Simple.bool_exps.get(value).recurCalculate();
        }
        else{
            if(!(value.equals("true") || value.equals("false"))) {
                throw new IllegalArgumentException(value + "Statement cannot be parsed to boolean");
            }
            actualValue = Boolean.parseBoolean(value);
        }
        return actualValue;
    }

    public static boolean isInteger(String value) throws NumberFormatException{
        if(Simple.int_exps.get(value) != null || Simple.int_vars.get(value) != null){
            return true;
        }
        try {
            Integer.valueOf(value);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }

    }

    public static boolean isBoolean(String value) throws NumberFormatException{
        if(Simple.bool_exps.get(value) != null || Simple.bool_vars.get(value) != null){
            return true;
        }
        return value.equals("true") || value.equals("false") ? true : false;
    }

    public static boolean isIntegerExp(String instruction){
        String[] temp = instruction.split(" ");
        String temp_operator = temp[temp.length - 2];
        for(Int_exp element : Int_exp.values()){
            if(element.get_Symbol().equals(temp_operator)){
                return true;
            }
        }
        return false;
    }

    public static boolean isBooleanExp(String instruction){
        String[] temp = instruction.split(" ");
        String temp_operator = temp[temp.length - 2];
        for(Bool_exp element : Bool_exp.values()){
            if(element.get_symbol().equals(temp_operator)){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidIntegerString(String str){
        if(str == null || str.length() == 0) return false;

        for(int i= 0; i<str.length(); i++){
            char c = str.charAt(i);
            if(c < '0' || c > '9') {
                if( i>0 ) return false;
                else if ( c!='-' && c!= '+') return  false;
            }
        }
        return true;
    }

    public static void check_Bp(String point){
        Scanner sc = new Scanner(System.in);
        if(Simple.break_point.get(Simple.entry)==null){
            return;
        }
        if( Simple.break_point.get(Simple.entry).contains(point)) {
            Debugger.isContinue = false;
            while (!Debugger.isContinue) {
                String instruction = sc.nextLine();
                String commandType = instruction.split(" ")[0];
                Simple.addInstruction(commandType, instruction);
            }
        }
    }

    public static void check_before_instrument(String point, HashMap before){
        if(Simple.before_instruments.get(Simple.entry)==null){
            return;
        }
        if(Simple.before_instruments.get(Simple.entry).contains(point)){
            System.out.print("{"+before.get(Simple.entry)+"}");
        }
    }
    public static void check_after_instrument(String point, HashMap after){
        if(!Simple.after_instruments.containsKey(Simple.entry)){
            return;
        }
        if(Simple.after_instruments.get(Simple.entry).contains(point)){
            System.out.print("{"+after.get(Simple.entry)+"}");
        }
    }

    public static boolean isValidRef(String ref) throws SyntaxErrorException {

        try{
            isValidName(ref);
        }
        catch (SyntaxErrorException e){
            throw e;
        }

        if(Arrays.asList(keywords).contains(ref)){
            throw new InvalidIdentifierException("InvalidIdentifierException: \'" + ref + "\' is a reserved word");
        }

        if(
                Simple.executables.containsKey(ref) ||
                Simple.bool_exps.containsKey(ref) ||
                Simple.int_exps.containsKey(ref)
        )
        {
            throw new InvalidIdentifierException("InvalidIdentifierException: Reference,  \'"+ ref + "\' has already been declared");
        }
        return true;
    }
    public static Boolean isValidName(String name) throws InvalidIdentifierException {
        if(name.length() >8){
            throw new InvalidIdentifierException("InvalidIdentifierException: Expression/Statement reference,  \'"+ name +"\' exceeded maximum length! Expression/Statement labels or references should be less than 8 characters.");
        }

        if(name.charAt(0) >='0'&& name.charAt(0)<='9') {
            throw new InvalidIdentifierException("InvalidIdentifierException: Expression/Statement reference, \'" + name + "\' cannot be started by a number.");
        }
        return true;
    }

}
