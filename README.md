#About the Project 
business Management is a mini project I created based on the ERD diagram a database course I took.
The application is intended to be used by someone high up in a company that is trying to view, add, delete and edit Employees.
The person using the app can find an employee supervision department, projects and dependents.

The goal of this project is to implement a program based on an ERD diagram in a hackathon time frame(24hrs)
Another goal of this project is to implement a client-server database program.

The focus on this project is the ERD, hence the frontend will simply be a terminal.

The entire project will not be completed

#Connecting to the database.
The database may be redeployed using the business.sql file located in the root. 
The file contains a dump of the MySQL database as of July 29th, 2022.

#Build and Development Instructions
To run the project, open businessManagement inside intellij.

In order to ensure a connection to a properly configured database, setup a database using the business.sql file. 
Once the database is setup, point to it inside mainCollection/DBConnectionManager inside the connection string in the following format

    private static String url = "jdbc:mysql://localhost:3306/business";
    private static String username = {username};
    private static String password = {password};