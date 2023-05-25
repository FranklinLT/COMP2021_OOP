package hk.edu.polyu.comp.comp2021.assignment3.baseN;

import java.math.BigInteger;

public class BaseNIntegerUnsigned {

    /**
     * Magnitude of the integer in String. 'magnitude' should always be valid w.r.t. 'base', and it should never
     * contain unnecessary leading zeros, i.e., DIGIT_ZEROs.
     */
    private final String magnitude;

    /** Base of the integer. Only valid base values are allowed. */
    private final int base;

    /**
     * Return the base of the integer.
     */
    public int getBase() {
        return base;
    }

    /**
     * Return the magnitude of the integer.
     */
    public String getMagnitude() {
        return magnitude;
    }


    /**
     * Instantiate an NBasedIntegerUnsigned. Throws IllegalArgumentException if 'base' or 'magnitude' is invalid.
     */
    public BaseNIntegerUnsigned(String magnitude, int base) {
        if(!isValidMagnitude(magnitude, base))
            throw new IllegalArgumentException();

        // Add missing code here.
        this.magnitude = magnitude;
        this.base = base;
    }

    /**
     * Can 'this' be represented as an int value?
     */
    public boolean canBeRepresentedInInteger() {
        // Add missing code here.
        String test = withoutLeadingZeroes(magnitude);

        int cur_length = test.length(), max_length = getMaximum(base).getMagnitude().length();
        if(cur_length > max_length) return false;
        else if (cur_length == max_length)
        {
            if(test.compareTo(getMaximum(base).getMagnitude()) > 0) return false;
        }
        return true;
    }

    /**
     * Return the value of 'this' integer in decimal.
     * Throw IllegalStateException if 'this' is too large to be represented as an int value.
     */
    public int toInteger(){
        if(!canBeRepresentedInInteger())
            throw new IllegalStateException();

        // Add missing code here.
        char[] temp = magnitude.toCharArray();
        int sum = 0, length = magnitude.length();

        for(int i = length - 1, n = 0; i >= 0; i--, n++)
        {
            int temp_sum = getValueAtPosition(n);
            for(int m = 0; m < n; m++)
            {
                temp_sum *= base;
            }
            sum += temp_sum;
        }

        return sum;
    }

    /**
     * Return the String representation of 'this' in the format "magnitude(base)".
     */
    public String toString(){
        // Add missing code here.
        return withoutLeadingZeroes(magnitude) + "(" + getBase() + "" + ")";
    }

    /**
     * Return true if 'this' and 'other' are both of type 'BaseNIntegerUnsigned' and they have equivalent 'base'
     * and 'magnitude'. Otherwise return false.
     */
    public boolean equals(Object other){
        // Add missing code here.
        if(!(other instanceof BaseNIntegerUnsigned)) return false;

        return (((BaseNIntegerUnsigned) other).getBase() == this.base) &&
                (withoutLeadingZeroes(((BaseNIntegerUnsigned) other).getMagnitude()).compareTo(withoutLeadingZeroes(this.magnitude)) == 0);
    }

    /**
     * Return an integer value to indicate the relation between 'this' and 'other'. The value should be 1) positive if
     * 'this' is greater than 'other', 2) zero if 'this' is equal to 'other', and 3) negative if 'this' is smaller
     * than 'other'. Throw IllegalArgumentException if 'other' is null or 'this' and 'other' have different bases.
     *
     * Note: Both 'this' and 'other' may not be representable as 'int' values,
     *       so neither of them should be converted to 'int' during the comparison.
     */
    public int compare(BaseNIntegerUnsigned other){
        if(other == null || getBase() != other.getBase())
            throw new IllegalArgumentException();

        // Add missing code here.
        String check1 = withoutLeadingZeroes(this.magnitude);
        String check2 = withoutLeadingZeroes(other.getMagnitude());

        if(check1.length() > check2.length()) return 1;
        else if(check1.length() < check2.length()) return -1;
        return check1.compareTo(check2);
    }

