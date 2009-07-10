/**
 * Demo of assorted language constructs
 * 
 * @author Inderjeet Singh
 */
public class AssortedDemo {

  public int labeledStatementDemo() {
    int value = 4;

    outer:
    for (int i = 0; i < value; ++i) {
      for (int j = 0; j < value; ++j) {
        if (i*j == 12) {
          break outer;
        } else {
          continue;
        }
      }
    }
    return value;
  }
}
