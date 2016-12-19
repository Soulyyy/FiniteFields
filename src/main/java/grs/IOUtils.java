package grs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class IOUtils {

  public static GRS readInputFile(String fileName) {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
      String fieldSizeString = bufferedReader.readLine();
      String parameters = bufferedReader.readLine();
      String parityLine = bufferedReader.readLine();
      String vectorLine = bufferedReader.readLine();
      String yLine = bufferedReader.readLine();
      int fieldSize = Integer.parseInt(fieldSizeString);
      int n = Integer.parseInt(parameters.split(" ")[0]);
      int k = Integer.parseInt(parameters.split(" ")[1]);
      Integer[] parity = parseStringVector(parityLine);
      Integer[] vector = parseStringVector(vectorLine);
      Integer[] y = parseStringVector(yLine);
      return new GRS(fieldSize, n, k, parity, vector, y);
    }
    catch (Exception e) {
      throw new IllegalStateException("Should not fail", e);
    }
  }

  public void writeOutputFile() {

  }

  private static Integer[] parseStringVector(String string) {
    String[] strings = string.split(" ");
    return Arrays.stream(strings).map(Integer::parseInt).toArray(Integer[]::new);
  }
}
