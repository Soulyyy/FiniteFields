import org.junit.Test;
import utils.FiniteField;
import utils.Polynomial;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExtendedEuclideanTest {

  private static final FiniteField FINITE_FIELD = new FiniteField(7);

  @Test
  public void ExtendedEuclideanTest() {
    Polynomial first = new Polynomial(new int[]{1, 2, 1}, FINITE_FIELD);
    Polynomial second = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    Polynomial expectedResult = new Polynomial(new int[]{1, 1}, FINITE_FIELD);
    List<Polynomial[]> extendedSteps = first.extendedEuclidean(second);
    //assertEquals("Expecting a polynomial", expectedResult, first.divide(second)[0]);
  }
}
