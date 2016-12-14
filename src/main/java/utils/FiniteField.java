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

  public int[] getAllFieldElements() {
    int[] allValues = new int[prime];
    int multiplicativeUnit = 1;
    int additiveUnit = 0;
    for (int i = 0; i < allValues.length; i++) {
      allValues[i] = additiveUnit + multiplicativeUnit * i;
    }
    return forceToField(allValues);
  }
}
