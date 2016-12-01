package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneralOperation {

  public static List<Polynomial> getAllFieldElements(ExtensionField field) {
    int elementsPerExponent = field.underlying.prime;
    List<Polynomial> polynomials = new ArrayList<>();
    for (int i = 0; i < elementsPerExponent; i++) {
      int[] newCoefs = new int[1];
      newCoefs[0] = i;
      polynomials.addAll(getAllFieldElements(field, new Polynomial(newCoefs, field.underlying), 1, field.polynomial.degree()));
    }
    return polynomials;
  }

  private static List<Polynomial> getAllFieldElements(ExtensionField field, Polynomial currentPolynomial, int currentLevel, int maxDegree) {
    List<Polynomial> values = new ArrayList<>();
    if (maxDegree == currentLevel) {
      return Collections.singletonList(currentPolynomial);
    } else {
      for (int i = 0; i < field.underlying.prime; i++) {
        int[] newCoefs = new int[currentLevel + 1];
        newCoefs[0] = i;
        Polynomial newPolynomial = field.add(currentPolynomial, new Polynomial(newCoefs, field.underlying));
        values.addAll(getAllFieldElements(field, newPolynomial, currentLevel + 1, maxDegree));
      }
      return values;
    }
  }

  public static int smallestRootOfUnity(ExtensionField field, Polynomial polynomial) {
    for (int i = 1; i <= Math.ceil(Math.pow(field.underlying.prime, field.polynomial.degree())) + 1; i++) {
      Polynomial powerPoly = field.power(polynomial, i);
      if(powerPoly.equals(new Polynomial(new int[]{1}, field.underlying))){
        return i;
      }
    }
    return -1;
  }

  public static boolean isNthRootOfUnity(ExtensionField field, Polynomial polynomial, int rootOfUnity) {
    Polynomial powerPoly = field.power(polynomial, rootOfUnity);
    if (powerPoly.equals(new Polynomial(new int[]{1}, field.underlying))) {
      return true;
    }
    return false;
  }

  public static boolean hasNthRoot(ExtensionField field, Polynomial polynomial, int rootOfUnity) {
    for (int i = 1; i < field.underlying.prime - 1; i++) {
      Polynomial powerPoly = field.power(polynomial, i);
      if (powerPoly.equals(new Polynomial(new int[]{rootOfUnity}, field.underlying))) {
        return true;
      }
    }
    return false;
    //Polynomial powerPoly = field.power(polynomial, rootOfUnity);
    //if (powerPoly.equals(polynomial)) {
    //  return true;
    //}
    //return false;
  }

  public static Polynomial nthRoot(ExtensionField field, Polynomial polynomial, int n) {
    return field.power(polynomial, n);
  }
}
