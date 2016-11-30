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

  public static int whichRootOfUnity(ExtensionField field, Polynomial polynomial) {
    for(int i = 1; i <= field.polynomial.degree() * field.underlying.prime * 2; i++) {
      Polynomial powerPoly = field.power(polynomial, i);
      if(powerPoly.equals(new Polynomial(new int[]{1}, field.underlying))){
        return i;
      }
    }
    return -1;
  }
}
