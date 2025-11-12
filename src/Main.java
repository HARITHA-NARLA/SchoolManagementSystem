import java.io.*;
import java.util.*;

// ---------- Base class ----------
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

// ---------- Derived classes ----------
class Student extends Person {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String grade;

    public Student(String name, int age, String studentId, String grade) {
        super(name, age);
        this.studentId = studentId;
        this.grade = grade;
    }

     @Override
    public String toString() {
        return "[Student] " + super.toString() + ", ID: " + studentId + ", Grade: " + grade;
    }
}

class Teacher extends Person {
    private static final long serialVersionUID = 1L;
    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "[Teacher] " + super.toString() + ", Subject: " + subject;
    }
}

// ---------- Main system class ----------
public class Main {
    private static final String FILE_NAME = "schoolData.ser";
    private static List<Person> people = new ArrayList<>();

    // Save all data to file
    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(people);
            System.out.println("Data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from file (if exists)
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            people = (List<Person>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // Display all people
    private static void viewAll() {
        if (people.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\n--- All Records ---");
        for (Person p : people) System.out.println(p);
    }

    // Add student
    private static void addStudent(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter student ID: ");
        String id = sc.next();
        System.out.print("Enter grade: ");
        String grade = sc.next();
        people.add(new Student(name, age, id, grade));
        System.out.println("Student added successfully!");
    }

    // Add teacher
    private static void addTeacher(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter subject: ");
        String subject = sc.next();
        people.add(new Teacher(name, age, subject));
        System.out.println("Teacher added successfully!");
    }

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== School Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add Teacher");
            System.out.println("3. View All Records");
            System.out.println("4. Save & Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> addTeacher(sc);
                case 3 -> viewAll();
                case 4 -> { saveData(); System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
