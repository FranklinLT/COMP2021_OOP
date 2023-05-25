package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.model.expressions.BooleanExpression;
import hk.edu.polyu.comp.comp2021.simple.model.expressions.IntegerExpression;
import hk.edu.polyu.comp.comp2021.simple.model.statements.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simple {
    //This part is the store part we use to store all the input of user.
    public static HashMap<String, IntegerExpression> int_exps;
    public static HashMap<String, BooleanExpression> bool_exps;
    public static HashMap<String, Variable<Boolean>> bool_vars;
    public static HashMap<String, Variable<Integer>> int_vars;
    public static HashMap<String, Executable> executables;
    public static HashMap<String, Program> programs;
    public static ArrayList<String> store_file;
    public static ArrayList<String> all_ref;
    public static HashSet<String> result;
    public static HashMap<String,HashSet<String>> break_point;
    public static String entry;
    public static boolean breakpoint_flag=false;//for break the program
    public static HashMap<String,HashSet<String>> before_instruments;
    public static HashMap<String,HashSet<String>> after_instruments;

    private static Simple instance;
    private Simple() {
        Simple.bool_exps = new HashMap<String, BooleanExpression>();
        Simple.int_vars = new HashMap<String, Variable<Integer>>();
        Simple.bool_vars = new HashMap<String, Variable<Boolean>>();
        Simple.executables = new HashMap<>();
        Simple.programs = new HashMap<String, Program>();
        Simple.int_exps = new HashMap<String, IntegerExpression>();
        Simple.store_file = new ArrayList<String>();
        Simple.all_ref = new ArrayList<String>();
        Simple.result = new HashSet<String>();
        Simple.break_point=new HashMap<>();
        Simple.before_instruments=new HashMap<>();
        Simple.after_instruments=new HashMap<>();
    }
    public static Simple getSimpleInstance(){
        if( instance == null){
            return new Simple();
        }
        return instance;
    }
    public static void reset(){
        instance = new Simple();
    }
    public static void addInstruction(String commandType, String instruction){
        try{
            if(instruction.split(" ").length > 1) {
                String ref = instruction.split(" ")[1];
                all_ref.add(ref);
                switch (commandType) {
                    case "vardef":
                        VarDef varDef = VarDef.fromString(instruction);
                        executables.put(ref, varDef);
                        break;
                    case "assign":
                        Assign assign = Assign.fromString(instruction);
                        executables.put(ref, assign);
                        break;
                    case "print":
                        PrintStatement print = PrintStatement.fromString(instruction);
                        executables.put(ref, print);
                        break;
                    case "while":
                        WhileLoop loop = WhileLoop.fromString(instruction);
                        executables.put(ref, loop);
                        break;
                    case "if":
                        IfStatement ifStatement = IfStatement.fromString(instruction);
                        executables.put(ref, ifStatement);
                        break;
                    case "block":
                        Block block = Block.fromString(instruction);
                        executables.put(ref, block);
                        break;
                    case "execute":
                        entry=instruction.split(" ")[1];
                        execute(instruction);
                        System.out.println();
                        break;
                    case "binexpr":
                        if (Utils.isIntegerExp(instruction)) {
                            IntegerExpression intExp = new IntegerExpression(instruction.substring(9 + ref.length()));
                            Utils.isValidRef(ref);
                            int_exps.put(ref, intExp);
                        } else if (Utils.isBooleanExp(instruction)) {
                            BooleanExpression booleanExp = new BooleanExpression(instruction.substring(9 + ref.length()));
                            Utils.isValidRef(ref);
                            bool_exps.put(ref, booleanExp);
                        }
                        break;
                    case "unexpr":
                        if (Utils.isIntegerExp(instruction)) {
                            IntegerExpression intExp = new IntegerExpression(instruction.substring(8 + ref.length()));
                            Utils.isValidRef(ref);
                            int_exps.put(ref, intExp);
                        } else if (Utils.isBooleanExp(instruction)) {
                            BooleanExpression booleanExp = new BooleanExpression(instruction.substring(8 + ref.length()));
                            Utils.isValidRef(ref);
                            bool_exps.put(ref, booleanExp);
                        }
                        break;
                    case "skip":
                        Skip skip = Skip.fromString(instruction);
                        executables.put(ref, skip);
                        break;
                    case "list":
                        list(ref);
                        print_list();
                        break;
                    case "program":
                        Program program = Program.fromString(instruction);
                        executables.put(ref, program);
                        break;
                    case "load":
                        load(ref, instruction.split(" ")[2]);
                        break;
                    case "store":
                        String path = instruction.substring(7 + ref.length());
                        store(ref, path);
                        break;
                    case "togglebreakpoint":
                        control_points(instruction);
                        break;
                    case "instrument":
                        set_instrument(instruction);
                        break;
                    case "debug":
                        String programName = instruction.split(" ")[1];
                        entry=programName;
                        if(!Debugger.hasStarted){
                            if( Simple.executables.get(programName) != null){
                                Executable pgrm = Simple.executables.get(programName);
                                if( pgrm instanceof Program){
                                    Debugger.hasStarted=true;
                                    pgrm.execute();
                                }
                                else {
                                    System.out.println("The statement can be executed as program");
                                }
                            }
                            else{
                                System.out.println("Program cannot be found!");
                            }
                        }
                        Debugger.isContinue = true;
                        break;
                    case "inspect":
                        inspect(instruction);
                        break;
                    default:
                        throw new IllegalArgumentException("The command is not recognised by the interpreter");
                }
            }
            else{
                throw new IllegalArgumentException("MissingArguments: The given command is not complete");
            }
        }
        catch(Exception e){
            System.err.println(e);
        }
    }

    public static void set_instrument(String instruction){
        String[] input=instruction.split(" ");
        for(String command:store_file){
            String[] tokens=command.split(" ");
            if(tokens[0].equals("program")&&tokens[1].equals(input[1])){
                if(input[3].equals("before")){
                    if(before_instruments.containsKey(tokens[1])){
                        before_instruments.get(tokens[1]).add(input[2]);
                        executables.get(input[2]).before_instrument(tokens[1],input[4]);
                    }
                    else{
                        HashSet<String> hashSet=new HashSet<>();
                        hashSet.add(input[2]);
                        before_instruments.put(tokens[1],hashSet);
                        executables.get(input[2]).before_instrument(tokens[1],input[4]);
                    }
                }
                else if(input[3].equals("after")){
                    if(after_instruments.containsKey(tokens[1])){
                        after_instruments.get(tokens[1]).add(input[2]);
                        executables.get(input[2]).after_instrument(tokens[1],input[4]);
                    }
                    else{
                        HashSet<String> hashSet=new HashSet<>();
                        hashSet.add(input[2]);
                        after_instruments.put(tokens[1],hashSet);
                        executables.get(input[2]).after_instrument(tokens[1],input[4]);
                    }
                }
                else{
                    System.out.println("Wrong command");
                }
            }
        }
    }

    public static void control_points(String instruction){
        String[] input=instruction.split(" ");
        for(String command:store_file){
            String[] tokens=command.split(" ");
            if(tokens[0].equals("program")&&tokens[1].equals(input[1])){
                if(break_point.containsKey(tokens[1])){
                    if(break_point.get(tokens[1]).contains(input[2])){
                        break_point.get(tokens[1]).remove(input[2]);
                        return;
                    }
                    break_point.get(tokens[1]).add(input[2]);
                }
                else{
                    HashSet<String> hashSet=new HashSet<>();
                    hashSet.add(input[2]);
                    break_point.put(tokens[1],hashSet);
                }
            }
        }
    }

    public static void store(String ref, String path){
        result.clear();
        list(ref);
        try {
            FileWriter fwrite = new FileWriter(path);
            // writing the content into the FileOperationExample.txt file
            for(int i = 0; i < store_file.size(); i++){
                if(result.contains(store_file.get(i))) {
                    fwrite.write(store_file.get(i) + "\n");
                }
            }
            // Closing the stream
            fwrite.close();
        }
        catch (IOException e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    public static void load(String path, String new_name){
        int_vars.clear();
        bool_vars.clear();
        bool_exps.clear();
        int_exps.clear();
        executables.clear();
        programs.clear();
        store_file.clear();
        result.clear();
        break_point.clear();
        Simple simple=new Simple();

        try {
            // Create f1 object of the file to read data
            File f1 = new File(path);
            Scanner dataReader = new Scanner(f1);
            while (dataReader.hasNextLine()) {
                String instruction = dataReader.nextLine();
                store_file.add(instruction);
            }
            dataReader.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println("Unexpected error occurred!");
            exception.printStackTrace();
        }

        String[] temp = store_file.get(store_file.size() - 1).split(" ");
        temp[1] = new_name;
        store_file.set(store_file.size() - 1, temp[0] + " " + temp[1] + " " + temp[2]);
        for(String command : store_file){
            String commandType=command.split(" ")[0];
            addInstruction(commandType,command);
        }
    }

    public static void list(String ref){
        String[] array=new String[store_file.size()];
        Queue<String> queue= new LinkedList<>();
        int index=0;
        HashSet<String> contain=new HashSet<>();
        String label="";
        for(String command:store_file){
            String[] temp=command.split(" ");
            array[index++]=temp[1];
            if(temp[1].equals(ref)&&temp[0].equals("program")){
                label=command;}
        }
        queue.add(label);
        while(!queue.isEmpty()){
            String[] test=queue.remove().split(" ");
            for(int k=store_file.size()-1;k>=0;k--){
                String[] temp=store_file.get(k).split(" ");
                for(int i=0;i<temp.length;i++){
                    if(is_label(array,temp[i])){
                        for(int j=2;j<test.length;j++){
                            if(temp[i].equals(test[j])){
                                result.add(store_file.get(k));
                                if(!contain.contains(store_file.get(k))){
                                    queue.add(store_file.get(k));
                                    contain.add(store_file.get(k));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void print_list(){
        for(String s:store_file){
            if (result.contains(s)) {
                System.out.println(s);
            }
        }
    }

    public static boolean is_label(String[] labels,String lab){
        for(String element : labels){
            if(lab.equals(element)){
                return true;
            }
        }
        return false;
    }

    public static void execute(String instruction){
        String expRef = instruction.split(" ")[1];
        Executable exp = executables.get(expRef);
        if(exp != null){
            exp.execute();
        }
        else{
            throw new IllegalArgumentException("Invalid expression reference");
        }
    }

    public static void inspect(String instruction){
        if(!Debugger.hasStarted){
            System.out.println("Not in debug mode");
            return;
        }
        String varname = instruction.split(" ")[2];
        if(Simple.int_vars.get(varname) != null){
            System.out.println("<" +Simple.int_vars.get(varname).getvalue()+">");
        }
        else if(Simple.bool_vars.get(varname)!=null){
            System.out.println("<"+Simple.bool_vars.get(varname).getvalue()+">");
        }
        else {
            System.out.println("Variable is not defined");
        }

    }
}

















