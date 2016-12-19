package grs;

import java.util.Arrays;
import org.junit.Test;

public class GrsDecoderTests {

  @Test
  public void testFirstInput() {
    System.out.println(System.getProperty("user.dir"));
    GRS grs = IOUtils.readInputFile("src/main/resources/FirstInput.txt");
    int[][] mat = grs.getParityCheckMatrix();
    int[] syndrome = grs.computeSyndrome(mat);
    System.out.println(Arrays.toString(syndrome));
    System.out.println(grs);
  }

  public void printMatrix(int[][] matrix) {
    StringBuilder builder = new StringBuilder();
    for(int[] row : matrix) {
      for(int val : row) {
        builder.append(val).append(" ");
      }
      builder.append("\n");
    }
    System.out.println(builder.toString());
  }
}
