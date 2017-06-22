 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataClass {
	Connection conn;
	Statement st;
	ResultSet result; 
	String sgbdName;
	//if there are no credential set for db
	public DataClass(String url, String className, String sgbdName){
		this(url, className, sgbdName, null, null);
	}
	//if there are credential set for db
	public DataClass(String url, String className, String sgbdName, String username, String password){
		this.sgbdName = sgbdName;
		if(username == null){
			username = "root";
		}
		if(password == null){
			password = "";
		}
		try{
			//name of the driver user for connection
			Class.forName(className);
			//establishing connection
			conn = DriverManager.getConnection(url, username, password);
			st = conn.createStatement();
			System.out.println(sgbdName + " database connection established");
			
		}
		catch (Exception e) {
			System.err.println("Cannot connect to " + sgbdName +  " database server"); 
            System.err.println(e.getMessage());
		}
	}
	//inserting test data into the databases
	public void insertData(Long date, Double open, Double high, Double low, Double close, String symbol, String exchange){
		try{
			String time = convertToDate(date);
			String query = "";
			if(sgbdName == "SQLServer"){
				query = "INSERT INTO data (date,openm,high,low,closem,symbol,exchange) "
						+ " VALUES ('" + time + "','" + open + "','" + high + "','" + low + "','" + close + "','" + symbol + "','" + exchange + "')";
			}
//			if(sgbdName == "SQLServer"){
//				query = "INSERT INTO data (date,openm,high,low,closem,volume,adjclose,symbol) "
//						+ " VALUES (" + time + "," + open + "," + high + "," + low + "," + close + "," + volume + "," + null + ",'" + symbol + "," + exchange + "')";
//			}
			else{
				query = "INSERT INTO data (date,open,high,low,close,symbol,exchange) "
					+ " VALUES ('" + time + "','" + open + "','" + high + "','" + low + "','" + close + "','" + symbol + "','" + exchange +  "')";
			}
//			else{
//			query = "INSERT INTO data (date,open,high,low,close,volume,adjclose,symbol) "
//				+ " VALUES (" + time + "," + open + "," + high + "," + low + "," + close + "," + volume + "," + null + ",'" + symbol + exchange +  "')";
//			}
			st.executeUpdate(query);
			System.out.println("Data has been inserted into " + sgbdName + " database");
		}
		catch (Exception e) {
			System.err.println("Cannot insert data into " + sgbdName + " database");
			System.err.println(e);
		}
	}
	//closing connection
	public void closeConnection(){
		if (conn != null) {
          try {
              conn.close();
              System.out.println(sgbdName + " database connection terminated");
          } 
          catch (Exception e) {
          	System.err.println(e.getMessage());
          } 
		}
	}
	
	private String convertToDate(Long millis) {
		Date date = new Date(millis);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
		
	}
}

