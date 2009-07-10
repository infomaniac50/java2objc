public class MethodDemo {

  public static int staticMethod(int a, int b) {
    return a+b;
  }

  public static int callAStaticMethod(int a, int b) {
    return staticMethod(a, b);
  }

  public String method1(String value) {
    return "Value is: " + value;
  }
  
  public String callAMethod() {
    return method1("foo");
  }
}
