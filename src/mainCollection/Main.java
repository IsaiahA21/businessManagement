package mainCollection;
import MVCController.*;
import MVCView.*;
import MVCModel.*;
import colors.ConsoleColors;

import java.sql.*;

/**
 * @author isaiah
 * */
public class Main extends ConsoleColors {
    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to Ione Company Management application");
        Connection connect = DBConnectionManager.getConnection();
        HomepageView homeView = new HomepageView();

        // create an instance of controller
        startController controller = new startController(homeView, connect);

        controller.callHomePage();// call to display the main menu
        DBConnectionManager.closeConnection();


    }
}