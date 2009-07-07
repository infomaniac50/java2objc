public class Sample1 {

  private final String value;

  public Sample1(String value) {
    this.value = value;
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
