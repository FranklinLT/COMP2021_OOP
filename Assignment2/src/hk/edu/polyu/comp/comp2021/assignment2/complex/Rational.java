package hk.edu.polyu.comp.comp2021.assignment2.complex;

public class Rational {
    // Task 1: add the missing fields
    private int numerator;
    private int denominator;

    public Rational(int numerator, int denominator) {
        // Task 2: complete the constructor
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Rational add(Rational other) {
        // Task 2: complete the method
        int result_numerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int result_denominator = this.denominator * other.denominator;
        Rational result = new Rational(result_numerator,result_denominator);
        return result;
    }

    public Rational subtract(Rational other) {
        // Task 2: complete the method
        int result_numerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int result_denominator = this.denominator * other.denominator;
        Rational result = new Rational(result_numerator,result_denominator);
        return result;
    }

    public Rational multiply(Rational other) {
        // Task 2: complete the method
        int result_numerator = this.numerator * other.numerator;
        int result_denominator = this.denominator * other.denominator;
        Rational result = new Rational(result_numerator,result_denominator);
        return result;
    }

    public Rational divide(Rational other) {
        // Task 2: complete the method
        int result_numerator = this.numerator * other.denominator;
        int result_denominator = this.denominator * other.numerator;
        if(result_denominator < 0)
        {
            result_numerator *= -1;
            result_denominator *= -1;
        }
        Rational result = new Rational(result_numerator,result_denominator);
        return result;
    }

    public String toString() {
        // Task 2: complete the method
        return numerator + "" + "/" + denominator + "";
    }

    public void simplify() {
        // Task 2: complete the method
        int dividend = numerator, divisor = denominator, remainder;
        while(divisor != 0)
        {
            remainder = dividend % divisor;
            dividend = divisor;
            divisor = remainder;
        }

        if(dividend < 0) dividend *= -1;
        numerator /= dividend;
        denominator /= dividend;
    }

    // ==================================

}
