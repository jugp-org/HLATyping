package jugp.hla.module;

import java.io.File;
import java.sql.ResultSet;

import jugp.hla.database.DBConnection;

public class HLADatabaseLoader {

	//private DBConnection dbConnect;
	DBConnection dbConnect = new DBConnection();
	
    // Набор данных - переменная
    private ResultSet resSet;
    
	public void LoadXMLData(File file) {
		
		//System.out.println("Файл создан: " + file.getAbsolutePath());
		
		if (dbConnect.Connect() == 1) {
			String sqlStr = "exec hla_XML_read @file_name='"+file.getAbsolutePath()+"'";
			if (dbConnect.Execute(sqlStr) == false) {
				
			}
		}
	}
	

}
