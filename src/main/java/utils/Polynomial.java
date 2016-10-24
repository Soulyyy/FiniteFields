package utils;

import java.util.Arrays;

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

  public Polynomial gcd(Polynomial polynomial) {
    Polynomial first = this;
    Polynomial second = polynomial;
    Polynomial[] divide = first.divide(second);
    while(!divide[1].equals(new Polynomial(new int[]{0}, polynomial.finiteField))) {
      first = second;
      second = divide[0];
      divide = first.divide(second);
    }
    return divide[0];
  }


  public Polynomial[] divide(Polynomial polynomial) {
    if (this.degree() == 0 && polynomial.polynomial[0] == 0) {
      throw new IllegalStateException("Division by a zero polynomial");
    }
    if (this.degree() < polynomial.degree()) {
      Polynomial result = new Polynomial(new int[0], finiteField);
      Polynomial remainder = new Polynomial(this.polynomial, finiteField);
      return new Polynomial[]{result, remainder};
    }
    int degree = this.degree() - polynomial.degree();
    if (degree == 0) {
      return new Polynomial[]{this, new Polynomial(new int[]{0}, finiteField)};
    }
    int[] ar = new int[degree + 1];
    int coef = this.polynomial[0] / polynomial.polynomial[0];
    ar[0] = coef;
    Polynomial newPoly = new Polynomial(ar, finiteField);
    Polynomial multiplicationResult = newPoly.multiply(polynomial);
    Polynomial subtractionResult = this.subtract(multiplicationResult);
    return polynomial.divide(subtractionResult);
  }

  private int[] addElements(int[] bigger, int[] smaller) {
    int dif = bigger.length - smaller.length;
    for (int i = smaller.length - 1; i >= 0; i--) {
      bigger[i + dif] += smaller[i];
    }
    return finiteField.forceToField(bigger);
  }

  private int[] subtractElements(int[] first, int[] second) {
    if (first.length >= second.length) {
      int[] resp = new int[first.length];
      int offset = first.length - second.length;
      for (int i = 0; i < first.length; i++) {
        resp[i] = first[i];
        if (i >= offset) {
          resp[i] -= second[i - offset];
        }
      }
      return shrinkDegree(finiteField.forceToField(resp));
    } else {
      int[] resp = new int[second.length];
      int offset = second.length - first.length;
      for (int i = 0; i < second.length; i++) {
        resp[i] -= second[i];
        if (i >= offset) {
          resp[i] += first[i - offset];
        }
      }
      return shrinkDegree(finiteField.forceToField(resp));
    }
  }

  private int[] multiplyElements(int[] first, int[] second) {
    int[] resp = new int[first.length + second.length - 1];
    for (int i = 0; i < first.length; i++) {
      for (int j = 0; j < second.length; j++) {
        resp[i + j] += first[i] * second[j];
      }
    }
    return finiteField.forceToField(resp);
  }

  public int degree() {
    for (int i = 0; i < polynomial.length; i++) {
      if (i != 0) {
        return polynomial.length - i;
      }
    }
    return 0;
  }

  private int[] resizeMultiplicationArray(int[] array) {
    int offset = 0;
    for (int i = array.length - 1; i >= 0; i--) {
      if (array[i] != 0) {
        offset = i + 1;
        break;
      }
    }
    int[] resp = new int[offset];
    for (int i = 0; i < offset; i++) {
      resp[i] = array[i];
    }
    return resp;
  }

  private int[] shrinkDegree(int[] polynomial) {
    int cur = 0;
    for (int i = 0; i < polynomial.length; i++) {
      if (polynomial[i] != 0) {
        break;
      }
      cur = i + 1;
    }
    if (cur != 0) {
      int[] resp = new int[polynomial.length - cur];
      for (int i = 0; i < polynomial.length - cur; i++) {
        resp[i] = polynomial[i + cur];
      }
      return resp;
    }
    return polynomial;
  }

  @Override
  public String toString() {
    if (polynomial.length == 0) {
      return "0";
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.polynomial.length - 1; i++) {
      if (this.polynomial[i] != 1) {
        sb.append(this.polynomial[i]);
      }
      sb.append("x^").append(this.polynomial.length - (i + 1)).append(" + ");
    }
    sb.append(this.polynomial[polynomial.length - 1]);
    return sb.toString().trim();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Polynomial that = (Polynomial) o;

    if (!Arrays.equals(polynomial, that.polynomial)) return false;
    return finiteField != null ? finiteField.equals(that.finiteField) : that.finiteField == null;

  }

  @Override
  public int hashCode() {
    int result = Arrays.hashCode(polynomial);
    result = 31 * result + (finiteField != null ? finiteField.hashCode() : 0);
    return result;
  }
}
