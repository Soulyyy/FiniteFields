package utils;

public class ExtensionField {

  FiniteField underlying;

  Polynomial polynomial;

  public ExtensionField(FiniteField underlying, Polynomial polynomial) {
    this.underlying = underlying;
    this.polynomial = polynomial;
  }

  private Polynomial[] createModuloPolynomial() {
    Polynomial tmp = this.polynomial;
    int[] poly = this.polynomial.polynomial;
    //Normalize the polynomial
    int firstCoef = poly[0];
    int inverseOfFirstCoef = underlying.findElementMultiplicativeInverse(firstCoef);
    for (int i = 0; i < this.polynomial.polynomial.length; i++) {
      this.polynomial.polynomial[i] = underlying.forceElementToField(this.polynomial.polynomial[i] * inverseOfFirstCoef);
    }
    //Find first
    int[] mainPolyCoefs = new int[this.polynomial.polynomial.length];
    mainPolyCoefs[0] = poly[0];
    Polynomial mainPoly = new Polynomial(mainPolyCoefs, this.polynomial.finiteField);
    poly[0] = 0;
    Polynomial newPoly = polynomial.additiveInverse();
    //TODO remove state here
    this.polynomial = tmp;
    return new Polynomial[]{mainPoly, newPoly};
  }

  public Polynomial getPolynomialInField(Polynomial polynomial) {
    Polynomial[] transformation = createModuloPolynomial();
    for (int i = 0; i <= polynomial.polynomial.length - transformation[0].polynomial.length; i++) {
      int offset = transformation[0].polynomial.length - transformation[1].polynomial.length;
      int multiplier = polynomial.polynomial[i];
      for (int j = 0; j < transformation[1].polynomial.length; j++) {
        polynomial.polynomial[i + j + offset] += multiplier * transformation[1].polynomial[j];
        polynomial.polynomial = underlying.forceToField(polynomial.polynomial);
      }
      polynomial.polynomial[i] = 0;
    }
    polynomial.polynomial = underlying.forceToField(polynomial.polynomial);
    polynomial.polynomial = polynomial.reduceToNonZeroSize(polynomial.polynomial);
    return polynomial;
  }

  public Polynomial add(Polynomial first, Polynomial second) {
    Polynomial response = first.add(second);
    return getPolynomialInField(response);
  }

  public Polynomial subtract(Polynomial first, Polynomial second) {
    Polynomial response = first.subtract(second);
    return getPolynomialInField(response);
  }

  public Polynomial multiply(Polynomial first, Polynomial second) {
    Polynomial response = first.multiply(second);
    return getPolynomialInField(response);
  }

  public Polynomial[] divide(Polynomial first, Polynomial second) {
    Polynomial[] response = first.divide(second);
    return new Polynomial[]{getPolynomialInField(response[0]), getPolynomialInField(response[0])};
  }

  public Polynomial power(Polynomial polynomial, int power) {
    Polynomial current = new Polynomial(new int[]{1}, polynomial.finiteField);
    for (int i = 0; i < power; i++) {
      current = multiply(current, polynomial);
    }
    return getPolynomialInField(current);
  }

  public int getDegree() {
    return this.polynomial.degree();
  }
}
