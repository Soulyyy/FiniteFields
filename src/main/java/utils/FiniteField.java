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

  public int findElementMultiplicativeInverse(int element) {
    if(element == 0) {
      return 1;
    }
    for (int i = 0; i < prime; i++) {
      if((((element * i) % prime) + prime) % prime == 1) {
        return i;
      }
    }
    throw new IllegalStateException("There must be a multiplicative inverse element");
  }

  public int forceElementToField(int element) {
    return ((element % prime) + prime) % prime;
  }
}
