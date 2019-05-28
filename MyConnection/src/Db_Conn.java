

import java.sql.Connection;
import java.sql.DriverManager;

public class Db_Conn {
	
	private Connection conn;
	String Url = "jdbc:mysql://localhost/esame";
	String users = "root";
	String  pass = "";
	
	public Db_Conn(){
		
		try {
			Connection conn = DriverManager.getConnection(Url,users,pass);
			System.out.println("Connessione eseguita");
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
}