public class Term {
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

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getExponentX() {
        return exponentX;
    }

    public void setExponentX(int exponentX) {
        this.exponentX = exponentX;
    }

    public int getExponentY() {
        return exponentY;
    }

    public void setExponentY(int exponentY) {
        this.exponentY = exponentY;
    }

    public int getExponentZ() {
        return exponentZ;
    }

    public void setExponentZ(int exponentZ) {
        this.exponentZ = exponentZ;
    }
}