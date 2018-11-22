package no_one;
// this class used for configure your project
// the values in this class should be static and will be visited by other class
// you should eidt these values when your environment changed

public class Configure {
    // user name
    public final static String USERNAME = "root";
    // password
    public final static String PASSWORD = "123456";
    // your database name
    public final static String DBNAME   = "test";
    // mysql driver
    public final static String DRIVER   = "com.mysql.jdbc.Driver";
    // mysql url
    public final static String URL      = "jdbc:mysql://localhost:3306/" + DBNAME;
    // must bigger than the number of the keyword in your database table 
    public final static int    TABLELEN = 10;
}