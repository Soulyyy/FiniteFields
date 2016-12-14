package diffie;

import utils.FiniteField;
import utils.Polynomial;

public class Main {

  public static void main(String[] args) {
    FiniteField finiteField = new FiniteField(2671);
    Polynomial polynomial = new Polynomial(new int[]{1, 0, 171, 853}, finiteField);
    DiffieHellman diffieHellman = new DiffieHellman(polynomial, finiteField);
    GeneratorFinder.findDiffieGenerator(diffieHellman);
  }
}
