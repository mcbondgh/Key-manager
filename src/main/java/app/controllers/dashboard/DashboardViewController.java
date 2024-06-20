package app.controllers.dashboard;

import app.interfaces.ViewTitles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    //FXML FILES
    @FXML private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
    }

}// end of class...
