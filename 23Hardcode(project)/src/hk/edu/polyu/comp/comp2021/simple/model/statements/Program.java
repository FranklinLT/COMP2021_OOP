package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;

public class Program implements Executable {
    String entry;
    String point;
    public Program(String entry,String point){
        this.entry=entry;
        this.point=point;
    }
    @Override
    public void execute(){
        if(Simple.executables.containsKey(entry)){
            Simple.executables.get(entry).execute();
            Debugger.hasStarted=false;
        }
    }
    @Override
    public void before_instrument(String program_name,String instrument){
    }
    @Override
    public void after_instrument(String program_name,String instrument){
    }
    public static Program fromString(String expString){
        String[] exp=expString.split(" ");
        String exp1=exp[2];
        return new Program(exp1,exp[1]);
    }
}