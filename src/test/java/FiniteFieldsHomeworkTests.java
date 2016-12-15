import org.junit.Test;
import utils.ExtensionField;
import utils.FiniteField;
import utils.GeneralOperation;
import utils.Polynomial;

import java.util.*;

public class FiniteFieldsHomeworkTests {

  @Test
  public void firstExercise() {
    FiniteField field = new FiniteField(3);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 1, 0, 2}, field));
    List<Polynomial> polynomialList = GeneralOperation.getAllFieldElements(extensionField);
    for (Polynomial polynomial : polynomialList) {
      System.out.println(polynomial.toString() + ": " + GeneralOperation.smallestRootOfUnity(extensionField, polynomial));
    }
  }

  @Test
  public void secondExercise() {
    FiniteField finiteField = new FiniteField(29);
    ExtensionField extensionField = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1}, finiteField));
    List<Polynomial> polynomials = GeneralOperation.getAllFieldElements(extensionField);
    Set<Polynomial> seventhRoots = new HashSet<>();
    Set<Polynomial> eightRoots = new HashSet<>();
    Set<Polynomial> ninthRoots = new LinkedHashSet<>();
    List<Polynomial> list = new ArrayList<Polynomial>(ninthRoots);
    for (Polynomial polynomial : polynomials) {
      seventhRoots.add(GeneralOperation.nthRoot(extensionField, polynomial, 7));
      eightRoots.add(GeneralOperation.nthRoot(extensionField, polynomial, 8));
      ninthRoots.add(GeneralOperation.nthRoot(extensionField, polynomial, 9));
      //System.out.println(polynomial.toString() + ": " + GeneralOperation.hasNthRoot(extensionField, polynomial, 8));

    }
    System.out.println("seventh");
    for(Polynomial polynomial : seventhRoots) {
      System.out.println(polynomial);
    }
    System.out.println("eight");
    for(Polynomial polynomial : eightRoots) {
      System.out.println(polynomial);
    }
    System.out.println("ninth");
    for(Polynomial polynomial : list) {
      System.out.println(polynomial);
    }
    System.out.println(28.0/gcd(7,28));
    System.out.println(28.0/gcd(8,28));
    System.out.println(28.0/gcd(9,28));
  }

  private void printRootOfUnity(ExtensionField field, Polynomial polynomial) {
    if (GeneralOperation.isNthRootOfUnity(field, polynomial, 7)) {
      System.out.println(polynomial.toString() + " is a 7th root of unity");
    }
    if (GeneralOperation.isNthRootOfUnity(field, polynomial, 8)) {
      System.out.println(polynomial.toString() + " is a 8th root of unity");
    }
    if (GeneralOperation.isNthRootOfUnity(field, polynomial, 9)) {
      System.out.println(polynomial.toString() + " is a 9th root of unity");
    }
  }

  private void printIfElementHasNthRootOfUnity(ExtensionField field, Polynomial polynomial, int n) {
    if (GeneralOperation.isNthRootOfUnity(field, polynomial, n)) {
      System.out.println(polynomial.toString() + " is a " + n + "th root of unity");
    }
  }

  @Test
  public void thirdExercise() {
    FiniteField finiteField = new FiniteField(5);
    ExtensionField extensionField = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1, 1}, finiteField));
    List<Polynomial> polynomials = GeneralOperation.getAllFieldElements(extensionField);
    Set<Polynomial> thirdRoots = new HashSet<>();
    for (Polynomial polynomial : polynomials) {
      //printIfElementHasNthRootOfUnity(extensionField, polynomial, 3);
      thirdRoots.add(GeneralOperation.nthRoot(extensionField, polynomial, 3));

    }
    for(Polynomial polynomial : thirdRoots) {
      System.out.print(polynomial+", ");
    }
    System.out.println("");
    System.out.println(polynomials.size());
  }

  @Test
  public void fourthExercise() {
    for (int i = 2; i <= 99; i++) {
      FiniteField finiteField = new FiniteField(i);
      ExtensionField extensionField = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1}, finiteField));
      Polynomial inverse = new Polynomial(new int[]{1}, finiteField).additiveInverse();
      //System.out.println(inverse);
      printIfElementHasNthRootOfUnity(extensionField, inverse, 4);
    }

    List<Integer> ints = new ArrayList<>();
    for (int i = 1; i < 100; i++) {
      if (isPrime(i)) {
        ints.add(i);
        int j = 2;
        while (Math.pow(i, j) < 100) {
          ints.add((int) Math.round(Math.pow(i, j)));
          j++;
        }
      }
    }
    Collections.sort(ints);
    for (int i : ints) {
      System.out.print(i + ", ");
    }
  }

  @Test
  public void seventhExercise() {
    FiniteField field = new FiniteField(3);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 1, 0, 2}, field));
    List<Polynomial> polynomials = GeneralOperation.getAllFieldElements(extensionField);
    Polynomial sum = new Polynomial(new int[]{0}, field);
    for (Polynomial polynomial : polynomials) {
      printIfElementHasNthRootOfUnity(extensionField, polynomial, 13);
      if (GeneralOperation.isNthRootOfUnity(extensionField, polynomial, 13)) {
        sum.add(polynomial.multiply(polynomial));
      }
    }
    System.out.println(polynomials.size());
    System.out.println(sum.toString());
  }

  @Test
  public void exerciseSix() {
    int count = 0;
    for (int i = 3; i < 1000; i++) {
      if(count >= 11) {
        break;
      }
      if (isPrime(i)) {
        //System.out.println(i);
        if(isValidSolution(2, i)) {
          System.out.println(i);
          count++;
        }
        //System.out.println(isValidSolution(2, i));
      }
    }
  }

  @Test
  public void tmpTest() {
    System.out.println(phi(7));
  }

  private int gcd(int a, int b) {
    if (a == 0) {
      return b;
    }
    return gcd(b % a, a);
  }

  private int phi(int n) {
    int resp = 1;
    for (int i = 2; i < n; i++) {
      if (gcd(i, n) == 1) {
        resp++;
      }
    }
    return resp;
  }

  private boolean isValidSolution(int el, int n) {
    for (int i = 2; i < phi(n); i++) {
      int val = (int) Math.round(Math.pow(el, i));
      if (val % n == 1) {
        return false;
      }
    }
    return (int) Math.round(Math.pow(el, phi(n))) % n == 1;
  }

  private static boolean isPrime(int num) {
    if (num < 2) return false;
    if (num == 2) return true;
    if (num % 2 == 0) return false;
    for (int i = 3; i * i <= num; i += 2)
      if (num % i == 0) return false;
    return true;
  }

}
