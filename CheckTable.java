import java.sql.*;
public class CheckTable
{
	public static void main(String[] args) 
	{
		String tables[]={"adminlog","product","categories_list","info_resort","table_items"};
		String sql0="create table adminlog('username' varchar(20) primary key,'password' varchar(20),'sequirty_Answer1' varchar(50),'sequirty_Answer2' varchar(50),'sequirty_Answer3' varchar(50),'tables' int, type char(1))";
		String sql1="create table product('productname' varchar(50),'category' varchar(50),'price' double)";
		String sql2="create table categories_list('name_categories' varchar(50))";
		String sql3="create table info_resort('resort_name' varchar(50),'owner_name' varchar(50),'contact' varchar(15),'mail' varchar(32),'gstin_no' varchar(50))";
		String sql4="create table table_items('booked_table' varchar(12),'item' varchar(30),'quantity' int,'price' double)";
		String queries[]={sql0,sql1,sql2,sql3,sql4};
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost/resort","root", "");
        	java.sql.DatabaseMetaData dbm = con.getMetaData();
        	for(int i=0;i<5;i++)
        	{
        		ResultSet rs = dbm.getTables(null, null,tables[i], null);
				if (rs.next()) {
				System.out.println(tables[i]+" Table Exists !");
				}else{
				System.out.println(tables[i]+" Table Doesn't Exist !");
				}
        	}
		}
		catch(Exception exp){}
	}
}