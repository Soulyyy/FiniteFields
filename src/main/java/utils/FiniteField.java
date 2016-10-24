package utils;

public class FiniteField {

  int prime;

  public FiniteField(int prime) {
    this.prime = prime;
  }

  public int[] forceToField(int[] ints) {
    int[] resp = new int[ints.length];
    for (int i = 0; i < ints.length; i++) {
      resp[i] = ((ints[i] % prime) + prime) % prime;
    }
    return resp;
  }
}
