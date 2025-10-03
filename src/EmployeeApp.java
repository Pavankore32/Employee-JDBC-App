import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {
    // DB URL updated to handle Public Key Retrieval and SSL
    static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static final String USER = "root";
    static final String PASS = "root123"; // <-- your MySQL password

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Scanner sc = new Scanner(System.in)) {

                System.out.println("Connected to DB!");

                while (true) {
                    System.out.println("\n1.Add 2.View 3.Update 4.Delete 5.Exit");
                    System.out.print("Enter choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine(); // consume newline

                    switch (choice) {
                        case 1 -> addEmployee(conn, sc);
                        case 2 -> viewEmployees(conn);
                        case 3 -> updateEmployee(conn, sc);
                        case 4 -> deleteEmployee(conn, sc);
                        case 5 -> {
                            System.out.println("Exiting...");
                            return;
                        }
                        default -> System.out.println("Invalid choice");
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Add the connector JAR to the classpath.");
            e.printStackTrace();
        }
    }

    static void addEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Name: "); 
        String name = sc.nextLine();
        System.out.print("Position: "); 
        String pos = sc.nextLine();
        System.out.print("Salary: "); 
        double sal = sc.nextDouble();
        sc.nextLine();

        String sql = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, pos);
            ps.setDouble(3, sal);
            int rows = ps.executeUpdate();
            System.out.println(rows + " employee added.");
        }
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employee";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("ID | Name | Position | Salary | Created At");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f | %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getTimestamp("created_at"));
            }
        }
    }

    static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to update: "); 
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("New Name: "); 
        String name = sc.nextLine();
        System.out.print("New Position: "); 
        String pos = sc.nextLine();
        System.out.print("New Salary: "); 
        double sal = sc.nextDouble();
        sc.nextLine();

        String sql = "UPDATE employee SET name=?, position=?, salary=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, pos);
            ps.setDouble(3, sal);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            System.out.println(rows + " employee updated.");
        }
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to delete: "); 
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM employee WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows + " employee deleted.");
        }
    }
}
