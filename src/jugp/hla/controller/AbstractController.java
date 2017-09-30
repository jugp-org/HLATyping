package jugp.hla.controller;

import javafx.stage.Stage;
import jugp.hla.HLATyping;
import jugp.hla.util.Messages;

public class AbstractController {

    //=========================================================================
    // ������������� ������� � ������� ������ ����������� ����� 
    //=========================================================================
    protected HLATyping mainApp;	// ������ �� ������� ����������
    protected Stage dialogStage;	// ��������� ������ ��� ���� �����
    protected Messages message = new Messages();	// ���������
    
    public void AbstractController() {
    }

    // ���������� ������� �����������, ������� ��� �� ���� ������.
    public void setMainApp(HLATyping mainApp) {
        this.mainApp = mainApp;
    }

    // ���������� ������� �����������, ������� ��� �� ���� ������.
    public HLATyping getMainApp() {
        return this.mainApp;
    }
    
    // ���������� ������� �����������, ������� ��� �� ���� ������.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    // ��������� ����� �������
    public void closeDialogStage() {
        this.dialogStage.close();
    }
    
    
    
}
