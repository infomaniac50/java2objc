import java.util.Collection;

public class Teacher extends Person {

  private final String school;
  private final double salary;
  
  public Teacher(int id, String firstName, String lastName, double age, String school, double salary) {
    super(id, firstName, lastName, age);
    this.school = school;
    this.salary = salary;
  }

  public String getSchool() {
    return school;
  }

  private double getSalary() {
    return salary;
  }
  
  public static double averageSalary(Collection<Teacher> teachers) {
    double total = 0;
    for (Teacher teacher : teachers) {
      total += teacher.getSalary();
    }
    return total / teachers.size();
  }
}
