package app.controllers.keys;

import app.stages.AppStages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IssueKeysController implements Initializable {


    public static String pageTitlePlaceholder;

    @FXML private Label pageTitleLabel;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem issueKeyButton, viewKeyTransactions;

    public IssueKeysController() throws IOException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
    }


    //USE THIS TO PREVENT THE STAGE FROM HAVING MULTIPLE INSTANCES ON BUTTON CLICK
    Stage issueKeyStage = AppStages.issueKeyStage();
    public void issueKeyButtonClicked(ActionEvent actionEvent) throws IOException {
       if (!issueKeyStage.isShowing()) {
           issueKeyStage.showAndWait();
       }
    }

    Stage transactionStage = AppStages.keyTransactionsHistoryStage();
    public void viewKeyTransactionsButtonClicked(ActionEvent actionEvent) {
        try {
            transactionStage.show();
        }catch (IllegalStateException ignore) {
            transactionStage.showAndWait();
        }

    }



}//end of class...
