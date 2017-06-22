

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.QueryParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {	
	public static void main(String[] args) throws Exception{
        ReadJson test = new ReadJson();
        // exchange , symbolFrom, symbolTo
        String exchange = "CCCAGG";
        String symbolFrom = "BTC";
        String symbolTo = "USD";
        String symbol = symbolFrom + ":" + symbolTo;
        JSONObject obj= test.getJson(exchange, symbolFrom, symbolTo);
        test.parseJson(obj);
        ArrayList<DataEntry> array = new ArrayList();
        array= test.getObjects();
      //in case of existing credentials, we set extra parameters: username&password
		DataClass mySQLUpdate = new DataClass("jdbc:mysql://localhost:3306/historicaldata", "com.mysql.jdbc.Driver", "MySQL");
		DataClass postgreSQLUpdate = new DataClass("jdbc:postgresql://localhost:5432/historicaldata", "org.postgresql.Driver", "PostgreSQL", "postgres", "postgresdb");
		DataClass msSQLServerUpdate = new DataClass("jdbc:sqlserver://EMMA-PC\\SQLEXPRESS;databaseName=historicaldata;integratedSecurity=true", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "SQLServer");
		
		for (DataEntry object : array) {
			Long date = object.getTime(); 
			Double open = object.getOpen();
			Double high = object.getHigh();
			Double low = object.getLow();
			Double close = object.getClose();
//			Double volumeFrom = object.getVolumeFrom();
//			Double volumeTo = object.getVolumeTo();
			
			//Double adjClose = object.getAdjClose());
			//String symbol  = object.getSymbol();
			mySQLUpdate.insertData(date, open, high, low, close, symbol, exchange);
			postgreSQLUpdate.insertData(date, open, high, low, close, symbol, exchange);
			msSQLServerUpdate.insertData(date, open, high, low, close, symbol, exchange);
			//System.out.println(date);
		} 
		
		
		mySQLUpdate.closeConnection();
		postgreSQLUpdate.closeConnection();
		msSQLServerUpdate.closeConnection();
	}
	
//	private static Double getDouble(String value) {
//		if (value != null)
//			return Double.parseDouble(value);
//		else
//			return null;
//	}

}
