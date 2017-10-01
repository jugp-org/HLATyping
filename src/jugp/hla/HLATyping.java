package jugp.hla;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import jugp.hla.util.Commons;
import jugp.hla.model.*;
import jugp.hla.view.*;
import jugp.hla.controller.*;
import jugp.hla.database.DBConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HLATyping extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;
    /**
     * �����������
     */
    public HLATyping() {
    }
    
    /*
     * ������ ����������
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("DNA-HLA");
        this.primaryStage.getIcons().add(new Image("file:resources/images/dnat_ico96.png"));
        //this.primaryStage.getIcons().add(new Image("file:dnat_ico96.png"));
        
        //System.out.println("End Of The Show!");
        
        
        // ������� ����
        initmainLayout();
        
        // �������� ���������� � ����� ������
        checkDatabase();
    }

    /**
     * �������������� ����� �������� ����
     */
    public void initmainLayout() {
        try {
            // ��������� �������� ����� �� fxml �����.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HLATyping.class.getResource("view/MainLayout.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            primaryStage.setScene(scene);
            
            // ��� ����������� ������ � �������� ����������.
            MainLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            // ���������� ���� ����������
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���������� ������� �����.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    
    /**
     * ��������� ���� ��� ������������ 
     * 
     */
    public void showTestAligner() {

	    try {
	        // ��������� fxml-���� � ������ ����� �����
	        // ��� ������������ ����������� ����.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(HLATyping.class.getResource("view/TestAligner.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("TestAligner");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
            // ��� ����������� ������ � �������� ����������.
	        TestAlignerController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
	        
	        //controller.setPerson(person);
	
	        // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
	        dialogStage.showAndWait();
	
	        //return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    /**
     * ��������� ���� ��� ��������� ������� 
     * 
     */
    public void alleleBrow() {

	    try {
	        // ��������� fxml-���� � ������ ����� �����
	        // ��� ������������ ����������� ����.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(HLATyping.class.getResource("view/alleleBrow.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Alleles");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
            // ��� ����������� ������ � �������� ����������.
	        AlleleBrowController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
	        
	        //controller.setPerson(person);
	
	        // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
	        dialogStage.showAndWait();
	
	        //return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    

    /**
     * ��������� ���� ��� ��������� �������� 
     * 
     */
    public void settingsForm() {
	    try {
	        // ��������� fxml-���� � ������ ����� �����
	        // ��� ������������ ����������� ����.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(HLATyping.class.getResource("view/SettingsForm.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Settings");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	
	        // ��� ����������� ������ � �������� ����������.
	        SettingsFormController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(this);
	
	        // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
	        dialogStage.showAndWait();
	
	        //return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    

    /**
     * ��������� ���� (����� �����) 
     * 
     */
    public void openForm(String formFXML, String formControllerClassName) {
	    try {
	        // ��������� fxml-���� � ������ ����� �����
	        // ��� ������������ ����������� ����.
	        FXMLLoader formLoader = new FXMLLoader();
	        formLoader.setLocation(HLATyping.class.getResource(formFXML));
	        AnchorPane formPage = (AnchorPane) formLoader.load();
	
	        // ������ ���������� ���� Stage.
	        Stage formStage = new Stage();
	        formStage.setTitle("Settings");
	        formStage.initModality(Modality.WINDOW_MODAL);
	        formStage.initOwner(primaryStage);
	        Scene formScene = new Scene(formPage);
	        formStage.setScene(formScene);
	
	        // ��� ����������� ������ � �������� ����������.
	        AbstractController formController = formLoader.getController();
	        formController.setMainApp(this);
	        formController.setDialogStage(formStage);
	
	        // ���������� ���������� ���� � ���, ���� ������������ ��� �� �������
	        formStage.showAndWait();
	
	        //return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
    /**
     * ����� ��������� �� �������
     */
    public void showErrorMessage(String header ,String mess) {
    	showMessage(AlertType.ERROR,"������",header,mess);
    }
    
    /**
     * ����� ��������� � �����������
     */
    public void showInfoMessage(String mess) {
    	showMessage(AlertType.INFORMATION,"����������","",mess);
    }
    
    /**
     * ����� ���� ���������
     */
    public void showMessage(AlertType aType, String title, String header ,String mess) {
	    Alert alert = new Alert(aType);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(mess);
	    alert.showAndWait();
    }

    /**
     * ����������� �������� ����������� � ���� ������
     */
    public void checkDatabase() {
    	DBConnection DBConn = new DBConnection();
        if (DBConn.Connect() == 0) {
        	showErrorMessage("������ ���������� � ����� ������! ��������� ���������!",DBConn.error);
        }
    }
    
    
}