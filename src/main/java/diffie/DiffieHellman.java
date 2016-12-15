package diffie;

import utils.FiniteField;
import utils.Polynomial;

public class DiffieHellman {

  private Polynomial polynomial;
  private FiniteField finiteField;

  public DiffieHellman(Polynomial polynomial, FiniteField finiteField) {
    this.polynomial = polynomial;
    this.finiteField = finiteField;
  }

  public Polynomial getPolynomial() {
    return polynomial;
  }

  public FiniteField getFiniteField() {
    return finiteField;
  }

  public int calculatePolynomialValue(int x) {
    int[] coefficients = polynomial.getPolynomial();
    int accumulator = 0;
    for (int i = 0; i < coefficients.length; i++) {
      accumulator += Math.pow(x, i) * coefficients[coefficients.length - 1 - i];
    }
    return 0;
  }
}
