package MVCController;
import colors.ConsoleColors;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import MVCModel.*;
import  MVCView.*;

public class employeeController extends ConsoleColors implements  controllerDefaultSetup{
    private employeeView empView;
    Connection EMPDBconnection;
    employeeModel empModel;

    /**
     *  constructor will assign the instance to the local variables
     * @param dbconnect db connection
     * @throws SQLException
     */
    public employeeController(Connection dbconnect) throws SQLException {
        this.EMPDBconnection = dbconnect;
        this.empView = new employeeView();
        this.empModel = new employeeModel(EMPDBconnection);

        //calls model to get all employees from db
        empView.display(getAllEmployees());

        // then call employeeView to display all employees
        int userInput = empView.displayOptions();

        // returns to the startController class
        if (userInput == 0) {
            System.out.print("Back to main...\n");
            return;
        }

        //else process to the userSelection method
        userSelection(userInput);

    }

    /**
     * Contains the switch statement
     * based on the user selection it will call the appropriate employee model method
     * And will then call to emp view class to display the result of the user action
     * @param userSelection the user input. Has to be a number, no checks for non-numbers
     * @throws SQLException
     */
    @Override
    public void userSelection(int userSelection) throws SQLException {
        String confirmationMesssge;
        String[] name;

        switch (userSelection) {
            case 1:
                // input was "add a user"
                System.out.println("Please complete the new user form");

                // call the employee view to display form to add a user
                employeeModel newEmployeeform = empView.addForm();

                // pass the form to the employee model
                confirmationMesssge = empModel.add(newEmployeeform);

                // display confirmation message
                empView.display(confirmationMesssge);
                break;

            case 2:
                // input was "delete a user by fname lname"

                // call the deleteForm method to display form for deleting a user
                name = empView.deleteForm();

                // pass the name to be deleted to the employee model
                confirmationMesssge = empModel.delete(name);

                // display confirmation message
                empView.display(confirmationMesssge);
                break;

            case 3:
                // input was "view specific user based fname lname"

                // call the employee view to display form for the user to be selected
                name = empView.getEmployeeForm();

                // pass the name to be selected to the employee model
                List<employeeModel> ret = empModel.retrieve(name);

                // the return was null, then based of input, no user could be found
                if (ret == null){
                    empView.display(ANSI_RED +"The user " + name[0] + " " + name[1] + " does not exist in the database"+ ANSI_RESET);
                }
                else{
                    // else we display the person the db found
                    empView.display(ret);
                }
                break;

            case 4:
                // input was "clear employee db"

                //call model function clear data
                String retString = empModel.clearData();

                empView.display(retString);
        }
    }

    /**
     * Calls the employeeModel object method retriveAll
     * @return a list of employeeModel
     */
    public List<employeeModel> getAllEmployees() {
        List<employeeModel> tempList = empModel.retrieveAll();
        assert tempList != null;


        return tempList;
    }


}
