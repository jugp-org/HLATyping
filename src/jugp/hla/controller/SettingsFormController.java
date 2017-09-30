package jugp.hla.controller;

import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jugp.hla.HLATyping;
import jugp.hla.database.DBConnection;
import jugp.hla.model.Allele;
import jugp.hla.model.DBServer;

public class SettingsFormController extends AbstractController {
	
    // =================================================
    // Установка переменных формы
    // =================================================
    @FXML
    private TextField DBHost;
    @FXML
    private TextField DBName;
    @FXML
    private TextField DBPort;
    @FXML
    private TextField DBSecurity;
    @FXML
    private TextField DBLogin;
    @FXML
    private TextField DBPwd;
    
    @FXML
    ComboBox<DBServer> cBoxDBserver = new ComboBox<DBServer>();
    
    private ObservableList<DBServer> DBServerList;

    
    //=========================================================================
    //  Инициализация контроллера диалога
    //=========================================================================
    @FXML
    private void initialize() {

        Preferences prefs = Preferences.userNodeForPackage(HLATyping.class);

        DBHost.setText(prefs.get("DBHost", "localhost"));
        DBPort.setText(prefs.get("DBPort", "1433"));
        DBName.setText(prefs.get("DBName", "DNA_HLA"));
        DBSecurity.setText(prefs.get("DBSecurity", "integrated"));
        DBLogin.setText(prefs.get("DBLogin", ""));
        DBPwd.setText(prefs.get("DBPwd", ""));

        // Список серверов
        DBServer srv1 = new DBServer("sqlserver", "Microsoft SQL Server");
        DBServer srv2 = new DBServer("mysql", "MySQL Server");
        DBServer srv3 = new DBServer("oracle", "Oracle Database Server");
   
        //ObservableList<DBServer> List = FXCollections.observableArrayList(srv1, srv2, srv3);
        DBServerList = FXCollections.observableArrayList(srv1, srv2, srv3);
        cBoxDBserver.setItems(DBServerList);
        cBoxDBserver.getSelectionModel().select(1);
        
    }

    
    //=========================================================================
    // Процедуры обработки элементов формы
    //=========================================================================
    /**
     * OnClick Ok bottom SettingsForm
     */
    @FXML
    private void onClickSettingsOk() {
    	
    	// Сохраняем настройки
        Preferences prefs = Preferences.userNodeForPackage(HLATyping.class);
        
        prefs.put("DBHost", DBHost.getText());
        prefs.put("DBPort", DBPort.getText());
        prefs.put("DBName", DBName.getText());
        prefs.put("DBSecurity", DBSecurity.getText());
        prefs.put("DBLogin", DBLogin.getText());
        prefs.put("DBPwd", DBPwd.getText());

    	closeDialogStage();
    }

    /**
     * OnClick TestConnection! bottom SettingsForm
     */
    @FXML
    private void onClickTestConnection() {
    	DBConnection dBcon = new DBConnection();
    	if ( dBcon.SetConnection(DBHost.getText(), DBPort.getText(), DBName.getText(), DBSecurity.getText(), DBLogin.getText(), DBPwd.getText()) == 1 ) {
    	    message.showInfoMessage("Соединение установлено!");
    	} else {
    	    message.showErrorMessage("Ошибка соединения!",dBcon.error);
    	}
    }
    
    /**
     * OnClick Cancel bottom SettingsForm
     */
    @FXML
    private void onClickSettingsCancel() {
    	closeDialogStage();
    }
}
