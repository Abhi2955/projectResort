import java.sql.*;
public class DbExsit {
	boolean dbFlag=true;
	public boolean isDbExsit()
	{
		Connection connection = null;
   		Statement statement = null;
    	try
    	{
        	Class.forName("com.mysql.jdbc.Driver");
        	connection = DriverManager.getConnection("jdbc:mysql://localhost/","root", "");
        	statement = connection.createStatement();
	        String sql = "CREATE DATABASE resort";
        	statement.executeUpdate(sql);
        	System.out.println("Database created!");
    	}
		catch (SQLException sqlException)
		{
        	if (sqlException.getErrorCode() == 1007)
        	{
            	dbFlag = false;
        	}
        	else
        	{
   		         isDbExsit();
        	}
    	}
    	catch (ClassNotFoundException e)
    	{
    	}
    	return dbFlag;
	}

}
