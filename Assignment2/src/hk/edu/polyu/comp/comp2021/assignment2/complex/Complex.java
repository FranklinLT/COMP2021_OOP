package hk.edu.polyu.comp.comp2021.assignment2.complex;

public class Complex {

    // Task 3: add the missing fields
    private Rational real;
    private Rational imag;

    public Complex(Rational real, Rational imag) {
        // Task 4: complete the constructor
        this.real = real;
        this.imag = imag;
    }

    public Complex add(Complex other) {
        // Task 4: complete the method
        Rational result_real = this.real.add(other.real);
        Rational result_imag = this.imag.add(other.imag);
        Complex result = new Complex(result_real,result_imag);
        return result;
    }

    public Complex subtract(Complex other) {
        // Task 4: complete the method
        Rational result_real = this.real.subtract(other.real);
        Rational result_imag = this.imag.subtract(other.imag);
        Complex result = new Complex(result_real,result_imag);
        return result;
    }

    public Complex multiply(Complex other) {
        // Task 4: complete the method
        Rational temp_real1 = this.real.multiply(other.real);
        Rational temp_real2 = this.imag.multiply(other.imag);
        Rational temp_imag1 = this.real.multiply(other.imag);
        Rational temp_imag2 = this.imag.multiply(other.real);
        Rational result_real = temp_real1.subtract(temp_real2);
        Rational result_imag = temp_imag1.add(temp_imag2);
        Complex result = new Complex(result_real,result_imag);
        return result;
    }

    public Complex divide(Complex other) {
        // Task 4: complete the method
        // you may assume 'other' is never equal to '0+/-0i'.
        Rational temp_real1 = this.real.multiply(other.real);
        Rational temp_real2 = this.imag.multiply(other.imag);
        Rational temp_imag1 = this.real.multiply(other.imag);
        Rational temp_imag2 = this.imag.multiply(other.real);
        Rational result_real = temp_real1.add(temp_real2);
        Rational result_imag = temp_imag2.subtract(temp_imag1);

        Rational other_temp1 = other.real.multiply(other.real);
        Rational other_temp2 = other_temp1.add(other.imag.multiply(other.imag));

        Complex result = new Complex(result_real.divide(other_temp2),result_imag.divide(other_temp2));
        return result;
    }

    public void simplify() {
        // Task 4L complete the method
        real.simplify();
        imag.simplify();
    }

    public String toString() {
        // Task 4: complete the method
        return "(" + real.toString() + "," + imag.toString() + ")";
    }

    // ==================================

}
