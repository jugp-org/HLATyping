package jugp.hla.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.prefs.Preferences;

import jugp.hla.HLATyping;

public class DBConnection {

	public String server; // Тип сервера - сейчас используется sqlserver
	public String connectionUrl;
	public String host;
	public String port;
	public String dbname;
	public String security;
	public String login;
	public String pwd;

	public int isConnected;
	public String error;

	public Connection con = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	
	// Create a variable for the connection string.
	//private String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
	//	"databaseName=JugP_HLA;integratedSecurity=true;";

	// Конструктор по умолчанию
	public DBConnection() {
		this.server			= "sqlserver";
		this.host			= "localhost";
		this.isConnected	= 0;
		this.connectionUrl	= "";
		this.port			= "1433";
		this.dbname			= "";
		this.security		= "integrated";
		this.login			= "";
		this.pwd			= "";
		this.error			= "";
	}

	// Деструктор
    protected void finalize ( ) {
    	// Перед выходом все закрываем
    	//this.Close();
    }
    

	// Проверка соединения
	public int checkConnection(String url, String port, String dbname, String security, String login, String pwd) {
		return Connect();
	}
	
	// Установка параметров из сохраненных значений
	public void RestorePrefParams() {
        Preferences prefs = Preferences.userNodeForPackage(HLATyping.class);
        this.server			= prefs.get("DBServer", "sqlserver");
        this.host			= prefs.get("DBHost", "localhost");
        this.security		= prefs.get("DBSecurity", "integrated");
        this.port			= prefs.get("DBPort", "1433");
        this.dbname			= prefs.get("DBName", "DNA_HLA");
        this.login			= prefs.get("DBLogin", "");
        this.pwd			= prefs.get("DBPwd", "");
	}

	// Соединение с установленными параметрами
	public int Connect() {
		// Восстановление параметров
		this.RestorePrefParams();
		
    	try {
    		// Establish the connection.
    		if (security.equals("integrated")) {
        		this.connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" +	"databaseName=" + dbname + ";integratedSecurity=true;";
    		}else {
        		this.connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" +	"databaseName=" + dbname + ";user="+login+";password="+pwd+";";
    		}
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	this.con = DriverManager.getConnection(this.connectionUrl);
    		this.isConnected = 1;
    	}
        // Handle any errors that may have occurred.
        catch (Exception e) {
        	//e.printStackTrace();
    		this.isConnected = 0;
        	this.error=e.getMessage();
        }
		return isConnected;
	}
	
	// установка соединения с параметрами
	public int SetConnection(String host, String port, String dbname, String security, String login, String pwd) {
    	try {
    		// Establish the connection.
    		if (security.equals("integrated")) {
        		this.connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" +	"databaseName=" + dbname + ";integratedSecurity=true;";
    		}else {
        		this.connectionUrl = "jdbc:sqlserver://" + host + ":" + port + ";" +	"databaseName=" + dbname + ";user="+login+";password="+pwd+";";
    		}
    		
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	this.con = DriverManager.getConnection(this.connectionUrl);
    		this.isConnected = 1;
    	}
        // Handle any errors that may have occurred.
        catch (Exception e) {
        	//e.printStackTrace();
    		this.isConnected = 0;
        	this.error=e.getMessage()+"\n Строка соединения: "+this.connectionUrl;
        }
		return isConnected;
	}
	
	public ResultSet ExecuteQuery(String SQL) {
		this.rs=null;
		if (this.con != null) {
	    	try {
        		// Create and execute an SQL statement that returns some data.
        		this.stmt = con.createStatement();
        		this.rs = stmt.executeQuery(SQL);
	    	}
			// Handle any errors that may have occurred.
			catch (Exception e) {
				//e.printStackTrace();
	        	this.error=e.getMessage();
			}
		}
		return rs;
	}

	public boolean Execute(String SQL) {
		boolean result = false; 
		if (this.con != null) {
	    	try {
        		// Create and execute an SQL statement that returns some data.
        		this.stmt = con.createStatement();
        		result = stmt.execute(SQL);
	    	}
			// Handle any errors that may have occurred.
			catch (Exception e) {
				//e.printStackTrace();
	        	this.error=e.getMessage();
			}
		}
		return result;
	}
	
	public void Close() {
		if (this.rs != null)   try { this.rs.close(); } catch(Exception e) {}
    	if (this.stmt != null) try { this.stmt.close(); } catch(Exception e) {}
    	if (this.con != null)  try { this.con.close(); } catch(Exception e) {}
	}

}
