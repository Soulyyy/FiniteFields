import org.junit.Test;
import utils.ExtensionField;
import utils.FiniteField;
import utils.Polynomial;

import static org.junit.Assert.assertEquals;

public class ExtensionFieldSummationTest {

  @Test
  public void testSumSameDegree() {
    FiniteField finiteField = new FiniteField(3);
    ExtensionField field = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1, 0, 2}, finiteField));


    Polynomial first = new Polynomial(new int[]{1, 1, 1, 1}, finiteField);
    Polynomial moduloField = field.getPolynomialInField(first);
    assertEquals("The polynomial should be modulo the field polynomial",new Polynomial(new int[]{1,2}, finiteField), moduloField);
    assertEquals("The sum of this polynomial should also be the sum in the modulo field",
        new Polynomial(new int[]{2,1}, finiteField), field.getPolynomialInField(first.add(first)));

    assertEquals("The sum of the two polynomials is correct", new Polynomial(new int[]{1, 2}, finiteField), field.add(first, first));
  }

  @Test
  public void testAdditionWithZero() {
    FiniteField finiteField = new FiniteField(3);
    ExtensionField field = new ExtensionField(finiteField, new Polynomial(new int[]{1, 1, 0, 2}, finiteField));

    Polynomial polynomial = new Polynomial(new int[]{1,0,0,0}, finiteField);

    assertEquals("The sum should be the same", new Polynomial(new int[]{2,0,1}, finiteField), field.add(polynomial, new Polynomial(new int[]{}, finiteField)));
  }
}
