package hk.edu.polyu.comp.comp2021.simple.model.expressions;

//first pass the operator +-*/ to initialize the class, then pass two leftOperand and rightOperand to  function calculate get the result
public enum Int_exp {
    PLUS("+"),SUBTRACT("-"),MULTIPLY("*"),DIVISION("/"),REMAINDER("%"),U_PLUS("#"),U_MINUS("~");
    private String s;
    Int_exp(String s){
        this.s=s;
    }
    String get_expression(){
        return s;
    }
    public String get_Symbol(){
        return this.s;
    }
    public static Int_exp fromString(String symbol){
        for(Int_exp operator: Int_exp.values()){
            if(operator.get_expression().equals(symbol)){
                return operator;
            }
        }
        System.exit(1);
        return null;
    }
}