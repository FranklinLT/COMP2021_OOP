package hk.edu.polyu.comp.comp2021.simple.model.statements;

public interface Executable {
    public void execute();
    public void before_instrument(String program_name,String instrument);
    public void after_instrument(String program_name,String instrument);
//    public boolean isValid(String[] tokens) throws SyntaxErrorException;
}
