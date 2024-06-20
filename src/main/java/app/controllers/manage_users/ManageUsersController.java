package app.controllers.manage_users;

import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersController implements Initializable {

    @FXML private Label pageTitleLabel;

    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
    }

    @FXML
    VBox formLayout, tableLayout;
    @FXML
    private HBox vBox;

    void toggleViews() {
        if (vBox.getChildren().contains(formLayout)) {
            vBox.getChildren().remove(formLayout);
        } else {
            vBox.getChildren().add(0,formLayout);
        }
    }



}//end of class...
