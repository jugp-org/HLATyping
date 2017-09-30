package jugp.hla.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Messages {
	
	public void Messages() {
		
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

}
