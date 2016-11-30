import org.junit.Test;
import utils.ExtensionField;
import utils.FiniteField;
import utils.GeneralOperation;
import utils.Polynomial;

import java.util.List;

public class GenerateAllValuesTest {

  @Test
  public void testAllElementGeneration() {
    FiniteField field = new FiniteField(3);
    ExtensionField extensionField = new ExtensionField(field, new Polynomial(new int[]{1, 1, 0, 2}, field));
    List<Polynomial> polynomialList = GeneralOperation.getAllFieldElements(extensionField);
    for (Polynomial polynomial : polynomialList) {
      System.out.println(polynomial);
    }
    System.out.println(polynomialList.size());
    System.out.println(GeneralOperation.smallestRootOfUnity(extensionField, new Polynomial(new int[]{1, 1, 1, 1}, field)));
    for (Polynomial polynomial : polynomialList) {
      System.out.println(GeneralOperation.smallestRootOfUnity(extensionField, polynomial));
    }
  }
}
