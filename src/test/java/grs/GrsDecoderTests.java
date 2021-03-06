package grs;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GrsDecoderTests {

  @Test
  public void testFirstInput() {
    System.out.println(System.getProperty("user.dir"));
    GRS grs = IOUtils.readInputFile("src/main/resources/FirstInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
  }

  @Test
  public void testSecondInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/SecondInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
  }

  @Test
  public void testThirdInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/ThirdInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
  }

  @Test
  public void testFourthInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/FourthInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
  }

  @Test
  public void testSixthInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/SixthInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
  }

  @Test
  public void testSeventhInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/SeventhInput.txt");
    int[] decoded = grs.decode();
    System.out.println(Arrays.toString(decoded));
    StringBuilder builder = new StringBuilder();
    for (int i: decoded) {
      builder.append(i).append(" ");
    }
    System.out.println(builder.toString().trim());
  }

  @Test
  public void testInput() {
    GRS grs = IOUtils.readInputFile("src/main/resources/Input.txt");
    int[] decoded = grs.decode();
    int[] output = ArrayUtils.toPrimitive(IOUtils.readOutputFile("src/main/resources/Output.txt"));
    System.out.println(Arrays.toString(decoded));
    System.out.println(Arrays.toString(output));
    Assert.assertArrayEquals(decoded, output);
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
