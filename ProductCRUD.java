import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String user = "root";
        String password = "your_password";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            con.setAutoCommit(false); // Transaction start

            while (true) {
                System.out.println("\n--- Product Table Menu ---");
                System.out.println("1. Insert Product");
                System.out.println("2. Display All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: // Insert
                        System.out.print("Enter ProductID: ");
                        int id = sc.nextInt();
                        System.out.print("Enter ProductName: ");
                        String name = sc.next();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        String insertSQL = "INSERT INTO Product VALUES (?, ?, ?, ?)";
                        PreparedStatement ps1 = con.prepareStatement(insertSQL);
                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setDouble(3, price);
                        ps1.setInt(4, qty);
                        ps1.executeUpdate();
                        con.commit();
                        System.out.println("✅ Product Inserted Successfully!");
                        break;

                    case 2: // Read
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
                        System.out.println("ProductID\tName\tPrice\tQuantity");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" +
                                               rs.getDouble(3) + "\t" + rs.getInt(4));
                        }
                        break;

                    case 3: // Update
                        System.out.print("Enter ProductID to update: ");
                        int uid = sc.nextInt();
                        System.out.print("Enter new Price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("Enter new Quantity: ");
                        int newQty = sc.nextInt();

                        String updateSQL = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
                        PreparedStatement ps2 = con.prepareStatement(updateSQL);
                        ps2.setDouble(1, newPrice);
                        ps2.setInt(2, newQty);
                        ps2.setInt(3, uid);
                        ps2.executeUpdate();
                        con.commit();
                        System.out.println("✅ Product Updated Successfully!");
                        break;

                    case 4: // Delete
                        System.out.print("Enter ProductID to delete: ");
                        int did = sc.nextInt();
                        String deleteSQL = "DELETE FROM Product WHERE ProductID=?";
                        PreparedStatement ps3 = con.prepareStatement(deleteSQL);
                        ps3.setInt(1, did);
                        ps3.executeUpdate();
                        con.commit();
                        System.out.println("✅ Product Deleted Successfully!");
                        break;

                    case 5:
                        con.close();
                        System.out.println("🚪 Exiting...");
                        return;

                    default:
                        System.out.println("❌ Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
