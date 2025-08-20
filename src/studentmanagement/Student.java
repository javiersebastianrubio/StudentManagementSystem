/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

/**
 *
 * @author user
 */
public class Student {
    private String name;
    private int age;
    private int[] grades;
    private double averageGrade;

    // Constructor
    public Student(String name, int age, int[] grades) {
        this.name = name;
        this.age = age;
        this.grades = grades;
        // Calculate average grade as soon as the grades are enter
        this.calculateAverage();
    }

    // This is the Method use to calculate the average grade
    private void calculateAverage() {
        int sum = 0;
        for (int grade : this.grades) {
            sum += grade;
        }
        this.averageGrade = (double) sum / this.grades.length;
    }

    //All getters are declare below
    public String getName() {return name;}
    public int getAge() {return age;}
    public int[] getGrades() {return grades;}
    public double getAverageGrade() {return averageGrade;}

    //This format all the variables into a line (string) so it can be added into students.txt
    public String toFileString() {
        return name + "," + age + "," + grades[0] + "," + grades[1] + "," + grades[2] + "," + grades[3] + "," + grades[4];}

    // This format is used for the report in StudentReport.txt
    public String toString() {
        return String.format("Name: %s, Age: %d, Grades: %d, %d, %d, %d, %d, Average: %.2f",
                name, age, grades[0], grades[1], grades[2], grades[3], grades[4], averageGrade);
    }    
}
