package com.google.code.java2objc.examples;
public class Person {
  private final int id;
  private final String firstName;
  private final String lastName;
  private final double age;
  public Person(int id, String firstName, String lastName, double age) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }
  public int getId() {
    return id;
  }
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public double getAge() {
    return age;
  }  
  
  public boolean isSenior() {
    if (age > 62) {
      return true;
    } else {
      return false;
    }
  }
}
