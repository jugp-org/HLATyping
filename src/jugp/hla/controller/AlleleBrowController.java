package jugp.hla.controller;


import jugp.hla.HLATyping;
import jugp.hla.database.DBConnection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jugp.hla.model.Allele;
import jugp.hla.model.Feature;


public class AlleleBrowController extends AbstractController {

	//private DBConnection dbConnect;
	DBConnection dbConnect = new DBConnection();
	
    // =================================================
    // Установка таблиц и колонок
    // =================================================
	// Таблица hla_alleles
    @FXML
    private TableView<Allele> alleleTable;
    @FXML
    private TableColumn<Allele, String> alleleId;
    @FXML
    private TableColumn<Allele, String> alleleName;
    @FXML
    private TableColumn<Allele, String> nucSequence;
    @FXML
    private TableColumn<Allele, String> releaseConfirmed;

	// Таблица hla_features
    @FXML
    private TableView<Feature> featureTable;
    @FXML
    private TableColumn<Feature, String> featureName;
    @FXML
    private TableColumn<Feature, String> featureStatus;
    @FXML
    private TableColumn<Feature, Integer> seqCoordinatesStart;
    @FXML
    private TableColumn<Feature, Integer> seqCoordinatesEnd;
    @FXML
    private TableColumn<Feature, Integer> seqLength;
    @FXML
    private TableColumn<Feature, String> featureSequence;
    @FXML
    private TableColumn<Feature, String> featureType;

    // =================================================
    // Данные 
    // =================================================
    private ObservableList<Allele> alleleData = FXCollections.observableArrayList();
    private ObservableList<Feature> featureData = FXCollections.observableArrayList();
 
    // Набор данных - переменная
    private ResultSet resSet;
    
    /**
     * Конструктор.
     * Конструктор вызывается раньше метода initialize().
     */
    public AlleleBrowController() {
    }

    /**
     * Инициализация класса-контроллера. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize() {
    	
        // Инициализация таблицы Allele
        alleleId.setCellValueFactory(
                cellData -> cellData.getValue().getAlleleId());
        alleleName.setCellValueFactory(
                cellData -> cellData.getValue().getAlleleName());
        nucSequence.setCellValueFactory(
                cellData -> cellData.getValue().getNucSequence());
        releaseConfirmed.setCellValueFactory(
                cellData -> cellData.getValue().getReleaseConfirmed());

        // Инициализация таблицы feature
        featureName.setCellValueFactory(
                cellData -> cellData.getValue().getFeatureName());
        featureStatus.setCellValueFactory(
                cellData -> cellData.getValue().getFeatureStatus());
        featureSequence.setCellValueFactory(
                cellData -> cellData.getValue().getFeatureSequence());
        featureType.setCellValueFactory(
                cellData -> cellData.getValue().getFeatureType());
        
        //featureSequence.setCellValueFactory(
        //		new PropertyValueFactory<Feature, String>("featureSequence"));
        
/*        
        featureName.setCellValueFactory(
        		new PropertyValueFactory<Feature, String>("featureName"));
        featureStatus.setCellValueFactory(
        		new PropertyValueFactory<Feature, String>("featureStatus"));
        seqCoordinatesStart.setCellValueFactory(
        		new PropertyValueFactory<Feature, Integer>("seqCoordinatesStart"));
        seqCoordinatesEnd.setCellValueFactory(
        		new PropertyValueFactory<Feature, Integer>("seqCoordinatesEnd"));
        seqLength.setCellValueFactory(
        		new PropertyValueFactory<Feature, Integer>("seqLength"));
*/

