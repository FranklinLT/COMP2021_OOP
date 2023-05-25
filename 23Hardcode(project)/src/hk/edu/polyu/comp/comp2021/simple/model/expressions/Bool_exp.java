package hk.edu.polyu.comp.comp2021.simple.model.expressions;

import java.util.InputMismatchException;

public enum Bool_exp {
    GreaterThan(">"), GreaterOrEqual(">="), SmallerThan("<"), SmallerOrEqual("<="), Equal("=="),
    Unequal("!="), And("&&"), Or("||"),NOT("!");
    private String s;
    Bool_exp(String s){
        this.s=s;
    }
    public static Bool_exp fromString(String symbol){
        for(Bool_exp operator: Bool_exp.values()){
            if(operator.get_expression().equals(symbol)){
                return operator;
            }
        }
        throw new InputMismatchException("cannot recognize the operator");
    }
    public String get_symbol(){
        return this.s;
    }
    public String get_expression(){
        return s;
    }
}