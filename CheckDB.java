import java.sql.*;
public class CheckDB
{

	private static void DbConnect()
	{
	    	boolean dbFlag=true;
		Connection connection = null;
   		 Statement statement = null;
    	try
    	{
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection("jdbc:mysql://localhost/","root", "");
        	statement = connection.createStatement();
	        String sql = "CREATE DATABASE resort";
    	    //To delete database: sql = "DROP DATABASE DBNAME";
        	statement.executeUpdate(sql);
        	System.out.println("Database created!");
    	}
		catch (SQLException sqlException)
		{
        	if (sqlException.getErrorCode() == 1007)
        	{
            	// Database already exists error
            	System.out.println(sqlException.getMessage());
            	dbFlag = false;
        	}
        	else
        	{
	            // Some other problems, e.g. Server down, no permission, etc
   		         sqlException.printStackTrace();
   		         DbConnect();
        	}
    	}
    	catch (ClassNotFoundException e)
    	{
        // No driver class found!
    	}
	}
	public static void main(String[] args)
	{
		 	DbConnect();
	}
}
