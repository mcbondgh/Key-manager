package app.controllers.report;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML private Label pageTitleLabel;

    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
    }
}