    /**
     * Return the sum of 'this' and 'other'. The result integer should have the same base as 'this'.
     * Throw IllegalArgumentException if 'other' is null or 'this' and 'other' have different bases.
     *
     * Note: Both 'this' and 'other' may not be representable as 'int' values,
     *       so neither of them should be converted to 'int' during the calculation.
     */
    public BaseNIntegerUnsigned add(BaseNIntegerUnsigned other){
        if(other == null || getBase() != other.getBase())
            throw new IllegalArgumentException();

        // Add missing code here.
        String result, temp1 = withoutLeadingZeroes(this.magnitude), temp2 = withoutLeadingZeroes(other.getMagnitude());
        result = (temp1.length() >= temp2.length()) ? "A" + temp1 : "A" + temp2;
        char[] operation = (temp1.length() >= temp2.length()) ? temp2.toCharArray() : temp1.toCharArray();
        char[] temp_result = result.toCharArray();
        int carry_bit = 0, m = temp_result.length - 1, op_int, tr_int;

        for(int i = 0, n = operation.length - 1; i < operation.length; i++, n--, m--)
        {
            op_int = getValueFromDigit(operation[n],base);
            tr_int = getValueFromDigit(temp_result[m],base);
            if(op_int + tr_int + carry_bit >= base)
            {
                temp_result[m] = getDigitFromValue(op_int + tr_int + carry_bit - base,base);
                carry_bit = 1;
            }
            else {
                temp_result[m] = getDigitFromValue(op_int + tr_int + carry_bit,base);
                carry_bit = 0;
            }
        }

        while(m >= 0){
            tr_int = getValueFromDigit(temp_result[m],base);
            if(tr_int + carry_bit >= base)
            {
                temp_result[m] = getDigitFromValue(tr_int + carry_bit - base,base);
                carry_bit = 1;
                m--;
            }
            else
            {
                temp_result[m] = getDigitFromValue(tr_int + carry_bit,base);
                break;
            }
        }

        result = withoutLeadingZeroes(new String(temp_result));
        BaseNIntegerUnsigned Answer = new BaseNIntegerUnsigned(result, base);

        return Answer;
    }

    /**
     * Return the result of subtracting 'other' from 'this'. The result integer should have the same base as 'this'.
     * Throw IllegalArgumentException if 1) 'other' is null, 2) 'this' and 'other' have different bases, or
     * 3) 'this' is smaller than 'other'.
     *
     * Note: Both 'this' and 'other' may not be representable as 'int' values,
     *       so neither of them should be converted to 'int' during the calculation.
     */
    public BaseNIntegerUnsigned subtract(BaseNIntegerUnsigned other) {
        if (other == null || getBase() != other.getBase() || compare(other) < 0)
            throw new IllegalArgumentException();

        // Add missing code here.
        String result, temp1 = withoutLeadingZeroes(this.magnitude), temp2 = withoutLeadingZeroes(other.getMagnitude());
        char[] operation = temp2.toCharArray();
        char[] temp_result = temp1.toCharArray();
        int carry_bit = 0, m = temp_result.length - 1, op_int, tr_int;

        for(int i = 0, n = operation.length - 1; i < operation.length; i++, n--, m--)
        {
            op_int = getValueFromDigit(operation[n],base);
            tr_int = getValueFromDigit(temp_result[m],base);
            if(tr_int - op_int - carry_bit < 0)
            {
                temp_result[m] = getDigitFromValue(tr_int + base - op_int - carry_bit,base);
                carry_bit = 1;
            }
            else {
                temp_result[m] = getDigitFromValue(tr_int - carry_bit - op_int,base);
                carry_bit = 0;
            }
        }

        if(m >= 0){
            tr_int = getValueFromDigit(temp_result[m],base);
            temp_result[m] = getDigitFromValue(tr_int - carry_bit,base);
        }

        result = withoutLeadingZeroes(new String(temp_result));
        BaseNIntegerUnsigned Answer = new BaseNIntegerUnsigned(result, base);

        return Answer;
    }

    //==================================================================================== Private members

    /**
     * Return the number of positions, which is the same as the number of digits, in the 'magnitude' of 'this'.
     *
     * For example, magnitudes "BA" and "CBA" in base 6 have 2 and 3 positions, respectively.
     */
    private int getNumberOfPositions(){
        return getMagnitude().length();
    }

    /**
     * Return the value of the digit at position 'pos'.
     * 'pos' should be non-negative. Return 0 if 'pos' is greater than the maximum position.
     *
     * For example, given magnitude "GECA" in base 9, the digits at positions 1, 3, and 5 are 'C', 'G', and 'A', and
     * their values are 2, 6, and 0, respectively.
     */
    private int getValueAtPosition(int pos){
        assert(pos >= 0);

        char digit;
        if(pos >= getNumberOfPositions())
            digit = DIGIT_ZERO;
        else
            digit = getMagnitude().charAt(getNumberOfPositions() - 1 - pos);

        return getValueFromDigit(digit, getBase());
    }

    //======================================================================================== Static members

    public static final int BASE_MINIMUM = 2;
    public static final int BASE_MAXIMUM = 26;
    public static final char DIGIT_ZERO = 'A';

