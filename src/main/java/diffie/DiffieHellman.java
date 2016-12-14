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
}
