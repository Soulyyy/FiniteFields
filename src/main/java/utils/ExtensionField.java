package utils;

public class ExtensionField {

  FiniteField underlying;

  Polynomial polynomial;

  public ExtensionField(FiniteField underlying, Polynomial polynomial) {
    this.underlying = underlying;
    this.polynomial = polynomial;
  }

  private int[] createModuloPolynomial() {
    int[] poly = this.polynomial.polynomial;
    //Find first
    int firstcoef = poly[0];
    poly[0] = 0;
    Polynomial newPoly = polynomial.additiveInverse();
  }
}
