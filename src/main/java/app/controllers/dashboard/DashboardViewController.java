package app.controllers.dashboard;

import app.interfaces.ViewTitles;
import app.methods.SpecialMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    public Label freeKeys;
    public Label roomsLabel;
    public Label bookedKeysLabel;
    public Label keysLabel;
    public Label approvedLabel;
    public Label blocksLabel;
    //FXML FILES
    @FXML private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        setParameters();
    }


    private void setParameters() {
        SpecialMethods.setDashboardValues(blocksLabel, "totalBlocks");
        SpecialMethods.setDashboardValues(approvedLabel, "approvedPersons");
        SpecialMethods.setDashboardValues(keysLabel, "totalKeys");
        SpecialMethods.setDashboardValues(bookedKeysLabel, "bookedKeys");
        SpecialMethods.setDashboardValues(freeKeys, "freeKeys");
        SpecialMethods.setDashboardValues(roomsLabel, "totalRooms");

    }


}// end of class...
