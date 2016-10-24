import org.junit.Test;
import utils.FiniteField;
import utils.Polynomial;

import static org.junit.Assert.assertEquals;

public class PolynomialMultiplicationTest {

  private static final FiniteField FINITE_FIELD = new FiniteField(7);

  @Test
  public void multiplyByOne() {
    Polynomial first = new Polynomial(new int[]{3, 2, 2}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{1}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{3, 2, 2}, FINITE_FIELD);
    assertEquals("Expecting a polynomial", expectedResult, first.multiply(second));
  }

  @Test
  public void multiplyEqualLengthTest() {
    Polynomial first = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{1, 2}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{1, 3, 2}, FINITE_FIELD);
    assertEquals("Expecting a polynomial", expectedResult, first.multiply(second));
  }

  @Test
  public void multiplyDifferentLengthTest() {
    Polynomial first = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{2, 1, 1}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{2, 3, 2, 1}, FINITE_FIELD);
    assertEquals("Expecting a polynomial", expectedResult, first.multiply(second));
  }

  @Test
  public void multiplyByNegativeTest() {
    Polynomial first = new Polynomial(new int[]{3, 2}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{-2, -1}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{1, 0, 5}, FINITE_FIELD);
    assertEquals("Expecting a polynomial", expectedResult, first.multiply(second));
  }
}