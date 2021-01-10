package login;

import common.IPresenter;
import javafx.event.ActionEvent;

public interface ILoginPresenter extends IPresenter {
    void loginButtonAction(ActionEvent actionEvent);
    void registerButtonAction(ActionEvent actionEvent);
    void forgotPasswordButtonAction(ActionEvent actionEvent);
    void setResultText(String resultText, String status);
}
