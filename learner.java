import java.io.*;
import java.util.Scanner;

// StudentInfo class implementing Serializable
class StudentInfo implements Serializable {
    private static final long serialVersionUID = 100L;
    int id;
    String name;
    double marks;

    public StudentInfo(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "[ID: " + id + ", Name: " + name + ", Marks: " + marks + "]";
    }
}

public class StudentDataApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input from user
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student marks: ");
        double marks = scanner.nextDouble();

        StudentInfo student = new StudentInfo(id, name, marks);

        // Serialize
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.ser"))) {
            out.writeObject(student);
            System.out.println("\n✔ Student data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving data.");
            e.printStackTrace();
        }

        // Deserialize
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.ser"))) {
            StudentInfo s = (StudentInfo) in.readObject();
            System.out.println("✔ Retrieved Student Record: " + s);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while reading data.");
            e.printStackTrace();
        }

        scanner.close();
    }
}