    /**
     * The largest BaseNIntegerUnsigned objects that can be represented as int values.
     */
    private static final BaseNIntegerUnsigned[] MAX_VALUE_BASE_N;

    static{
        // To initialize MAX_VALUE_BASE_N.
        MAX_VALUE_BASE_N = new BaseNIntegerUnsigned[27];
        int maxInt = Integer.MAX_VALUE;
        for(int base = BASE_MINIMUM ; base <= BASE_MAXIMUM ; base++ ){
            MAX_VALUE_BASE_N [base] = new BaseNIntegerUnsigned(encode(maxInt, base), base);
        }
    }

    /**
     * Return the greatest BaseNIntegerUnsigned object with 'base' that represent Integer.MAX_VALUE.
     *
     * For example, the object will be "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB(2)", i.e., 31 ones, when base is 2.
     */
    public static BaseNIntegerUnsigned getMaximum(int base){
        if(!isValidBase(base))
            throw new IllegalArgumentException();

        return MAX_VALUE_BASE_N [base];
    }

    /**
     * Return the encoding of 'value' in 'base'.
     *
     * For example, 16 will encoded as "BAAAA" when base is 2 and "DB" when base is 5.
     */
    public static String encode(int value, int base){
        if(value < 0 || !isValidBase(base))
            throw new IllegalArgumentException();

        if(value == 0)
            return DIGIT_ZERO + "";

        StringBuilder builder = new StringBuilder();
        int quotient = value;
        int remainder = 0;
        while(quotient != 0){
            remainder = quotient % base;
            quotient /= base;
            char digit = getDigitFromValue(remainder, base);
            builder.insert(0, digit);
        }
        return builder.toString();
    }

    /**
     * Return true if 'base' is between BASE_MINIMUM and BASE_MAXIMUM (both inclusive); Otherwise, return false.
     */
    public static boolean isValidBase(int base){
        return BASE_MINIMUM <= base && base <= BASE_MAXIMUM;
    }

    /**
     * Return true if 'base' is valid and 'magnitude' is valid for 'base'.
     * 'magnitude' is valid for 'base' if 1) it is not null, 2) it is not empty, and 3) it contains only digits
     * that are valid for the base.
     */
    public static boolean isValidMagnitude(String magnitude, int base){
        if(!isValidBase(base) || magnitude == null || magnitude.isEmpty())
            return false;

        // Add missing code here.
        char check_digit = getLargestDigit(base);
        char[] check_string = magnitude.toCharArray();
        System.out.println(magnitude);
        for(int i = 0; i < check_string.length; i++)
        {
            if((check_digit < check_string[i])||(check_string[i]<'A')) return false;
        }

        return true;
    }

    /**
     * Return the simplified magnitude string by removing unnecessary leading zeros, i.e., DIGIT_ZEROs,
     * from 'magnitudeStr'.
     */
    public static String withoutLeadingZeroes(String magnitudeStr){
        assert(magnitudeStr != null && !magnitudeStr.isEmpty());

        int pos = 0;
        for(; pos < magnitudeStr.length(); pos++){
            if(magnitudeStr.charAt(pos) != DIGIT_ZERO)
                break;
        }
        if(pos < magnitudeStr.length())
            return magnitudeStr.substring(pos);
        else
            return DIGIT_ZERO + "";
    }

    /**
     * Return the largest digit allowed by 'base'. Throw IllegalArgumentException if 'base' is invalid.
     *
     * For example, the largest digits for base 4 and 6 are 'D' and 'F', respectively.
     */
    public static char getLargestDigit(int base){
        if(!isValidBase(base))
            throw new IllegalArgumentException();

        return (char)(DIGIT_ZERO + base - 1);
    }

    /**
     * Return the digit in 'base' with the specified 'value'. Throw IllegalArgumentException if 'base' is invalid or
     * 'value' cannot be represented using a single digit in 'base'.
     *
     * For example, value 3 is represented using digit 'D' in base 5.
     */
    public static char getDigitFromValue(int value, int base){
        if(!isValidBase(base) || value < 0 || value >= base)
            throw new IllegalArgumentException();

        return (char)(DIGIT_ZERO + value);
    }

    /**
     * Return the value of 'digit' in 'base'. Throw IllegalArgumentException if 'base' is invalid or 'digit' is not
     * a valid digit for 'base'.
     *
     * For example, digit 'C' in base 6 has value 2.
     */
    public static int getValueFromDigit(char digit, int base){
        if(!isValidBase(base) || digit < DIGIT_ZERO || digit > getLargestDigit(base))
            throw new IllegalArgumentException();

        return digit - DIGIT_ZERO;
    }

}
