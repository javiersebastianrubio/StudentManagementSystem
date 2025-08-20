/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagement;

import java.util.*;
import java.io.*;

/**
 *
 * @author user
 */
public class StudentManager {
    
// GitHub Repo Link: https://github.com/javiersebastianrubio/StudentManagementSystem    
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        String continueInput;

        System.out.println("||| Student Data Management System |||");

        do {
            //This gets and uset a custom validation helper for student name
            String name = getValidatedName();

            //This gets and uset a custom validation helper for age
            int age = getValidatedAge();

            //This gets and uset a custom validation helper for all the grades (5)
            int[] grades = getValidatedGrades();

            //Create a new Student object and group the validated data in it
            Student newStudent = new Student(name, age, grades);
            students.add(newStudent);
            
            //checking for new entries
            System.out.print("Do you want to enter another student? (yes/no): ");
            continueInput = scanner.next().toLowerCase();
            scanner.nextLine(); // Consume the leftover newline character

        } while (continueInput.equals("yes") || continueInput.equals("y"));

        //Save all students to file using a cutom method declared below in the code (saveStudentsToFile)
        saveStudentsToFile(students, "students.txt");
        System.out.println("Student data saved to students.txt");

        //reads the file created above
        ArrayList<Student> studentsFromFile = readStudentsFromFile("students.txt");
        System.out.println("Data successfully read from students.txt");

        //Calculate highest grade across all students using findHighestGrade()
        int highestGrade = findHighestGrade(studentsFromFile);

        //Sort students by average grade (descending)
        bubbleSortByAverage(studentsFromFile);

        //this is use to generate the report
        generateReport(studentsFromFile, highestGrade, "StudentReport.txt");
        System.out.println("Report generated: StudentReport.txt");

        scanner.close();
    }

    // ------------- VALIDATION METHODS ------------- //
    
    //custom function to validate the Name... if its not all strings throws an error message
    //asks to enter the name then compares to capital and lower case letters and if match return the nam if not an error message
    private static String getValidatedName() {String name;
        while (true) {
            System.out.print("Enter student name (letters only): ");
            name = scanner.nextLine().trim();
            if (name.matches("[a-zA-Z ]+")) {
                return name;
            } else {
                System.out.println("Error: Name can only contain alphabetic characters. Please try again.");
            }
        }
    }

    //custom function to validate Age... first asks you to enter the age... then checks if its an integer...
    //and then checks if the integer is between 18 and 30... if all this is true then returns the age, if not an error message
    private static int getValidatedAge() {
        int age;
        while (true) {
            System.out.print("Enter student age (18-30): ");
            // Check if the next input is an integer
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();
                if (age >= 18 && age <= 30) {
                    scanner.nextLine(); 
                    return age;
                } else {
                    System.out.println("Error: Age must be between 18 and 30. Please try again.");
                }
            } else {
                System.out.println("Error: age has to be in writen in numbers and has to be between 18 and 30.");
                scanner.next(); // empty the invalid input from the scanner
            }
        }
    }

    //custom function to validate the grades. use a loop to itinerate through all 5 grades start at i=0 and finishing at i=4... Frist ask you to enter the grade... 
    //then the loop is generated... then checks that the grade is an integer between 0 and 100... if is not then the user get an error message if all conditions are met
    //then return that grade... does this for each grade from the first one until the fifth one
    private static int[] getValidatedGrades() {
        int[] grades = new int[5];
        System.out.println("Enter 5 grades (each between 0 and 100):");

        for (int i = 0; i < 5; i++) {
            while (true) {
                System.out.print("Grade " + (i + 1) + ": ");
                if (scanner.hasNextInt()) {
                    int grade = scanner.nextInt();
                    if (grade >= 0 && grade <= 100) {
                        grades[i] = grade;
                        break; // Exit the while loop for this grade
                    } else {
                        System.out.println("Error: Grade must be between 0 and 100. Please try again.");
                    }
                } else {
                    System.out.println("Error: Please enter a valid integer for the grade.");
                    scanner.next(); // Clear the invalid input
                }
            }
        }
        scanner.nextLine();
        return grades;
    }

    //----Custom functions to write, read and transform data from the file----//
    
    //Custom function to write/create a file... setting 2 variables arratlist(student) and string(filename) 
    //using a try-catch function to caught any error during the generation of the file... if something fails then error message is sent
    //using a PrintWriter and FileWriter I create the file and add a for loop itinerating through each student created in Student class with toFileString()
    private static void saveStudentsToFile(ArrayList<Student> students, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.println(student.toFileString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving to the file: " + e.getMessage());
        }
    }

    //Custom function to read a file... setting 1 variable string(filename) 
    //using a try-catch function to caught any error while the file is bein read... if something fails then error message is sent
    private static ArrayList<Student> readStudentsFromFile(String filename) {
        ArrayList<Student> studentList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");//breaks the strings into an array of strings separating by the comma adn storing in "data"

                //calling each element in the new created "data" array... name is in position [0] and so on
                //also converts strings into the correct format... eg.: age from string to integer
                //finally a for loop is put in place to itinerate through all the grades
                
                String name = data[0].trim();
                int age = Integer.parseInt(data[1].trim());
                int[] grades = new int[5];
                for (int i = 0; i < 5; i++) {
                    grades[i] = Integer.parseInt(data[i + 2].trim());
                }
                //Create a Student object and add it to the list
                studentList.add(new Student(name, age, grades));
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file '" + filename + "' was not found.");
        } catch (Exception e) {
            System.out.println("An error happened while reading the file: " + e.getMessage());
        }
        return studentList;
    }

    // --- Calculations --- //
    //custom function to calculate higher grade 1 array list (student) as variable
    //create an empty integer object with value 0
    //then I added a for loop so the first grade get compare with the object highest
    //if its bigger then populate highest object with grade value... the same will happen on the next grade...
    //this way the highest grade will populate highest and no matter what comes after wont get override
    private static int findHighestGrade(ArrayList<Student> students) {
        int highest = 0;
        for (Student student : students) {
            for (int grade : student.getGrades()) {
                if (grade > highest) {
                    highest = grade;
                }
            }
        }
        return highest;
    }

    //custom function to sort the grades desc
    //storing the lenght of students in n object... creating a for loop itinerating from position 0 until the last one (n)
    //creating an inner loop where for each i then j (starting at 0) has to be less than n - j - 1
    //
    private static void bubbleSortByAverage(ArrayList<Student> students) {
        //this is a bubble sort function order by average grade... descending order
        int n = students.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getAverageGrade() < students.get(j + 1).getAverageGrade()) {//compares j with j+1
                    //Swap students at positions j and j+1... bascially if average is bigger swap it to a lower position
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
    }

    // --- generate the report function with the average oredr desc --- //
    private static void generateReport(ArrayList<Student> students, int highestGrade, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("========== STUDENT REPORT ==========");
            writer.println("Sorted by Average Grade (Descending Order)");
            writer.println("------------------------------------");

            for (Student student : students) {
                writer.println(student.toString());
            }

            writer.println("------------------------------------");
            writer.println("Highest Grade in the Dataset: " + highestGrade);
            writer.println("========== END OF REPORT ==========");

        } catch (IOException e) {
            System.out.println("An happened while the report was being generated: " + e.getMessage());
        }
    }    
}
