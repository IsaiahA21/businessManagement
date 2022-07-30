
package MVCModel;
import colors.ConsoleColors;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class employeeModel extends ConsoleColors {
    private Connection dbconnect;
    private String Fname;
    private String Lname;
    private int sin;
    private String Bdate;
    private String Address;
    private double Salary;
    private int Mgr_sin;
    private int Dept_no;


    //  constructor
    public  employeeModel(Connection dbconnect){
        this.dbconnect = dbconnect;
    }

    /**
     * This constructor when we need to create an object of employee model i.e, after a call to the database
     *
     * @param fname corresponds to the fname in the db employee table
     * @param lname corresponds to the lname in the db employee table
     * @param bdate corresponds to the bdate in the db employee table
     * @param sin corresponds to the sin in the db employee table
     * @param addr corresponds to the addr in the db employee table
     * @param salary corresponds to the salary in the db employee table
     * @param mgr_sin corresponds to the mgr_sin in the db employee table
     * @param dno corresponds to the dno in the db employee table
     */
    public employeeModel( String fname, String lname, String bdate,int sin, String addr, double salary, int mgr_sin, int dno) {
        this.setFname(fname);
        this.setLname(lname);
        this.setSin(sin);
        this.setBdate(bdate);
        this.setAddress(addr);
        this.setSalary(salary);
        this.setMgr_sin(mgr_sin);
        this.setDept_no(dno);
    }


    // getter and setter methods
    public String getFname() {return Fname;}
    public void setFname( String fname) {this.Fname = fname;}

    public String getLname() {return Lname;}
    public void setLname( String lname) {this.Lname = lname;}

    public int getSin() {return sin;}
    public void setSin( int sin) {this.sin = sin;}

    public String getBdate() {return Bdate;}
    public void setBdate( String Bdate) {this.Bdate = Bdate;}

    public String getAddress() {return Address;}
    private void setAddress(String addr) {
        this.Address = addr;
    }

    public double getSalary() {
        return Salary;
    }
    public void setSalary(double salary) {
        Salary = salary;
    }

    public int getMgr_sin() {
        return Mgr_sin;
    }

    public void setMgr_sin(int mgr_sin) {
        Mgr_sin = mgr_sin;
    }

    public int getDept_no() {
        return Dept_no;
    }

    public void setDept_no(int dept_no) {
        Dept_no = dept_no;
    }

    // CRUD data management methods

    /**
     * add the employeeModel to the database
     * If sin doesn't currently exist in the db
     * If the mgr_sin, if provided, matches one the db already
     * @param form an employeeModel object
     * @return
     * @throws SQLException
     */
    public String add(employeeModel form) throws SQLException  // Employee.add for creating a new Employee instance.
     {
         // first check if the SIN already exist in db
         Statement stmt = dbconnect.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM  employee  WHERE  SIN = " + form.getSin());

         // if true, return to view the  user with that sin
         if (rs.next()) {
             String Fullname =rs.getString("fname") + " " + rs.getString("lname");
             closeQuery(stmt,rs);
             return ANSI_RED + "[duplicate SIN] There is already a user with the sin " + form.getSin() + ".\n" + "The user is " +Fullname + ANSI_RESET;
         }

         if (form.getMgr_sin() != 0) {// if the mgr_sin is not null, then check
             // next check if there is a user with the mgr_sin
             rs = stmt.executeQuery("SELECT * FROM  employee  WHERE  SIN = " + form.getMgr_sin());

             // if false, return to view that a person with the mgr_sin doesn't exist
             if (!rs.next()) {
                 closeQuery(stmt, rs);
                 return ANSI_RED + "[No matching SIN found] There is no current user which sin matches the manager sin you entered " + form.getMgr_sin() + ".\n" + ANSI_RESET;
             }
         }
         // get the number of rows from the result set

         //SQL INSERT statement, using the Java PreparedStatement
         String insert = " insert into employee (fname, lname, bdate, SIN, Address, Salary, Mgr_sin, D_no)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";

         //Set the fields on our Java PreparedStatement object.
         PreparedStatement preparedStmt = dbconnect.prepareStatement(insert);
         preparedStmt.setString (1, form.getFname());
         preparedStmt.setString (2, form.getLname());
         preparedStmt.setString   (3, form.getBdate());
         preparedStmt.setInt(4, form.getSin());
         preparedStmt.setString    (5, form.getAddress());
         preparedStmt.setDouble    (6, form.getSalary());
         preparedStmt.setInt    (7, form.getMgr_sin());
         preparedStmt.setInt    (8, form.getDept_no());
         preparedStmt.execute();

         closeQuery(stmt,rs,preparedStmt);
         return  ANSI_BLUE + "The user " + form.getFname() + " " + form.getLname() + " has been added to the database"+ ANSI_RESET;
     }

    /**
     * EmployeeModel.retrieveAll for retrieving all Employee instances from the persistent data store.
     * @return a list of employee model
     */
    public  List<employeeModel> retrieveAll() {
        Statement stmt = null;
        ResultSet rs = null;
        List<employeeModel> employeeResult = new ArrayList<>();
        try{
            stmt = dbconnect.createStatement();
            rs = stmt.executeQuery("Select * from employee");
            while (rs.next()) {
                String fn = rs.getString("fname");
                String ln = rs.getString("lname");
                String bd = rs.getString("bdate");
                int sin = rs.getInt("SIN");
                String add = rs.getString("Address");
                double sal = rs.getDouble("Salary");
                int mgrSin =rs.getInt("Mgr_sin");
                int dept =rs.getInt("D_no");

                employeeResult.add(new employeeModel(fn,ln,bd,sin,add,sal,mgrSin,dept));
            }
            closeQuery(stmt,rs);
            return employeeResult;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
        }
        return null;
    }

    /**
     * returns a confirmation the user has been either successful deleted or the user couldn't be found in database
     *  Employee.delete for deleting an Employee instance.
     * @param name employee to be deleted
     * @return message confirming whether or not the employee was deleted
     * @throws SQLException
     */
    public String delete(String[] name) throws SQLException {

        //Set the fields on our Java PreparedStatement object.
        PreparedStatement preparedStmt = dbconnect.prepareStatement("Delete FROM `employee` Where fname =  ? AND lname = ?;");
        preparedStmt.setString(1,name[0]);
        preparedStmt.setString(2,name[1]);

        int deleted = preparedStmt.executeUpdate(); //An int that indicates the number of rows affected, or 0 if using a DDL statement.
        preparedStmt.close();

        String ret;
        if (deleted > 0) {
            ret = ANSI_BLUE + name[0] + " " + name[1] + " has been successfully removed \n" + ANSI_RESET;
        }
        else {
            ret = ANSI_RED + name[0] + " " + name[1] + " couldn't be found in the db.\nHence removal was unsuccessful \n" + ANSI_RESET;
        }

        return ret;
    }

   /*
    public static void update(...) {...} // Employee.update for updating an existing Employee instance.
    public static void clearData(...) {...} //Employee.clearData for clearing the employee database table.
    */

    /**
     *  clearData wipes clean the employee table
     * @return a string confirm that the db has been cleaned out
     * @throws SQLException
     */
   public String  clearData() throws SQLException
   {
       PreparedStatement preparedStmt = dbconnect.prepareStatement("DELETE FROM employee;");
       preparedStmt.executeUpdate();
       preparedStmt.close();
       return ANSI_BLUE + " Employee storage has been wiped clean" + ANSI_RESET;

   }

    /**
     * Employee.retrieve for retrieving a specific Employee instance
     * @param name tuple to be retrieved
     * @return list of employeeModel
     * @throws SQLException
     */
    public  List<employeeModel> retrieve(String[] name) throws SQLException
   {
       Statement stmt = dbconnect.createStatement();
       List<employeeModel> employeeResult = new ArrayList<>();

       ResultSet rs = stmt.executeQuery("Select * from employee WHERE fname = '" + name[0] + "' AND  lname = '" + name[1] + "';");

       if (rs.next()) {
           String fn = rs.getString("fname");
           String ln = rs.getString("lname");
           String bd = rs.getString("bdate");
           int sin = rs.getInt("SIN");
           String add = rs.getString("Address");
           double sal = rs.getDouble("Salary");
           int mgrSin = rs.getInt("Mgr_sin");
           int dept = rs.getInt("D_no");

           employeeResult.add(new employeeModel(fn, ln, bd, sin, add, sal, mgrSin, dept));

           while (rs.next()) {
               fn = rs.getString("fname");
               ln = rs.getString("lname");
               bd = rs.getString("bdate");
               sin = rs.getInt("SIN");
               add = rs.getString("Address");
               sal = rs.getDouble("Salary");
               mgrSin = rs.getInt("Mgr_sin");
               dept = rs.getInt("D_no");

               employeeResult.add(new employeeModel(fn, ln, bd, sin, add, sal, mgrSin, dept));
           }
        }
        else {
           employeeResult = null;
        }

        closeQuery(stmt,rs);
        return employeeResult;
    }

    /**
     * @overload closequery
     *  Closes statements and and resultset
     * @param stmt an opened statement
     * @param rs an open resultset
     * @param preparedStmt  an opened prepared statement
     * @throws SQLException
     */
   private void closeQuery(Statement stmt, ResultSet rs, PreparedStatement preparedStmt) throws SQLException {
       rs.close();
       stmt.close();
       preparedStmt.close();
   }

    /**
     * @overload closequery
     *  Closes statements and and resultset
     * @param st an opened statement
     * @param rs an open resultset
     * @throws SQLException
     */
    void closeQuery(Statement st, ResultSet rs) throws SQLException {
        rs.close();
        st.close();
    }
}


