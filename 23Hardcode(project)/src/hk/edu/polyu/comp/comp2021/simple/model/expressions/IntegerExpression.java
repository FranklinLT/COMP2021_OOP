package hk.edu.polyu.comp.comp2021.simple.model.expressions;

import hk.edu.polyu.comp.comp2021.simple.model.statements.Utils;

public class IntegerExpression{
    private String command;
    private String[] substrings;
    public Int_exp operator;

    public IntegerExpression(String command){
        this.command = command;
        fromString();
        operator = Int_exp.fromString(substrings[substrings.length - 2]);
    }

    public int recurCalculate() {
        return calculate(this.substrings);
    }
    public int calculate(String[] substrings) {
        if (substrings.length == 3) {
            Integer left = Utils.getIntValue(substrings[0]);
            Integer right = Utils.getIntValue(substrings[2]);
            switch (this.operator) {
                case PLUS:
                    return left + right;
                case SUBTRACT:
                    return left - right;
                case DIVISION:
                    return left / right;
                case MULTIPLY:
                    return left * right;
                case REMAINDER:
                    return left % right;
                default:
                    throw new ArithmeticException();
            }
        }

        else if(substrings.length==2){
            Integer operand = Utils.getIntValue(substrings[1]);
            switch (this.operator) {
                case U_PLUS:
                    return +operand; //'+' MAYBE CAN REMOVE
                case U_MINUS:
                    return -operand;
                default:
                    throw new ArithmeticException();
            }

        }
        else{
            throw new IllegalArgumentException("InvalidFormat: The command is malformed! The statement has unexpected number of tokens.");
        }
    }

    //This function receive the command and split it to 2 or 3 parts and then add them to ArrayList[substrings]
    public void fromString()
    {
        this.substrings =this.command.split(" ");
    }


}
