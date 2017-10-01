package jugp.hla.controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jugp.hla.HLATyping;
import jugp.hla.database.DBConnection;
import jugp.hla.module.HLADatabaseLoader;
import jugp.hla.reports.ReportForm;
import net.sf.jasperreports.engine.JRException;

/**
 * ���������� ��� ��������� ������. �������� ����� ������������� �������
 * ����� ����������, ���������� ������ ���� � �����, ��� ����� ���������
 * ��������� �������� JavaFX.
 * 
 * @author Yuriy Shirokov
 */
public class MainLayoutController {

    // ������ �� ������� ����������
    private HLATyping mainApp;

    /**
     * ���������� ������� �����������, ����� �������� ������ �� ������ ����.
     * 
     * @param mainApp
     */
    public void setMainApp(HLATyping mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * ��������� FileChooser, ����� ������������ ���� �����������
     * ������� ���� ��� ��������.
     */
    @FXML
    private void menuOpen() {
        FileChooser fileChooser = new FileChooser();

        // ����� ������ ����������
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "FASTQ files (*.fastq)", "*.fastq");
        fileChooser.getExtensionFilters().add(extFilter);

        // ���������� ������ �������� �����
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
        	// ��������� ����
        	DBConnection dbConnect = new DBConnection();
      		if (dbConnect.Connect() == 1) {
      			
      			
       			String sqlStr = "exec DNA2_FASTQ.dbo.fastq_data_read @file_name='"+file.getAbsolutePath()+"'";
       			if (dbConnect.Execute(sqlStr) == false) {
        			mainApp.showErrorMessage("������ �������� ������!",dbConnect.error);
        		}else {
        			mainApp.showInfoMessage("���� ��������!");
        		}
       		}
      		dbConnect.Close();
        }
    }

    
    //==========================================================
    // ���� ��� ������������
    // =========================================================
    @FXML
    private void menuTest() {
    	mainApp.showTestAligner();
    }
    
    //==========================================================
    // �������� �����
    // =========================================================
    @FXML
    private void menuPrint() {
    	
    	ReportForm testRep = new ReportForm();
    	try {
			testRep.showReport();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    

    //==========================================================
    // �������� ���� HLA
    // =========================================================
    @FXML
    private void menuAlleleBrow() {
    	mainApp.alleleBrow();
    }
    
    /**
     * ��������� ����.
     * ���� ���� �� ������, �� ������������ ������ "save as".
     */
    @FXML
    private void menuSave() {
//        File personFile = mainApp.getPersonFilePath();
//        if (personFile != null) {
//            mainApp.savePersonDataToFile(personFile);
//        } else {
//            menuSaveAs();
//        }
    }

    /**
     * ��������� FileChooser, ����� ������������ ���� �����������
     * ������� ����, ���� ����� ��������� ������
     */
    @FXML
    private void menuSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // ����� ������ ����������
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // ���������� ������ ���������� �����
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
//            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Settings
     */
    @FXML
    private void menuSettings() {
    	//mainApp.settingsForm();
    	mainApp.openForm("view/SettingsForm.fxml", "SettingsFormController");
    }


    /**
     * LoadHLADataBase
     */
    @FXML
    private void menuLoadHLA() {
        FileChooser fileChooser = new FileChooser();

        // ����� ������ ����������
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "hla.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // ���������� ������ �������� �����
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
        	// ��������� ����
        	DBConnection dbConnect = new DBConnection();
      		if (dbConnect.Connect() == 1) {
       			String sqlStr = "exec hla2_XML_read @file_name='"+file.getAbsolutePath()+"'";
       			if (dbConnect.Execute(sqlStr) == false) {
        			mainApp.showErrorMessage("������ �������� ������!",dbConnect.error);
        		}else {
        			mainApp.showInfoMessage("���� ��������!");
        		}
       		}
      		dbConnect.Close();
        }
    }
    
    
    
    /**
     * ��������� ���������� ���� about.
     */
    @FXML
    private void menuAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("HLA-Typing");
        alert.setHeaderText("About");
        alert.setContentText("Author: ");

        alert.showAndWait();
    }

    /**
     * ��������� ����������.
     */
    @FXML
    private void menuExit() {
        System.exit(0);
    }
}