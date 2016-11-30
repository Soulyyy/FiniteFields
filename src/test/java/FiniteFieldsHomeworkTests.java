import java.util.List;
import org.junit.Test;

import utils.ExtensionField;
import utils.FiniteField;
import utils.GeneralOperation;
import utils.Polynomial;

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
    for (Polynomial polynomial : polynomials) {
      printRootOfUnity(extensionField, polynomial);
    }
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
      System.out.println(polynomial.toString() + " is a "+n+"th root of unity");
    }
  }

  @Test
  public void thirdExercise() {
    FiniteField finiteField = new FiniteField(5);
    ExtensionField extensionField = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1, 1}, finiteField));
    List<Polynomial> polynomials = GeneralOperation.getAllFieldElements(extensionField);
    for (Polynomial polynomial : polynomials) {
      printIfElementHasNthRootOfUnity(extensionField, polynomial, 3);
    }
    System.out.println(polynomials.size());
  }

  @Test
  public void fourthExercise() {
    for (int i = 2; i <= 99; i++) {
      FiniteField finiteField = new FiniteField(i);
      ExtensionField extensionField = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1}, finiteField));
      Polynomial inverse = new Polynomial(new int[]{1}, finiteField).additiveInverse();
      System.out.println(inverse);
      printIfElementHasNthRootOfUnity(extensionField, inverse, 4);
      printIfElementHasNthRootOfUnity(extensionField, inverse, 5);
    }
  }


}
