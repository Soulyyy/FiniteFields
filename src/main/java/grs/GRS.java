package grs;

import org.apache.commons.lang3.ArrayUtils;
import utils.FiniteField;
import utils.Polynomial;

import java.util.Arrays;

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
    int[][] unweightedParityCheckMatrix = new int[n - k][n];
    for (int i = 0; i < unweightedParityCheckMatrix.length; i++) {
      for (int j = 0; j < unweightedParityCheckMatrix[0].length; j++) {
        unweightedParityCheckMatrix[i][j] = field.power(parityRow[j], i);
      }
    }

    int[][] weightMatrix = new int[n][n];
    for (int i = 0; i < n; i++) {
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

  private int[] computeSyndrome(int[][] parityCheck) {
    int[][] transpose = new int[received.length][1];
    for (int i = 0; i < received.length; i++) {
      transpose[i][0] = received[i];
    }
    int[][] syndromeMat = multiplyMatrices(parityCheck, transpose);
    int[] syndrome = new int[syndromeMat.length];
    for (int i = 0; i < syndrome.length; i++) {
      syndrome[i] = syndromeMat[i][0];
    }
    return syndrome;
  }

  private Polynomial getSyndromePolynomial(int[] syndrome) {
    ArrayUtils.reverse(syndrome);
    return new Polynomial(syndrome, field);
  }

  private Polynomial[] extendedEuclidean(Polynomial a, Polynomial b) {
    Polynomial[] resp = new Polynomial[2];
    int baseDegree = a.degree();
    Polynomial remainderMinusOne = a;
    Polynomial remainderZero = b;
    Polynomial t1 = new Polynomial(new int[0], field);
    Polynomial t0 = new Polynomial(new int[]{1}, field);
    for (int i = 1; !remainderZero.equals(new Polynomial(new int[]{0}, field)); i++) {
      System.out.println("*****************************");
      Polynomial[] division = remainderMinusOne.divide(remainderZero);
      Polynomial otherQuotient = division[0].multiply(t0);
      Polynomial otherRemainder = t1.subtract(otherQuotient);
      System.out.println("r: (" + remainderMinusOne + ") = " + "(" + division[0] + ") * (" + remainderZero + ") + " + "(" + division[1] + ")");
      System.out.println("t: (" + t1 + ") = " + "(" + division[0] + ") * (" + t0 + ") + " + "(" + otherRemainder + ")");
      int degree = remainderZero.degree();
      if (degree < baseDegree / 2.0) {
        int c = t0.getPolynomial()[t0.getPolynomial().length - 1];
        System.out.println("C is: " + c);
        if (c != 1) {
          int inverse = field.findElementMultiplicativeInverse(c);
          resp[0] = t0.multiply(new Polynomial(new int[]{inverse}, field));
          resp[1] = remainderZero.multiply(new Polynomial(new int[]{inverse}, field));
        } else {
          resp[0] = t0;
          resp[1] = remainderZero;
        }
        System.out.println("SPOT: " + i);
        break;
      }
      //reassign now
      remainderMinusOne = remainderZero;
      remainderZero = division[1];
      t1 = t0;
      t0 = otherRemainder;
      System.out.println("Deg is: " + degree);
      System.out.println("New stop condition: " + remainderZero);
      System.out.println("FINISHED ITER: " + i);
    }
    return resp;
  }

  private int[] errorVector(Polynomial lambda, Polynomial gamma) {
    int[] error = new int[n];
    Polynomial lambdaDerivative = derivative(lambda);
    for (int i = 0; i < error.length; i++) {
      int alpha = parityRow[i];
      int v = weights[i];
      int multInverse = field.findElementMultiplicativeInverse(alpha);
      int lambdaVal = lambda.evaluate(multInverse);
      if (lambdaVal == 0) {
        int firstTerm = field.divide(-alpha, v);
        int secondTerm = field.divide(gamma.evaluate(multInverse), lambdaDerivative.evaluate(multInverse));
        error[i] = field.multiply(firstTerm, secondTerm);
      } else {
        error[i] = 0;
      }
    }
    return error;
  }

  private Polynomial derivative(Polynomial polynomial) {
    int[] newPoly = new int[polynomial.getPolynomial().length - 1];
    for (int i = 0; i < polynomial.getPolynomial().length - 1; i++) {
      newPoly[i] = field.multiply(polynomial.getPolynomial()[i], polynomial.degree() - i);
    }
    return new Polynomial(newPoly, field);
  }


  private int[] correct(int[] error) {
    int[] codeword = new int[error.length];
    for (int i = 0; i < codeword.length; i++) {
      codeword[i] = field.subtract(received[i], error[i]);
    }
    return codeword;
  }


  public int[] decode() {
    int[][] mat = getParityCheckMatrix();
    int[] syndrome = computeSyndrome(mat);
    Polynomial syndromePolynomial = getSyndromePolynomial(syndrome);
    int[] grsCoefs = new int[n - k + 1];
    grsCoefs[0] = 1;
    Polynomial[] euclid = extendedEuclidean(new Polynomial(grsCoefs, field), syndromePolynomial);
    int[] errorVector = errorVector(euclid[0], euclid[1]);
    return correct(errorVector);
  }
}
