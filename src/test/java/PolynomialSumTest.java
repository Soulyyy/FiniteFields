import org.junit.Test;
import utils.FiniteField;
import utils.Polynomial;

import static org.junit.Assert.assertEquals;

public class PolynomialSumTest {

  @Test
  public void testSumSameDegree() {
    FiniteField finiteField = new FiniteField(5);
    Polynomial first = new Polynomial(new int[]{2, 2, 2}, finiteField);
    Polynomial second = new Polynomial(new int[]{3, 3, 3}, finiteField);
    assertEquals("The result should be a zero polynomial", new Polynomial(new int[]{0,0,0}, finiteField), first.add(second));
  }

  @Test
  public void testSUmDifferentDegree() {
    FiniteField finiteField = new FiniteField(7);
    Polynomial first = new Polynomial(new int[] {2,2,2,2}, finiteField);
    Polynomial second = new Polynomial(new int[] {2,2,8}, finiteField);
    assertEquals("The result should be 2x^3 + 4x^2 + 4x^1 + 3", new Polynomial(new int[]{2,4,4,3}, finiteField), first.add(second));
  }
}