        // ===================================================
        // Меняем стиль строки 
        // ===================================================
        alleleTable.setRowFactory(row -> new TableRow<Allele>(){
            @Override
            public void updateItem(Allele item, boolean empty){
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    // Проверяем условие
                    if (item.getReleaseConfirmed().get().equals("Unconfirmed")) {
                        // Меняем для всех ячеек строки  
                        for(int i=0; i<getChildren().size();i++){
                            ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                            //((Labeled) getChildren().get(i)).setStyle("-fx-background-color: yellow");
                        }                        
                    }
                }
            }
        });        
        
        
        // ===================================================
        // Меняем стиль ячейки
        // ===================================================
        featureType.setCellFactory(column -> {
            return new TableCell<Feature, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); //This is mandatory

                    if (item == null || empty) { //If the cell is empty
                        setText(null);
                        setStyle("");
                    } else { //If the cell is not empty
                    	
                        setText(item); //Put the String data in the cell
                        
                        //We get here all the info of the Allele of this row
                        Feature cName = getTableView().getItems().get(getIndex());
                        
                        // Условие для изменения стиля
                        if (cName.getFeatureType().get().equals("Exon")) {
                            setTextFill(Color.RED); // Font color
                            // Если ячейка выделена, то фон не меняем
                            
                            //boolean bb = getTableView().getSelectionModel().getSelectedItems().contains(cName); 
                            
                            if (getTableView().getSelectionModel().getSelectedItems().contains(cName) == false)
                            	setStyle("-fx-background-color: yellow");
                        } else {
                        	/*
                            // Если ячейка выделена, то фон не меняем, а меняем только цвет текста
                            if(getTableView().getSelectionModel().getSelectedItems().contains(cName))
                                setTextFill(Color.WHITE);
                            else
                                setTextFill(Color.BLACK);
                            */
                        }
                    }
                }
            };
        });        

        
        // Очистка дополнительной информации об адресате.
        //showAlleleDetails(null);

        // Слушаем изменения выбора alleles, и при изменении отображаем features
        alleleTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadFeatureData(newValue));
        
        //loadAlleleData();
        alleleTable.setItems(alleleData);
        featureTable.setItems(featureData);

    }

    // ===============================================
    // Кнопка загрузки данных
    // ===============================================
    @FXML
    private void loadDataBtnClick() {
    	loadAlleleData();
    } 
    
    // ===============================================
    // Загрузка данных 
    // ===============================================
    public void loadAlleleData() {

    	// Установка соединения с БД
    	if (dbConnect.isConnected == 0) {
    		//dbConnect.SetConnection("localhost", "1433", "DNA_HLA", "integrated","","");
    		dbConnect.Connect();
    	}
    	
    	if (dbConnect.con != null) {
    		String sqlStr = "Select ha.allele_id, ha.allele_name, ha.sequence_nucsequence, ha.release_confirmed From hla_alleles ha With (Nolock)";
            try {
	    		resSet = dbConnect.ExecuteQuery(sqlStr);
				while (resSet.next()) {
				    //System.out.println(rs.getString(4) + " " + rs.getString(6));
				    alleleData.add(new Allele(resSet.getString(1), resSet.getString(2), resSet.getString(3), resSet.getString(4)));
				}
				resSet.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				message.showErrorMessage("Ошибка обращения к серверу баз данных:\n"+sqlStr,e.getMessage());
			}
    	}else {
			message.showErrorMessage("Coedineneie с сервером баз данных не установлено!","");
    	}
    	
    }
    
    // ===============================================
    // Загрузка таблицы hla_features 
    // ===============================================
    public void loadFeatureData(Allele allele) {
    	
        if (allele != null) {
        	// Очистим табличку features
    		featureData.clear();
        	
	  		String sqlStr = "Select fe.feature_name, fe.feature_status, fe.feature_nucsequence, fe.feature_type From hla_features fe With (Nolock) where fe.allele_id='"+allele.getAlleleId().get()+"'";
			//message.showErrorMessage("Строка SQL\n",sqlStr);
	  		
	        try {
	    		resSet = dbConnect.ExecuteQuery(sqlStr);
				while (resSet.next()) {
				    //System.out.println(rs.getString(4) + " " + rs.getString(6));
				    featureData.add(new Feature(resSet.getString(1), resSet.getString(2), resSet.getString(3), resSet.getString(4)));
				}
				resSet.close();
			} catch (SQLException e) {
				//e.printStackTrace();
				message.showErrorMessage("Ошибка обращения к серверу БД:\n"+sqlStr,e.getMessage());
			}
        }
    }
    
}

