package app.controllers.login;

import app.conf.AlertDialogs;
import app.conf.security.UserAuthentication;
import app.controllers.MainViewController;
import app.models.UsersModel;
import app.stages.AppStages;
import com.sun.tools.javac.Main;
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

public class LoginController extends UsersModel implements Initializable {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private MFXButton loginButton, closeButton;
    @FXML private Hyperlink resetPasswordLink;

    UserAuthentication AUTHENTICATION_OBJ = new UserAuthentication();

    //ACTION EVENTS IMPLEMENTATION
    @FXML void loginButtonClicked() {
        try{
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (AUTHENTICATION_OBJ.authenticateUser(username, password)) {
                MainViewController.loggedInUsername = Objects.equals(username, "") ? "Admin" : username;
                MainViewController.roleName = AUTHENTICATION_OBJ.getRoleName();
                int userId = fetchUserIdByName(username);
                logUserActivity(userId);
                loginButton.getScene().getWindow().hide();
                AppStages.mainView();
            } else {
                new AlertDialogs("INVALID CREDENTIALS", "The username or password is incorrect", "please provide correct credentials to login")
                        .errorAlert();
            }
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
