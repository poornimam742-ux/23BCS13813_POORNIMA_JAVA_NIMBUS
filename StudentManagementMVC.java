import java.sql.*;
import java.util.*;

// 🟩 MODEL
class Student {
    int studentID;
    String name;
    String department;
    double marks;

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
}

// 🟨 CONTROLLER
class StudentDAO {
    private Connection con;

    public StudentDAO() throws Exception {
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String user = "root";
        String password = "your_password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
    }

    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, s.studentID);
        ps.setString(2, s.name);
        ps.setString(3, s.department);
        ps.setDouble(4, s.marks);
        ps.executeUpdate();
        System.out.println("✅ Student Added!");
    }

    public void viewStudents() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Student");
        System.out.println("ID\tName\tDepartment\tMarks");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
                               rs.getString(3) + "\t" + rs.getDouble(4));
        }
    }

    public void updateStudent(int id, double newMarks) throws SQLException {
        String sql = "UPDATE Student SET Marks=? WHERE StudentID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, newMarks);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println("✅ Student Updated!");
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("✅ Student Deleted!");
    }
}

// 🟦 VIEW (User Interface)
public class StudentManagementMVC {
    public static void main(String[] args) {
        try {
            StudentDAO dao = new StudentDAO();
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student Marks");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        System.out.print("Enter Name: ");
                        String name = sc.next();
                        System.out.print("Enter Department: ");
                        String dept = sc.next();
                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();
                        dao.addStudent(new Student(id, name, dept, marks));
                        break;

                    case 2:
                        dao.viewStudents();
                        break;

                    case 3:
                        System.out.print("Enter ID to update: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter new Marks: ");
                        double newMarks = sc.nextDouble();
                        dao.updateStudent(uid, newMarks);
                        break;

                    case 4:
                        System.out.print("Enter ID to delete: ");
                        int did = sc.nextInt();
                        dao.deleteStudent(did);
                        break;

                    case 5:
                        System.out.println("🚪 Exiting...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
