package hk.edu.polyu.comp.comp2021.simple.model.expressions;

import hk.edu.polyu.comp.comp2021.simple.model.statements.Utils;

public class BooleanExpression{
    private String command;
    private String[] substrings;
    Bool_exp operator;

    public BooleanExpression(String command){
        this.command = command;
        fromString();
        operator = Bool_exp.fromString(get_substring()[get_substring().length - 2]);
    }

    public String[] get_substring(){
        return substrings;
    }

    public boolean recurCalculate(){
        return calculate(this.substrings);
    }
    public boolean calculate(String[] substrings)
    {
        if (substrings.length == 3) {
            if(get_substring()[get_substring().length - 2].equals(">") || get_substring()[get_substring().length - 2].equals(">=") ||
                    get_substring()[get_substring().length - 2].equals("<")  || get_substring()[get_substring().length - 2].equals("<=")) {

                Integer left = Utils.getIntValue(substrings[0]);
                Integer right = Utils.getIntValue(substrings[2]);
                switch (this.operator){
                    case GreaterThan:
                        return left > right;
                    case GreaterOrEqual:
                        return left >= right;
                    case SmallerThan:
                        return left < right;
                    case SmallerOrEqual:
                        return left <= right;
                    default:
                        throw new ArithmeticException();
                }

            }

            else if(get_substring()[get_substring().length - 2].equals("&&") || get_substring()[get_substring().length - 2].equals("||")){

                Boolean left = Utils.getBooleanValue(substrings[0]);
                Boolean right = Utils.getBooleanValue(substrings[2]);
                switch (this.operator){
                    case And:
                        return left && right;
                    case Or:
                        return left || right;
                    default:
                        throw new ArithmeticException();
                }

            }

            else{
                if(Utils.isInteger(substrings[0]) && Utils.isInteger(substrings[2])){
                    Integer left = Utils.getIntValue(substrings[0]);
                    Integer right = Utils.getIntValue(substrings[2]);
                    switch (this.operator){
                        case Equal:
                            return left.equals(right);
                        case Unequal:
                            return !(left.equals(right));
                        default:
                            throw new ArithmeticException();
                    }
                }
                else if( Utils.isBoolean((substrings[0])) && Utils.isBoolean(substrings[2])){
                    Boolean left = Utils.getBooleanValue(substrings[0]);
                    Boolean right = Utils.getBooleanValue(substrings[2]);
                    switch (this.operator){
                        case Equal:
                            return left.equals(right);
                        case Unequal:

                            return !(left.equals(right));
                        default:
                            throw new ArithmeticException();
                    }

                }
                else{
                    throw new ArithmeticException("Operations not defined on the give arguments");
                }

            }
        }
        else if(substrings.length == 2){
            Boolean operand=Utils.getBooleanValue(substrings[1]);
            switch (this.operator){
                case NOT:
                    return !operand;
                default:
                    throw new ArithmeticException();
            }
        }
        else {
            throw new IllegalArgumentException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens.");
        }
    }

    //This function receive the command and split it to 2 or 3 parts and then add them to ArrayList[get_substring()]
    public void fromString()
    {
        this.substrings =this.command.split(" ");
    }
}
