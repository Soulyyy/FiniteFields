import org.junit.Test;
import utils.FiniteField;
import utils.Polynomial;

import static org.junit.Assert.assertEquals;

public class PolynomialSubtractionTest {


  private static FiniteField finiteField = new FiniteField(7);

  @Test
  public void testSubtractSameLength() {
    Polynomial first = new Polynomial(new int[]{3, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{2, 1, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{1, 1, 1}, finiteField);
    assertEquals("Expecting a polynomial x^2 + x^", expectedResult, first.subtract(second));
  }

  @Test
  public void testSubtractBiggerFromSmaller() {
    Polynomial first = new Polynomial(new int[]{3, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{1, 2, 1, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{6, 1, 1, 1}, finiteField);
    assertEquals("Expecting a polynomial x^2 + x^", expectedResult, first.subtract(second));
  }

  @Test
  public void testSubtractSmallerFromBigger() {
    Polynomial first = new Polynomial(new int[]{1, 3, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{2, 1, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{1, 1, 1, 1}, finiteField);
    assertEquals("Expecting a polynomial x^2 + x^", expectedResult, first.subtract(second));
  }

  @Test
  public void testNegativeSubtraction() {
    Polynomial first = new Polynomial(new int[]{-1, 3, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{2, 1, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{6, 1, 1, 1}, finiteField);
    assertEquals("Expecting a polynomial x^2 + x^", expectedResult, first.subtract(second));
  }

  @Test
  public void testSizeReductionSubtraction() {
    Polynomial first = new Polynomial(new int[]{1, 3, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{1, 2, 1, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{1, 1, 1}, finiteField);
    assertEquals("Expecting a polynomial x^2 + x^", expectedResult, first.subtract(second));
  }
}
