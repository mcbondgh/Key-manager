package app.controllers.login;

import app.controllers.MainViewController;
import app.stages.AppStages;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private MFXButton loginButton, closeButton;
    @FXML private Hyperlink resetPasswordLink;





    //ACTION EVENTS IMPLEMENTATION
    @FXML void loginButtonClicked() {
        try{
            String username = usernameField.getText();
            String password = passwordField.getText();
            MainViewController.loggedInUsername = Objects.equals(username, "") ? "Admin" : username;

            loginButton.getScene().getWindow().hide();
            AppStages.mainView();
        }catch (Exception ignore) {}

    }
    @FXML void closeButtonClicked() {
        Platform.exit();
    }

    @FXML void requestPasswordReset() {
        try{
            AppStages.passwordResetStage();
        }catch (Exception ignored){}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
