package login;

import common.IPresenter;
import javafx.event.ActionEvent;

public interface IForgotPresenter extends IPresenter {
    void backButtonAction(ActionEvent actionEvent);
    void previousButtonAction(ActionEvent actionEvent);
    void resetButtonAction(ActionEvent actionEvent);
    void confirmButtonAction(ActionEvent actionEvent);
    void displaySecurityButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status, int index);
}
