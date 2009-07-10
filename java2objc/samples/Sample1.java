public class Sample1 {

  private final String value;

  public Sample1(String value) {
    this.value = value;
  }

  int labeledStatement() {
    int value = 4;
    for (int i = 0; i < value; ++i) {
      if (i == 2) {
        break;
      } else {
        continue;
      }
    }
    label: value = 1;
    return value;
  }
  public String getValue() {
    return value;
  }
  
  public String getAnotherValue(String value) {
    return "Value is: " + value;
  }
  
  public String callAMethod() {
    String value2 = getAnotherValue("foo");
    return value2;
  }
}
