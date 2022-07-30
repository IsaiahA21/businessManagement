package mainCollection;
import colors.ConsoleColors;

import java.sql.*;
import java.util.Scanner;
public class DBConnectionManager extends ConsoleColors {
    private static String url = "jdbc:mysql://localhost:3306/business";
    private static Connection con = null;

    public static String[] loginDetail() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter database username(ie. root):");
        String username = scan.nextLine();

        System.out.print("Enter database password:");
        String password = scan.nextLine();

        return (new String[]{username,password});
    }


    public static Connection getConnection() {
        String[] log = loginDetail();

        try {
            con = DriverManager.getConnection(url, log[0], log[1]);
            System.out.println(ANSI_GREEN + "Successfully created database connection." + ANSI_RESET);

        } catch (SQLException ex) {
            // log an exception. fro example:
            System.out.println(ANSI_PURPLE + "Failed to create the database connection." + ANSI_RESET);
            ex.printStackTrace();
            System.exit(0);
        }

        return con;
    }
    public static void closeConnection(){
        if (con != null) try { con.close(); } catch (SQLException ignore) {}
        System.out.println(ANSI_GREEN  + "\n The connection to the database was successfully closed" + ANSI_RESET);
    }
}
