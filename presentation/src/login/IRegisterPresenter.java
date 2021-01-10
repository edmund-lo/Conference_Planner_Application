package login;

import common.IPresenter;
import javafx.event.ActionEvent;

public interface IRegisterPresenter extends IPresenter {
    void backButtonAction(ActionEvent actionEvent);
    void registerButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
}
