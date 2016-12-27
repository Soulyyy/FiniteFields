package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polynomial implements Comparable {

  int[] polynomial;
  public FiniteField finiteField;

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
    while (!divide[1].equals(new Polynomial(new int[0], polynomial.finiteField)) && !divide[0].equals(new Polynomial(new int[0], polynomial.finiteField))) {
      first = second;
      second = divide[0];
      divide = first.divide(second);
    }
    if (divide[0].equals(new Polynomial(new int[0], polynomial.finiteField))) {
      return new Polynomial(new int[]{1}, this.finiteField);
    } else {
      return divide[0];
    }
  }

  public Polynomial additiveInverse() {
    int[] ar = new int[this.polynomial.length];
    for (int i = 0; i < ar.length; i++) {
      ar[i] = -this.polynomial[i];
    }
    ar = finiteField.forceToField(ar);
    ar = reduceToNonZeroSize(ar);
    return new Polynomial(ar, finiteField);
  }

  public List<Polynomial[]> extendedEuclidean(Polynomial polynomial) {
    List<Polynomial[]> sequenceOfSteps = new ArrayList<>();
    //Like GCD but store intermediate results
    Polynomial first = this;
    Polynomial second = polynomial;
    Polynomial[] divide = first.divide(second);
    while (!divide[1].equals(new Polynomial(new int[0], polynomial.finiteField)) && !divide[0].equals(new Polynomial(new int[0], polynomial.finiteField))) {
      first = second;
      second = divide[0];
      divide = first.divide(second);
      if (divide[0].equals(new Polynomial(new int[0], polynomial.finiteField))) {
        divide[0] = new Polynomial(new int[]{1}, this.finiteField);
      }
      Polynomial[] resultElement = new Polynomial[]{first, divide[0], divide[1]};
      sequenceOfSteps.add(resultElement);
    }
    return sequenceOfSteps;
  }


  public Polynomial[] divide(Polynomial polynomial) {
    if (this.degree() == 0 && polynomial.polynomial[0] == 0 || polynomial.degree() == 0) {
      return new Polynomial[]{this, new Polynomial(new int[]{0}, finiteField)};
      //throw new IllegalStateException("Division by a zero polynomial");
    }
    if (this.degree() < polynomial.degree()) {
      Polynomial result = new Polynomial(new int[0], finiteField);
      Polynomial remainder = new Polynomial(this.polynomial, finiteField);
      return new Polynomial[]{result, remainder};
    }
    int degree = this.degree() - polynomial.degree();
    if (degree == 0 && this.polynomial.length <= 1) {
      return new Polynomial[]{this, new Polynomial(new int[]{0}, finiteField)};
    }
    int[] ar = new int[degree + 1];
    int coef = finiteField.divide(this.polynomial[0], polynomial.polynomial[0]);
    ar[0] = coef;
    Polynomial newPoly = new Polynomial(ar, finiteField);
    Polynomial multiplicationResult = newPoly.multiply(polynomial);
    Polynomial subtractionResult = this.subtract(multiplicationResult);
    Polynomial[] resp = subtractionResult.divide(polynomial);
    resp[0] = resp[0].add(newPoly);
    return resp;
  }

  private int[] addElements(int[] bigger, int[] smaller) {
    int dif = bigger.length - smaller.length;
    for (int i = smaller.length - 1; i >= 0; i--) {
      bigger[i + dif] += smaller[i];
    }
    bigger = finiteField.forceToField(bigger);
    bigger = reduceToNonZeroSize(bigger);
    return bigger;
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
    if (first.length == 0 && second.length == 0) {
      return new int[]{0};
    }
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
      if (polynomial[i] != 0) {
        return polynomial.length - i -1;
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
    if (polynomial == null || polynomial.length == 0) {
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

  public int[] reduceToNonZeroSize(int[] polynomial) {

    int sizeToReduceby = 0;
    for (int i = 0; i < polynomial.length; i++) {
      if (polynomial[i] == 0) {
        sizeToReduceby = i + 1;
      } else {
        break;
      }
    }
    if (sizeToReduceby == 0) {
      return polynomial;
    }
    int[] ar = new int[polynomial.length - sizeToReduceby];
    for (int i = 0; i < polynomial.length - sizeToReduceby; i++) {
      ar[i] = polynomial[i + sizeToReduceby];
    }
    return ar;
  }

  public int evaluate(int x) {
    int accumulator = 0;
    for (int i = 0; i < polynomial.length; i++) {
      accumulator = finiteField.add(accumulator, finiteField.multiply(finiteField.power(x, polynomial.length - i), polynomial[i]));
    }
    return accumulator;
  }

  public int[] getPolynomial() {
    return polynomial;
  }


  @Override
  public int compareTo(Object o) {
    if (o instanceof Polynomial) {
      Polynomial second = (Polynomial) o;
      if (this.degree() > second.degree()) {
        return 1;
      } else if (this.degree() < second.degree()) {
        return -1;
      } else {
        for (int i = 0; i < this.degree(); i++) {
          if (this.polynomial[i] > second.polynomial[i]) {
            return 1;
          } else if (this.polynomial[i] < second.polynomial[i]) {
            return -1;
          }
        }
        return 0;
      }
    } else {
      return 0;
    }
  }
}
