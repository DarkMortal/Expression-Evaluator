package complex;

/**
 * Implementation of the ComplexInterface interface
 * 
 * @author Saptarshi Dey
 * @since March 2025
 * @version 1.0
 */

public record Complex(double real, double imaginary) implements complex.ComplexInterface {
    /**
     * @param other Complex number that needs to be added to the current Complex
     *              object
     * @return Sum of 2 Complex numbers
     */
    public Complex add(Complex other) {
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    /**
     * @param other Complex number that needs to be subtracted to the current
     *              Complex object
     * @return Difference between 2 Complex numbers
     */
    public Complex subtract(Complex other) {
        return new Complex(real - other.real, imaginary - other.imaginary);
    }

    /**
     * @param other Complex number that needs to be multiplied to the current
     *              Complex object
     * @return Dot product of 2 Complex numbers
     */
    public Complex multiply(Complex other) {
        return new Complex(real * other.real - imaginary * other.imaginary,
                real * other.imaginary + imaginary * other.real);
    }

    /**
     * @param other Complex number by which the current Complex object needs to be
     *              divided
     * @return Quotient after division of 2 Complex numbers
     */
    public Complex divide(Complex other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        return new Complex((real * other.real + imaginary * other.imaginary) / denominator,
                (imaginary * other.real - real * other.imaginary) / denominator);
    }

    /**
     * @return Distance from the radius of the Complex number in an Euclidean
     *         complex plane
     */
    public double mod() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    /**
     * @return Argument of the Complex number
     */
    public double arg() {
        return Math.atan2(imaginary, real);
    }

    /**
     * @return Conjugate of the Complex number
     */
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    /**
     * @return Complex number raised to a real power
     */
    public Complex pow(double power) {
        double modulus = Math.pow(mod(), power);
        double argument = power * arg();
        return new Complex(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }

    /**
     * @return Complex number raised to a Complex power
     */
    public Complex pow(Complex power) {
        if (power.real == 0 && power.imaginary == 0)
            return new Complex(1, 0);
        if (power.imaginary == 0)
            return this.pow(power.real);
        return this.log(Math.E).multiply(power).exp();
    }

    /**
     * @return Reciprocal of the Complex number
     */
    public Complex reciprocal() {
        double denominator = real * real + imaginary * imaginary;
        return new Complex(real / denominator, -imaginary / denominator);
    }

    /**
     * @return Exponent raised to the power of the Complex number
     */
    public Complex exp() {
        double expReal = Math.exp(real);
        return new Complex(expReal * Math.cos(imaginary), expReal * Math.sin(imaginary));
    }

    /**
     * @return Sine of the Complex number
     */
    public Complex sin() {
        return new Complex(Math.sin(real) * Math.cosh(imaginary), Math.cos(real) * Math.sinh(imaginary));
    }

    /**
     * @return Cosine of the Complex number
     */
    public Complex cos() {
        return new Complex(Math.cos(real) * Math.cosh(imaginary), -Math.sin(real) * Math.sinh(imaginary));
    }

    /**
     * @param base Base in which the Log will be computed
     * @return Log of the Complex number
     */
    public Complex log(double base) {
        return new Complex(Math.log(mod()) / Math.log(base), arg() / Math.log(base));
    }

    /**
     * @param number Scalar multiplicand
     * @return Scalar product of a number and a Complex number
     */
    public Complex multiply(double number) {
        return new Complex(this.real * number, this.imaginary * number);
    }

    /**
     * @param precision Precision of fractional part of the real and imaginary
     *                  values
     * @return String representation of the Complex number
     */
    public String toString(int precision) {
        double t_real = Double.parseDouble(String.format("%." + precision + "f", real));
        double t_imaginary = Double.parseDouble(String.format("%." + precision + "f", imaginary));

        if (t_real == 0.0 && t_imaginary == 0.0)
            return String.format("%." + precision + "f", t_real);
        if (t_real == 0.0)
            return imaginary + "i";
        if (t_imaginary == 0.0)
            return Double.toString(t_real);
        if (t_imaginary == 1.0)
            return "(" + t_real + " + i)";
        String real_part = String.format("%." + precision + "f", real);
        String imaginary_part = imaginary < 0.0 ? String.format("- %.2fi", -imaginary)
                : String.format("+ %." + precision + "fi", imaginary);
        return "(" + t_real + " " + imaginary_part + ")";
    }

    @Override
    public String toString() {
        return toString(2);
    }
}