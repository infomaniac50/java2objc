
public class SynchronizedDemo {

  public void synchronizedDemo() {
    Integer value = 10;
    synchronized(value) {
      value = 15;
    }
  }
}
