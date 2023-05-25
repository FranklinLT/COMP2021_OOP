package hk.edu.polyu.comp.comp2022.assignment1;

public class ScientificNotation {

    public static double getValueFromAeB(String strSequence){
        int exponent = 0;
        int i = 0;
        double significand = 0.0, retValue;
        String sign = "+";
        String divided = "e";
        String[] temp = strSequence.split(divided);
        // TODO:To parse significand and exponent from strSequence.
        System.out.print(temp[0]);

        // significand part:
        boolean flag = false;
        double location = 1;
        if(temp[0].startsWith("-"))
        {
            sign = "-";
            i = 1;
        }
        char[] first = temp[0].toCharArray();
        for(;i < first.length;i++)
        {
            if(first[i] >= '0' && first[i] <= '9')
            {
                significand = significand + location * (first[i] - '0');
                location *= 0.1;
            }
        }
        if(sign == "-") significand *= -1;

        // exponent part:
        i = 0; sign = "+";
        if(temp[1].startsWith("-"))
        {
            sign = "-";
            i = 1;
        }
        char[] second = temp[1].toCharArray();
        for(;i < second.length;i++)
        {
            if(second[i] >= '0' && second[i] <= '9')
            {
                exponent = exponent*10 + second[i] - '0';
            }
        }
        if(sign == "-") exponent *= -1;

        retValue = significand * Math.pow(10, exponent);
        return retValue;
    }
}
