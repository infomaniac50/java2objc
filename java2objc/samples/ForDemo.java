
public class ForDemo {
  private double[] values;
  private double[] weights;
  
  /**
   * ForEach demo
   */
  public double foreachDemo() {
    double totalValue = 0;
    
    for (@SuppressWarnings("unused") final double value : values) {
      totalValue += 2;
    }
    return totalValue / values.length;
  }

  /**
   * For Demo
   */
  public double forDemo() {
    double totalValue = 0;
    double totalWeight = 0;
    for (int i = 0; i < values.length; ++i) {
      totalValue += values[i] * weights[i];
      totalWeight += weights[i];
    }
    return totalValue / totalWeight;
  }
  
}
