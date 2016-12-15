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
      if (powerPoly.equals(new Polynomial(new int[]{1}, field.underlying))) {
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

  public static int computeTrace(Polynomial primitiveRoot, ExtensionField field) {
    Polynomial accumulator = new Polynomial(new int[]{0}, field.underlying);
    for (int i = 0; i < field.polynomial.degree(); i++) {
      int power = (int) Math.pow(field.underlying.prime, i);
      Polynomial powerOfPrimitiveRoot = field.power(primitiveRoot, power);
      //System.out.println("While computing trace, got: " + primitiveRoot + " to the power of " + power + " is: " + powerOfPrimitiveRoot);
      accumulator = accumulator.add(powerOfPrimitiveRoot);
      //System.out.println("Accumulator is: " + accumulator.toString());
      //accumulator += powerOfPrimitiveRoot.getPolynomial()[powerOfPrimitiveRoot.getPolynomial().length - 1];
    }
    accumulator = field.getPolynomialInField(accumulator);
    //System.out.println(accumulator);
    if (accumulator.getPolynomial().length == 0) {
      return 0;
    }
    return accumulator.getPolynomial()[accumulator.getPolynomial().length - 1];
  }

  public static int[] elementTrace(Polynomial root, ExtensionField field) {
    int[] traces = new int[root.polynomial.length];
    for (int i = 0; i < root.polynomial.length; i++) {
      traces[i] = computeValueTrace(root.polynomial[i], field);
    }
    return traces;
  }

  public static int computeValueTrace(int value, ExtensionField field) {
    int accumulator = 0;
    for (int i = 0; i < field.polynomial.degree(); i++) {
      int power = (int) Math.pow(field.underlying.prime, i);
      accumulator += Math.pow(value, power);
    }
    accumulator = field.underlying.forceElementToField(accumulator);
    return accumulator;
  }

  public static boolean isTraceOrthogonal(List<Polynomial> firstBasis, List<Polynomial> secondBasis) {
    return false;
  }

  public static boolean isSelfDual(List<Polynomial> basis, ExtensionField field) {
    for (Polynomial polynomial : basis) {
      Polynomial product = field.multiply(polynomial, polynomial);
      int trace = trace(product, field);
      System.out.println("TRACE IS: " + trace);
/*      if (trace != 1) {
        return false;
      }*/
    }
    return true;
  }

  public static boolean isDual(List<Polynomial> firstBasis, List<Polynomial> secondBasis, ExtensionField field) {
    for (Polynomial fistBasisPolynomial : firstBasis) {
      int matches = 0;
      for (Polynomial secondBasisPolynomial : secondBasis) {
        Polynomial product = field.multiply(fistBasisPolynomial, secondBasisPolynomial);
        int trace = trace(product, field);
        if (trace != 0) {
          matches++;
        }
      }
      if (matches != 1) {
        System.out.println("Not a dual basis: " + matches);
        return false;
      }
    }
    return true;
  }

  public static int trace(Polynomial polynomial, ExtensionField field) {
    Polynomial accumulator = new Polynomial(new int[]{0}, field.underlying);
    for (int i = 0; i < field.getDegree(); i++) {
      int power = (int) Math.pow(field.underlying.prime, i);
      Polynomial powerOfPrimitiveRoot = field.power(polynomial, power);
      accumulator = accumulator.add(powerOfPrimitiveRoot);
    }
    accumulator = field.getPolynomialInField(accumulator);
    if (accumulator.getPolynomial().length == 0) {
      return 0;
    }
    return accumulator.getPolynomial()[accumulator.getPolynomial().length - 1];
  }
}


