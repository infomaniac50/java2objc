public class ReturnDemo {
  public int returnDemo() {
    int value = 5;
    switch (value) {
      case 0:
        return 1;
      case 1:
        return value;
      case 2:
        return value * value;
      case 3:
        return value * value * value;
      case 4:
        return value * value * value * value;
      case 5:
        return ((value * value) * value) * value;
      default:
        return -1;
    }
  }
}
