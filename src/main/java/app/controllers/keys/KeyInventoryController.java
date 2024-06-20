package app.controllers.keys;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class KeyInventoryController implements Initializable {

    @FXML private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
    }


}//end of class
