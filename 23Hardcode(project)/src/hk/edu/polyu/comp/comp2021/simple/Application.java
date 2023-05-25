package hk.edu.polyu.comp.comp2021.simple;
import hk.edu.polyu.comp.comp2021.simple.model.Simple;

import java.util.Scanner;

public class Application {
    public static void main(String[] args){
        Simple simple = Simple.getSimpleInstance();
        Scanner sc = new Scanner(System.in);
        String instruction;
        boolean isStop = false;
        while(!isStop){
            instruction = sc.nextLine();
            String commandType = instruction.split(" ")[0];
            if(commandType.equals("quit")){
                isStop = true;
            }
            else {
                Simple.addInstruction(commandType, instruction);
                if(commandType != "load"){
                    Simple.store_file.add(instruction);
                }
            }
        }
    }
}


