package MVCController;

import MVCModel.employeeModel;
import  MVCView.*;
import colors.ConsoleColors;
import java.sql.*;

public class startController extends ConsoleColors implements controllerDefaultSetup {
    HomepageView homeView;
    Connection DBconnection;

    /**
     * constructor will assign the instance to the local variables
     * @param homeView an instance of the HomepageView class
     * @param con an connection to the database
     */
    public startController(HomepageView homeView, Connection con){
        this.homeView = homeView;
        this.DBconnection = con;
    }


    /**
     * Contains the switch statement
     * based on the user selection it will call the appropriate controller class
     * @param userSelection the input the user enters. Has to be a number, no checks for non-numbers
     * @throws SQLException
     */
    @Override
    public void userSelection(int userSelection) throws SQLException {
        switch (userSelection) {
            case 1:// input was "All employees"

                System.out.println("Displaying all employees...");

                // call the employee controller
                employeeController eControl = new employeeController(/*empModel,*/DBconnection);
                break;

            default:
                System.out.println("Option not available");
        }
    }

    /**
     * calls the method OpeningMessage in the  view class Homepageview  to display the basic page
     * Will then pass the user input to the selection method
     * @throws SQLException
     */
    public void callHomePage() throws SQLException {
        while (true) {
            int input = homeView.OpenningMessage();
            if (input == 0) {
                System.out.print(ANSI_GREEN + "Good bye" +ANSI_RESET);
                return;
            }
            userSelection(input);
        }
    }

}
