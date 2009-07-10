
public class SwitchDemo {
  int newValue;

  public void switchWithInt(int value) {
    switch (value) {
      case 0:
        newValue = 0;
        break;
      case 1:
        newValue = value;
        break;
      case 2:
        newValue = value * value;
      default:
        newValue = -1;
        break;
    }
  }
}
