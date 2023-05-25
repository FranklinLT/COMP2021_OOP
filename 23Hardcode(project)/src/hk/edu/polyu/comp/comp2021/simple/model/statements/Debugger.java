package hk.edu.polyu.comp.comp2021.simple.model.statements;

import hk.edu.polyu.comp.comp2021.simple.model.Simple;

public class Debugger{
    String program_name;
    public static boolean isContinue = true;
    public static boolean hasStarted = false;
    public Debugger(String program_name){
        this.program_name=program_name;
    }

    public static void execute(String expRef){
        Executable exp= Simple.executables.get(expRef);
        if(exp!=null&&!Simple.break_point.get(Simple.entry).contains(expRef)){
            exp.execute();
            if(Simple.breakpoint_flag){
                return;
            }
        }
        else{
            throw new IllegalArgumentException();
        }
        Simple.break_point.get(Simple.entry).add(expRef);
    }


}
