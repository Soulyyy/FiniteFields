package diffie;

import utils.Polynomial;

public class GeneratorFinder {

  public static int findDiffieGenerator(DiffieHellman diffieHellman) {
    Polynomial polynomial = diffieHellman.getPolynomial();
    System.out.println(polynomial.toString());
    int[] allFieldValues = diffieHellman.getFiniteField().getAllFieldElements();
/*    for(int i : allFieldValues) {
      System.out.println(i);
    }*/


    return 0;
  }
}
