import org.junit.Test;
import utils.ExtensionField;
import utils.FiniteField;
import utils.GeneralOperation;
import utils.Polynomial;

import java.util.*;

public class FiniteFieldsHomework4 {

  @Test
  public void exercise1() {
    FiniteField field = new FiniteField(2);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 0, 1, 0, 0, 1}, field));
    List<Polynomial> polynomialList = GeneralOperation.getAllFieldElements(extensionField);
    int expectedSize = 31;
    List<Polynomial> possibleBasePolys = new ArrayList<>();
    for (Polynomial polynomial : polynomialList) {
      if (GeneralOperation.smallestRootOfUnity(extensionField, polynomial) == expectedSize) {
        possibleBasePolys.add(polynomial);
      }
      System.out.println(polynomial.toString() + ": " + GeneralOperation.smallestRootOfUnity(extensionField, polynomial));
    }

    Polynomial polynomial = new Polynomial(new int[]{1, 0}, field);
    Polynomial product = new Polynomial(new int[]{1}, field);
    Set<Polynomial> allPolys = new HashSet<>();

    for (int i = 0; i <= expectedSize; i++) {
      Polynomial newPoly = product;
      allPolys.add(newPoly);
      product = extensionField.multiply(product, polynomial);
    }
    System.out.println("The size is: " + allPolys.size());
    for (Polynomial poly : allPolys) {
      System.out.println(poly.toString());
    }
    System.out.println();
    System.out.println("```````````````````````````````````````````");
    System.out.println("Create base");
    for (int i = 0; i < extensionField.getDegree(); i++) {
      Polynomial poly = extensionField.power(polynomial, i);
      System.out.println(poly.toString());
    }
    System.out.println();
    System.out.println("```````````````````````````````````````````");
    System.out.println(GeneralOperation.computeTrace(polynomial, extensionField));
    List<Polynomial> somePotentialBasis = createBase(polynomial, extensionField);
    System.out.println();
    System.out.println("```````````````````````````````````````````");
    System.out.println("AM I A REAL BASIS :)((((");
    System.out.println(isOrthonormalBasis(somePotentialBasis, extensionField));
    //List<Polynomial> originalBasis = createBase(polynomial, extensionField);
    for (Polynomial originalBasisPoly : possibleBasePolys) {
      for (Polynomial baseElement : possibleBasePolys) {
        if (originalBasisPoly.equals(baseElement)) {
          //System.out.println("Are equal" + originalBasisPoly.toString() + " " + baseElement.toString());
          continue;
        }
        List<Polynomial> originalBasis = createBase(originalBasisPoly, extensionField);
        //Collections.sort(originalBasis);
        //System.out.println(baseElement);
        List<Polynomial> dualBasis = createBase(baseElement, extensionField);
        //Collections.sort(dualBasis);
        System.out.println("Considering basis: " + originalBasis.toString() + "and: " + dualBasis.toString());
        System.out.println(isDualBase(originalBasis, dualBasis, extensionField));
        if (isDualBase(originalBasis, dualBasis, extensionField)) {
          System.out.println("DUAL");
          for (Polynomial dualBasisElement : dualBasis) {
            System.out.println(dualBasisElement.toString());
          }
        }
      }
    }
  }

  @Test
  public void ex1Again() {
    FiniteField field = new FiniteField(2);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 0, 1, 0, 0, 1}, field));
    List<Polynomial> polynomialList = GeneralOperation.getAllFieldElements(extensionField);
    int expectedSize = 31;
    List<Polynomial> possibleBasePolys = new ArrayList<>();
    for (Polynomial polynomial : polynomialList) {
      if (GeneralOperation.smallestRootOfUnity(extensionField, polynomial) == expectedSize) {
        possibleBasePolys.add(polynomial);
      }
      System.out.println(polynomial.toString() + ": " + GeneralOperation.smallestRootOfUnity(extensionField, polynomial));
    }
    System.out.println("*****************************************");
    for (Polynomial polynomial : possibleBasePolys) {
      System.out.println(polynomial);
    }
    System.out.println("*****************************************");
    for (Polynomial polynomial : possibleBasePolys) {
      for (Polynomial polynomial2 : possibleBasePolys) {
        List<Polynomial> basis = createBase(polynomial, extensionField);
        List<Polynomial> secondBasis = createBase(polynomial2, extensionField);
        System.out.println(GeneralOperation.isDual(basis, secondBasis, extensionField));
        System.out.println(basis);
      }
    }
  }

  @Test
  public void exercise2() {
    FiniteField field = new FiniteField(2);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 0, 0, 1, 1}, field));
    List<Polynomial> polynomialList = GeneralOperation.getAllFieldElements(extensionField);
    int expectedSize = 15;
    List<Polynomial> possibleBasePolys = new ArrayList<>();
    for (Polynomial polynomial : polynomialList) {
      if (GeneralOperation.smallestRootOfUnity(extensionField, polynomial) == expectedSize) {
        possibleBasePolys.add(polynomial);
      }
      System.out.println(polynomial.toString() + ": " + GeneralOperation.smallestRootOfUnity(extensionField, polynomial));
    }
    System.out.println("*****************************************");
    for (Polynomial polynomial : possibleBasePolys) {
      System.out.println(polynomial);
    }
    System.out.println("*****************************************");
    for (Polynomial polynomial : possibleBasePolys) {
      List<Polynomial> basis = createBase(polynomial, extensionField);
      //System.out.println(GeneralOperation.isSelfDual(basis, extensionField));
      Set<List<Polynomial>> allDuals = generateAllDuals(basis, extensionField);
      for(List<Polynomial> dual : allDuals) {
        Collections.sort(dual);
        Collections.sort(basis);
        System.out.println(basis.equals(dual));
        System.out.println(basis);
        System.out.println(dual);
        if(basis.equals(dual)) {
          System.out.println(basis);
        }
      }
      //System.out.println(basis);
    }


  }

  private static boolean isDualBase(List<Polynomial> firstBase, List<Polynomial> secondBase, ExtensionField field) {
    if (firstBase.size() != secondBase.size()) {
      throw new IllegalStateException("Bases must be of equal size!");
    }
    for (int i = 0; i < firstBase.size(); i++) {
      int acc = 0;
      for (int j = 0; j < secondBase.size(); j++) {
        //System.out.println("I is : " + i + " and J is: " + j);
        //System.out.println("Multiplying: " + firstBase.get(i).toString() + " and " + secondBase.get(j).toString());
        Polynomial testPoly = field.multiply(firstBase.get(i), secondBase.get(j));
        printArray(GeneralOperation.elementTrace(testPoly, field));
        //System.out.println("Result is: " + testPoly);
        //System.out.println(testPoly);
        int trace = GeneralOperation.computeTrace(testPoly, field);
        //System.out.println(trace);
        if (trace == 1) {
          acc++;
        }
      }

      System.out.println("ACC IS:" + acc);
      if (acc != 1) {
        return false;
      }
    }
    return true;
  }

  private static List<Polynomial> createBase(Polynomial polynomial, ExtensionField field) {
    List<Polynomial> base = new ArrayList<>();
    System.out.println(field.getDegree());
    for (int i = 0; i < field.getDegree(); i++) {
      base.add(field.power(polynomial, i));
    }
    return base;
  }

  private static boolean isOrthonormalBasis(List<Polynomial> basis, ExtensionField field) {
    for (int i = 0; i < basis.size(); i++) {
      for (int j = i; j < basis.size(); j++) {
        System.out.println("Multiplying: " + basis.get(i).toString() + " and " + basis.get(j).toString());
        Polynomial testPoly = field.multiply(basis.get(i), basis.get(j));
        System.out.println(testPoly);
        int trace = GeneralOperation.computeTrace(testPoly, field);
        System.out.println(trace);
        if (i != j) {
          if (trace != 0) {
            System.out.println("is 0");
            return false;
          }
        } else {
          if (trace != 1) {
            System.out.println("is 1");
            return false;
          }
        }
      }
    }
    return true;
  }

  private static void printArray(int[] ar) {
    StringBuilder sb = new StringBuilder();
    for (int i : ar) {
      sb.append(i).append(", ");
    }
    System.out.println(sb.toString());
  }

  private static Set<List<Polynomial>> generateAllDuals(List<Polynomial> basis, ExtensionField field) {
    boolean isNewPolys = true;
    Set<List<Polynomial>> resp = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      List<Polynomial> dual = generateDual(basis, field);
      resp.add(dual);
    }
    return resp;
  }

  private static List<Polynomial> generateDual(List<Polynomial> basis, ExtensionField field) {
    //Recursive construction
    List<Polynomial> allPolynomials = GeneralOperation.getAllFieldElements(field);
    java.util.Collections.shuffle(allPolynomials);
    List<Polynomial> dualBasis = new ArrayList<>();
    for (Polynomial polynomial : allPolynomials) {
      int count = 0;
      for (Polynomial basisElement : basis) {
        Polynomial testPoly = field.multiply(polynomial, basisElement);
        int trace = GeneralOperation.computeTrace(testPoly, field);
        if (trace == 1) {
          count++;
        } else if (trace != 0) {
          continue;
        }
      }
      if (count == 1) {
        dualBasis.add(polynomial);
      }
      if (dualBasis.size() >= field.getDegree()) {
        return dualBasis;
      }
    }
    return dualBasis;
  }

  private static boolean containsEquivalentToThisPoint(List<List<Polynomial>> allBasis, List<Polynomial> candidate) {
    for (List<Polynomial> basis : allBasis) {
      int nrEquals = 0;
      for (Polynomial basisPoly : basis) {
        boolean isEqual = false;
        for (Polynomial element : candidate) {
          if (basisPoly.equals(element)) {
            isEqual = true;
            break;
          }
        }
        if (isEqual) {
          nrEquals++;
        }
      }
      if (nrEquals == basis.size()) {
        return false;
      }
    }
    return true;
  }

  /*private static List<Polynomial> generateSelfDual(ExtensionField field) {
    List<Polynomial> allPolynomials = GeneralOperation.getAllFieldElements(field);
    List<Polynomial> dualBasis = new ArrayList<>();
    for (Polynomial polynomial : allPolynomials) {

    }
  }

  private static List<Polynomial> selfDualSubtask(List<Polynomial> options, List<Polynomial> currentBase, ExtensionField field) {
    for (Polynomial polynomial : options) {
      boolean fail = false;
      for (Polynomial basePoly : currentBase) {
        Polynomial testPoly = field.multiply(polynomial, basePoly);
        int trace = GeneralOperation.computeTrace(testPoly, field);
        if (trace != 0) {
          fail = true;
          break;
        }
      }
      if (!fail) {
        currentBase.add(polynomial);
        List<Polynomial> newOptions = options.remove(polynomial);
        return selfDualSubtask(options.remove(polynomial), currentBase, field);
      }

    }
  }*/
}
