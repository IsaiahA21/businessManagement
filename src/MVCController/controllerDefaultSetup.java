package MVCController;

import colors.ConsoleColors;

import java.sql.SQLException;

public interface controllerDefaultSetup{
    /**
     * ALl the controller classes will have a userselection method
     * @param userSelection
     * @throws SQLException
     */
    public void userSelection(int userSelection) throws SQLException;  // interface method (does not have a body)
}
