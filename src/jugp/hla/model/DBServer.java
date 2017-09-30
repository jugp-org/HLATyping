package jugp.hla.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBServer {	
	
   public  String code;
   private String name;
 
    public DBServer() {
    }
 
    public DBServer(String code, String name) {
        this.code = code;
        this.name = name;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString()  {
        return this.name;
    }	

}
