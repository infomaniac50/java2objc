
@SuppressWarnings("unused")
public class InitDemo {
  private final int a;
  private final String b;
  private final String c;
  private final double d;
  
  public InitDemo(int a, String b) {
    this(a, b, "c", 2.0);
  }

  public InitDemo(int a, String b, String c, double d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
}
