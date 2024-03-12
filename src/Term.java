public class Term {
    //it has the coefficient and exponents of the each term
    private int coefficient;
    private int exponentX;
    private int exponentY;
    private int exponentZ;

    public Term(int coefficient, int exponentX, int exponentY, int exponentZ) {
        this.coefficient = coefficient;
        this.exponentX = exponentX;
        this.exponentY = exponentY;
        this.exponentZ = exponentZ;
    }

    //gets coefficient
    public int getCoefficient() {
        return coefficient;
    }

    //set coefficient
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    //gets exponent x
    public int getExponentX() {
        return exponentX;
    }

    //sets exponent x
    public void setExponentX(int exponentX) {
        this.exponentX = exponentX;
    }

    //gets exponent y
    public int getExponentY() {
        return exponentY;
    }

    //sets exponent y
    public void setExponentY(int exponentY) {
        this.exponentY = exponentY;
    }

    //gets exponent z
    public int getExponentZ() {
        return exponentZ;
    }

    //sets exponent z
    public void setExponentZ(int exponentZ) {
        this.exponentZ = exponentZ;
    }
}