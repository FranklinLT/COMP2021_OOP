package hk.edu.polyu.comp.comp2022.assignment1;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Base7 {

    public static String convertToBase7(int num) {
        // TODO: Add your code here
        String sign = "",retstr = "";
        if(num < 0)
        {
            sign = "-";
            num *= -1;
        }

        while(num >= 7)
        {
            int remainder = num % 7;
            retstr = (char)('0' + remainder) + retstr;
            num /= 7;
        }
        retstr = (char)('0' + num) + retstr;
        retstr = sign + retstr;
        return retstr;
    }
}
