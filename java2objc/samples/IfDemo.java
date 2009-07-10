public class IfDemo {

  public int ifWithBlock() {
    int value = 3;
    if (value > 3) { 
      return 2;
    }
    if (value > 2) { 
      return 2;
    } else if (value > 1) {
      return 1;
    } else { 
      return 0;
    }
  }
  
  public int ifWithoutBlock() {
    int value = 3;
    if (value > 3) 
      return 2;
    if (value > 2) 
      return 2;
    else if (value > 1)
      return 1;
    else 
      return 0;    
  }
  
  public int ifWithMixedBlocks() {
    int value = 3;
    if (value > 2) 
      return 2;
    else if (value > 1) {
      return 1;
    } else 
      return 0;        
  }
}
