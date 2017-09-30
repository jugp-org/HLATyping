package jugp.hla.view;

import java.awt.Cursor;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import jugp.hla.HLATyping;
import jugp.hla.formats.*;
import jugp.hla.model.*;
import jugp.hla.module.*;
import jugp.hla.matrix.*;
import jugp.hla.util.*;

public class TestAlignerController {

    @FXML
    private TextArea etalonField;
    @FXML
    private TextArea exampleField;
    @FXML
    private TextArea diffField;

	/**
	 * Logger
	 */
	private static final Logger logger = Logger
			.getLogger(SmithWatermanGotoh.class.getName());
    

    private Stage dialogStage;
    //private Sequence sequence;
    private boolean okClicked = false;
    
    /**
     * Loaded scoring matrices
     */
    private HashMap matrices = new HashMap();
    

    /**
     * �������������� �����-����������. ���� ����� ���������� �������������
     * ����� ����, ��� fxml-���� ����� ��������.
     */
    @FXML
    private void initialize() {
    }

    /**
     * ������������� ����� ��� ����� ����.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    
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
     * Returns true, ���� ������������ ������� OK, � ������ ������ false.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * ����������, ����� ������������ ������� �� ������ OK.
     */
    @FXML
    private void handleGo() {
        if (isInputValid()) {
        	align();
        }
    }
    
    
    /**
     * ������������
     */
    private void align() {
        
        Sequence sequence1 = null;
        try {
            logger.info("Processing sequence #1...");
            sequence1 = SequenceParser.parse(etalonField.getText());
            logger.info("Finished processing sequence #1");
        } catch (Exception e) {
            String message = "Failed parsing sequence #1: " + e.getMessage();
            //logger.log(Level.SEVERE, message, e);
            mainApp.showErrorMessage(message,"");
            etalonField.requestFocus();
            return;
        }
        
        Sequence sequence2 = null;
        try {
            logger.info("Processing sequence #2...");
            sequence2 = SequenceParser.parse(exampleField.getText());
            logger.info("Finished processing sequence #2");
        } catch (Exception e) {
            String message = "Failed parsing sequence #2: " + e.getMessage();
            logger.log(Level.SEVERE, message, e);
            mainApp.showErrorMessage(message,"");
            exampleField.requestFocus();
            return;
        }
        
        logger.info("Aliging...");
        try {
            //setCursor(new Cursor(Cursor.WAIT_CURSOR));
        	long start = System.currentTimeMillis();

            // ����� ��������� ������������ 
            Alignment alignment = AlignAlrorithm.align(sequence1, sequence2);
            long end = System.currentTimeMillis();
            logger.info("Finished aligning in " + (end - start) + " milliseconds");
            
            StringBuffer buffer = new StringBuffer();
            
            buffer.append(alignment.getSummary());
            buffer.append(Commons.getLineSeparator());
            
            String formatId = (String) "FASTA";
            String formattedAlignment = FormatFactory.getInstance().getFormat(formatId).format(alignment);
            buffer.append(formattedAlignment);
            
    		StringBuffer diffStr = new StringBuffer();
            char[] m =alignment.getMarkupLine();
            for (int i = 0; i < m.length; i++) {
            	diffStr.append(m[i]);
            }
            
            diffField.setText("");
            diffField.setText(diffStr.toString());
    		
            
        } catch (Error error) {
            String message = "Failed aligning: " + error.getMessage();
            //logger.log(Level.SEVERE, message, error);
            mainApp.showErrorMessage("������!",message);
        } catch (Exception exception) {
            String message = "Failed aligning: " + exception.getMessage();
            //logger.log(Level.SEVERE, message, exception);
            mainApp.showErrorMessage("������!",message);
        } finally {
            //setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	exampleField.requestFocus();
        }
    }    

    
    

    /**
     * ����������, ����� ������������ ������� �� ������ Cancel.
     */
    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    /**
     * ��������� ���������������� ���� � ��������� �����.
     * 
     * @return true, ���� ���������������� ���� ���������
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (etalonField.getText() == null || etalonField.getText().length() == 0) {
            errorMessage += "No valid Etalon!\n"; 
        }
        if (exampleField.getText() == null || exampleField.getText().length() == 0) {
            errorMessage += "No valid example!\n"; 
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {

        	// ���������� ��������� �� ������.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        	
        	//mainApp.errorMessage("Wrong input!","Please set correct values!");

            return false;
        }
    }

}