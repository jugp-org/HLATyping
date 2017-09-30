package jugp.hla.controller;

import javafx.stage.Stage;
import jugp.hla.HLATyping;
import jugp.hla.util.Messages;

public class AbstractController {

    //=========================================================================
    // Инициализация свойств и методов класса контроллера формы 
    //=========================================================================
    protected HLATyping mainApp;	// Ссылка на главное приложение
    protected Stage dialogStage;	// Установка макета для сцен формы
    protected Messages message = new Messages();	// Сообщения
    
    public void AbstractController() {
    }

    // Вызывается главным приложением, которое даёт на себя ссылку.
    public void setMainApp(HLATyping mainApp) {
        this.mainApp = mainApp;
    }

    // Вызывается главным приложением, которое даёт на себя ссылку.
    public HLATyping getMainApp() {
        return this.mainApp;
    }
    
    // Вызывается главным приложением, которое даёт на себя ссылку.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    // Закрываем форму диалога
    public void closeDialogStage() {
        this.dialogStage.close();
    }
    
    
    
}
