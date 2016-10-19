package utils;

public class Polynomial {

  int[] polynomial;
  FiniteField finiteField;

  public Polynomial(int[] polynomial, FiniteField finiteField) {
    this.polynomial = polynomial;
    this.finiteField = finiteField;
  }


  public Polynomial add(Polynomial polynomial) {
    if (this.polynomial.length > polynomial.polynomial.length) {
      return new Polynomial(addElements(this.polynomial, polynomial.polynomial), finiteField);
    } else {
      return new Polynomial(addElements(polynomial.polynomial, this.polynomial), finiteField);
    }
  }

  public Polynomial subtract(Polynomial polynomial) {
    return new Polynomial(subtractElements(this.polynomial, polynomial.polynomial), finiteField);
  }

  public Polynomial multiply(Polynomial polynomial) {
    return new Polynomial(multiplyElements(this.polynomial, polynomial.polynomial), finiteField);
  }

  private int[] addElements(int[] bigger, int[] smaller) {
    for (int i = smaller.length - 1; i >= 0; i--) {
      bigger[i] += smaller[i];
    }
    return finiteField.forceToField(bigger);
  }

  private int[] subtractElements(int[] first, int[] second) {
    int offset = first.length - second.length;
    for (int i = 0; i < second.length; i++) {
      first[i] -= second[i + offset];
    }
    return finiteField.forceToField(first);
  }

  private int[] multiplyElements(int[] first, int[] second) {
    int[] resp = new int[first.length + second.length];
    for (int i = 0; i < first.length; i++) {
      for (int j = 0; j < second.length; j++) {
        resp[i + j] = first[i] * second[j];
      }
    }
    return finiteField.forceToField(resp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.polynomial.length; i++) {
      sb.append(this.polynomial[i]).append("x^").append(this.polynomial.length - i).append(" ");
    }
    return sb.toString().trim();
  }
}
