import org.junit.Test;
import utils.FiniteField;
import utils.Polynomial;

import static org.junit.Assert.assertEquals;

public class PolynomialGCDTest {

  private static final FiniteField FINITE_FIELD = new FiniteField(7);

  @Test
  public void gcd() {
    Polynomial first = new Polynomial(new int[]{1, 2, 1}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    assertEquals("Expecting a polynomial", expectedResult, first.gcd(second));
  }

  @Test
  public void gcd2() {
    FiniteField finiteField = new FiniteField(2);
    Polynomial first = new Polynomial(new int[]{1, 1, 0, 0, 1, 1}, finiteField);
    Polynomial second = new Polynomial(new int[]{1, 0, 1, 0, 1}, finiteField);
    Polynomial expectedResult = new Polynomial(new int[]{1}, finiteField);
    assertEquals("Expecting a polynomial", expectedResult, first.gcd(second));
  }
}
