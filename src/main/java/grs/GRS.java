package grs;

import java.util.Arrays;

import utils.FiniteField;

public class GRS {

  FiniteField field;
  int fieldSize;
  int n;
  int k;

  Integer[] parityRow;
  Integer[] weights;

  public Integer[] received;

  public GRS(int fieldSize, int n, int k, Integer[] parityRow, Integer[] weights, Integer[] received) {
    this.field = new FiniteField(fieldSize);
    this.fieldSize = fieldSize;
    this.n = n;
    this.k = k;
    this.parityRow = parityRow;
    this.weights = weights;
    this.received = received;
  }

  @Override
  public String toString() {
    return "GRS{" +
        "fieldSize=" + fieldSize +
        ", n=" + n +
        ", k=" + k +
        ", parityRow=" + Arrays.toString(parityRow) +
        ", weights=" + Arrays.toString(weights) +
        ", received=" + Arrays.toString(received) +
        '}';
  }

  public int[][] getParityCheckMatrix() {
    int[][] unweightedParityCheckMatrix = new int[n-k][n];
    for(int i = 0; i < unweightedParityCheckMatrix.length; i++) {
      for (int j = 0; j < unweightedParityCheckMatrix[0].length; j++) {
        unweightedParityCheckMatrix[i][j] = field.power(parityRow[j], i);
      }
    }

    int[][] weightMatrix = new int[n][n];
    for(int i = 0; i < n; i++) {
      weightMatrix[i][i] = weights[i];
    }
    return multiplyMatrices(unweightedParityCheckMatrix, weightMatrix);
  }

  private int[][] multiplyMatrices(int[][] first, int[][] second) {
    if (first[0].length != second.length) {
      throw new IllegalStateException("Matrix length mismatch");
    }
    int[][] response = new int[first.length][second[0].length];
    for (int i = 0; i < first.length; i++) {
      for (int j = 0; j < second[0].length; j++) {
        int element = 0;
        for (int h = 0; h < second.length; h++) {
          element += field.multiply(first[i][h], second[h][j]);
        }
        response[i][j] = field.forceElementToField(element);
      }
    }
    return response;
  }

  public int[] computeSyndrome(int[][] parityCheck) {
    int[][] transpose = new int[received.length][1];
    for(int i = 0; i < received.length; i++) {
      transpose[i][0] = received[i];
    }
    int[][] syndromeMat = multiplyMatrices(parityCheck, transpose);
    int[] syndrome = new int[syndromeMat.length];
    for(int i = 0; i < syndrome.length; i++) {
      syndrome[i] = syndromeMat[i][0];
    }
    return syndrome;
  }

}
