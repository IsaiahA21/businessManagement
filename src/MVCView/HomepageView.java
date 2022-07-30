// This is the frontend of the company Management application
// This file will take the user input/request and query the mysql database

package MVCView;
import colors.ConsoleColors;
import java.util.Scanner;


public class HomepageView extends ConsoleColors {

  // default constructor
  public HomepageView() {}



  /**
    displays the user option
  */
  public void beginning() {
    System.out.println("\nPlease select the action you wish to peform:");
    System.out.println("0. Exit            (type 0)");
    System.out.println("1. All Employees   (type 1)");
    System.out.println("2. All Department  (type 2)");
    System.out.println("3. All Projects    (type 3)");
    System.out.println("4. All Managers    (type 4)");
    System.out.println("5. All supervisors (type 5)");
  }

  /**
   * Gets the userinput
   * Contains a bit of logic for determning if the user need to try again
   * No error checking for if the user input is not an int
   * @return The user input
   */
  public int OpenningMessage()
  {
    while (true)
    {
      beginning();
      Scanner inputObj = new Scanner(System.in);
      int selection = inputObj.nextInt();
      System.out.println("You entered: " + selection);

      if (selection > 5 || selection < 0 ){
        System.out.println("You entered an invalid input");
        System.out.println("Please try again: ");
      }
      else {
        return  selection;
      }
    }//end of while loop
  }


}
