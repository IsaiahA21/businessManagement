// This is the frontend of the company Management application
// This file will take the user input/request and query the mysql database

package MVCView;
import MVCModel.employeeModel;
import colors.ConsoleColors;
import java.util.*;


public class employeeView extends ConsoleColors {

  // default constructor
  public employeeView() {}

  /**
   * Will display a string
   * @param message string to be displayed
   */
  public  void display(String message){
    System.out.println(message);
  }

  /**
   * Will display a employeeModel(s)
   * @param allEmployees objects of employeeModel to be displayed
   */
  public void display(List<employeeModel> allEmployees) {
    System.out.format("+---------------+---------------+------------+------------+------------------------------+--------------+------------+----+%n");
    System.out.format("| fname         | lname         | bdate      | SIN        | Address                      | Salary       | Mgr_SIN    | DNO|%n");
    System.out.format("+---------------+---------------+------------+------------+------------------------------+--------------+------------+----+%n");

    List<String> ret = new ArrayList<>();
    String b = " |";
    allEmployees.forEach((row) -> ret.add(String.format("%-15s %-15s %-12s %-12s %-30s %-14s %-12s %-3s %1s", "|" +row.getFname() , "|" + row.getLname(), "|" +row.getBdate(), "|" + row.getSin(), "|" + row.getAddress(), "|" + row.getSalary(), "|" + row.getMgr_sin(), "|" + row.getDept_no(), b )));
    ret.forEach(System.out::println);

    System.out.format("+---------------+---------------+------------+------------+------------------------------+--------------+------------+----+%n");

  }

  /**
   * Display the options available for the employee
   */
  public static void menu() {
    System.out.println("Please select the action you wish to perform:");
    System.out.println("""
            0. Back to main menu       (type 0)
            1. Add an employee         (type 1)
            2. Delete an employee      (type 2)
            3. View specific employee  (type 3)
            4. Remove all employees    (type 4)""");
  }

  /**
   * Gets the userinput
   * Contains a bit of logic for determning if the user need to try again
   * No error checking for if the user input is not an int
   * @return The user input
   */
  public  int displayOptions()
  {
    while (true)
    {
      menu();
      Scanner inputObj = new Scanner(System.in);
      int selection = inputObj.nextInt();
      System.out.println("You entered: " + selection);

      if (selection > 4 || selection < 0  ){
        System.out.println("You entered an invalid input");
        System.out.println("Please try again: ");
      }
      else {
        return  selection;
      }
    }//end of while loop
  }

  /**
   * Gets the user to fillout the employee they want to add to the db
   * @return an employeeModel object
   */
  public employeeModel addForm() {
    Scanner scan = new Scanner(System.in);
    String firstname;
    do {
      System.out.print("\nEnter the individual first name:");
      firstname = scan.nextLine();
    } while (firstname.length() < 1);

    String lastname;
    do {
      System.out.print("\nEnter " + firstname + " last name:");
      lastname = scan.nextLine();
    } while (lastname.length() < 1);

    DateValidator validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");
    String yr;

    do{
      System.out.print("\nEnter " + firstname + " year of birth(yyyy-MM-dd):");
      yr = scan.nextLine();
    }while(!validator.isValid(yr)); // while false


    String Stringusersin;
    do {
      System.out.print("\nEnter " + firstname + " SIN:");
      Stringusersin = scan.nextLine();
    }while (!checkSinLength(Stringusersin));

    int usersin = Integer.parseInt(Stringusersin);

    String useradddres;
      System.out.print("\nEnter " + firstname + " address(can be left blank):");
       useradddres = scan.nextLine();


    int usersalary;
    do {
      System.out.print("\nEnter " + firstname + " salary(can't be 0):");
       usersalary = scan.nextInt();
    }while(usersalary < 1);

    String stringMgrSin;
    scan.nextLine(); // "used to consume a \n so that Scanner waits for input "
    do
    {
      System.out.print("\nEnter " + firstname + " manager sin(can be left blank):");
      stringMgrSin = scan.nextLine();
      if(stringMgrSin.length() < 1){
        break;
      }
    }while (!checkSinLength(stringMgrSin) || checkSins(stringMgrSin, Stringusersin));// while the length is not 9 and the user and mgr sin is the same
    int usermgrsin =0;
    if (stringMgrSin.length() > 1){ usermgrsin = Integer. parseInt(stringMgrSin);}

    int userdno;
    do {
      System.out.print("\nEnter " + firstname + " department number(can't be 0):");
       userdno = scan.nextInt();
    }while(userdno < 1);

    // return input to the controller
    return  new employeeModel(firstname,lastname,yr,usersin,useradddres,usersalary,usermgrsin,userdno);
  }

  /**
   * checkSins checks if the sin and mgr sin are the same
   * If they are, we prompt the user to enter a new mgr sin.
   * A person can't be their own manager
   * @param stringMgrSin manager sin
   * @param stringUserSin individual sin
   * @return true if the individual sin equals the manager sin
   *         false if they are different
   */
  private boolean checkSins(String stringMgrSin, String stringUserSin) {
    if (stringMgrSin.equals(stringUserSin)){
      System.out.println(ANSI_RED + "Manager sin can not be the same as the individual sin"+ ANSI_RESET);
      return true;
    }
    return false;
  }

  /**
   * checkSinLength checks sin length
   * @param stringMgrSin sin to be checked
   * @return true if the individual sin length is 9-digit
   *         false if it's not
   */
  boolean checkSinLength(String stringMgrSin){

    if (stringMgrSin.length() != 9){
      System.out.println(ANSI_RED + "Invalid sin length!! sin  should have 9-digits "+ ANSI_RESET);
      return  false;
    }
    return true;
  }

  /***
   * individual to be deleted from db
   * @return the name of the individual to be deleted
   */
  public String[] deleteForm() {
    String[] fullname = new String[2];

    System.out.println("\nPlease provide the name of the individual you like to remove: ");
    Scanner scan = new Scanner(System.in);
    String firstname;
    do {
      System.out.print("\nEnter the individual first name:");
      firstname = scan.nextLine();
    } while (firstname.length() < 1);

    String lastname;
    do {
      System.out.print("\nEnter " + firstname + " last name:");
      lastname = scan.nextLine();
    } while (lastname.length() < 1);

    fullname[0] = firstname;
    fullname[1] = lastname;

    return fullname;
  }

  /**
   * individual to be retrieved from db
   * @return the name of the individual to be deleted
   */
  public String[] getEmployeeForm() {
    String[] fullname = new String[2];

    System.out.println("\nPlease provide the name of the individual you like to retrieve: ");
    Scanner scan = new Scanner(System.in);
    String firstname;
    do {
      System.out.print("\nEnter the individual first name:");
      firstname = scan.nextLine();
    } while (firstname.length() < 1);

    String lastname;
    do {
      System.out.print("\nEnter " + firstname + " last name:");
      lastname = scan.nextLine();
    } while (lastname.length() < 1);

    fullname[0] = firstname;
    fullname[1] = lastname;

    return fullname;

  }
}
