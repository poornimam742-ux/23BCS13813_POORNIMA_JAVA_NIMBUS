import java.io.*;
import java.util.Scanner;

class Employee implements Serializable {
    private static final long serialVersionUID = 2L;
    int empId;
    String empName;
    String role;
    double pay;

    public Employee(int empId, String empName, String role, double pay) {
        this.empId = empId;
        this.empName = empName;
        this.role = role;
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "EmpID: " + empId + ", Name: " + empName + ", Role: " + role + ", Salary: " + pay;
    }
}

public class EmployeeDirectory {
    static final String FILE_PATH = "employee_data.ser";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Records");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    addEmployee(input);
                    break;
                case 2:
                    readEmployees();
                    break;
                case 0:
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (option != 0);

        input.close();
    }

    private static void addEmployee(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Employee Name: ");
        String nm = sc.nextLine();
        System.out.print("Enter Role: ");
        String rl = sc.nextLine();
        System.out.print("Enter Salary: ");
        double sal = sc.nextDouble();

        Employee emp = new Employee(id, nm, rl, sal);

        try (ObjectOutputStream oos = new AppendableObjectStream(new FileOutputStream(FILE_PATH, true))) {
            oos.writeObject(emp);
            System.out.println("✔ Employee added.");
        } catch (IOException e) {
            System.out.println("Error writing data.");
        }
    }

    private static void readEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            System.out.println("\n--- Employee Records ---");
            while (true) {
                Employee e = (Employee) ois.readObject();
                System.out.println(e);
            }
        } catch (EOFException e) {
            // reached end of file
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No records available.");
        }
    }
}

class AppendableObjectStream extends ObjectOutputStream {
    public AppendableObjectStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        File f = new File(EmployeeDirectory.FILE_PATH);
        if (f.length() == 0) {
            super.writeStreamHeader();
        } else {
            reset();
        }
    }
}
