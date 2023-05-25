package hk.edu.polyu.comp.comp2022.assignment1;

public class LDLettersRemoval {

    public static String removeLDLetters(String str){
        // TODO: Add your code here
        if(str.length() == 1) return str;

        char[] string = str.toCharArray();
        String remove = "";
        int i,n,m;
        boolean not_finish = true;

        for(i = 0;i < string.length && not_finish;i++)
        {
            not_finish = false;
            char leading_letter = string[i];
            for(n = i + 1; n < string.length; n++)
            {
                if(leading_letter == string[n]) not_finish = true;
            }
        }
        i--;
        for(;i  < string.length;i++)
        {
            remove += string[i];
        }
        return remove;
    }
}
