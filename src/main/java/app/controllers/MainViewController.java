package app.controllers;

import app.controllers.dashboard.DashboardViewController;
import app.controllers.keys.IssueKeysController;
import app.controllers.keys.KeyInventoryController;
import app.controllers.keys.KeyScheduleController;
import app.controllers.manage_users.ManageUsersController;
import app.controllers.blocks.BlocksAndOfficesController;
import app.controllers.personnel.PersonnelController;
import app.controllers.report.ReportsController;
import app.controllers.settings.SettingsViewController;
import app.methods.SpecialMethods;
import app.stages.AppStages;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable{
    public static String viewTitlePlaceholder;
    public static String loggedInUsername;

    //FXML FILE EJECTIONS
    @FXML private Label currentUserLabel, viewTitle, notificationCounter;
    @FXML private Button signoutButton;
    @FXML private BorderPane borderPane;
    @FXML private MFXButton notificationNav, keyInventoryNav, manageUserNav, keyScheduleNav;
    @FXML private MFXButton reportsNav, settingsNav, dashboardNav, personnelNav;
    @FXML private MFXButton keyIssuanceNav;
    @FXML private HBox scheduleBox;
    @FXML private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dashboardNavClicked();
            currentUserLabel.setText(loggedInUsername );
            vBox.getChildren().remove(scheduleBox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //ACTION EVENT METHODS IMPLEMENTATION
    public void dashboardNavClicked() throws IOException {
        String fxmlFile = "views/dashboard-view.fxml";
        DashboardViewController.pageTitlePlaceholder = dashboardNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void manageUsersNavClicked() throws IOException {
        String fxmlFile = "views/manage-users-view.fxml";
        ManageUsersController.pageTitlePlaceholder = manageUserNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void keyIssuanceNavClicked() throws IOException {
        String fxmlFile = "views/key-issuance-view.fxml";
        IssueKeysController.pageTitlePlaceholder = keyIssuanceNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void blocksAndOfficesNavClicked() throws IOException {
        String fxmlFile = "views/blocks-offices-view.fxml";
        BlocksAndOfficesController.pageTitlePlaceholder = notificationNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void reportsNavClicked() throws IOException {
        String fxmlFile = "views/reports-view.fxml";
        ReportsController.pageTitlePlaceholder = reportsNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void settingsNavClicked() throws IOException {
        String fxmlFile = "views/settings-view.fxml";
        SettingsViewController.pageTitlePlaceholder = settingsNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void keyInventoryNavClicked() throws IOException {
        String fxmlFile = "views/key-inventory-view.fxml";
        KeyInventoryController.pageTitlePlaceholder = keyInventoryNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void managePersonnelNavClicked() throws IOException {
        String fxmlFile = "views/personnel-view.fxml";
        PersonnelController.pageTitlePlaceholder = personnelNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }
    public void keyScheduleNavClicked() throws IOException {
        String fxmlFile = "views/key-schedule-view.fxml";
        KeyScheduleController.pageTitlePlaceholder = keyScheduleNav.getText();
        SpecialMethods.ChangeView(fxmlFile, borderPane);
    }


    @FXML void logOutUser() throws IOException {
        signoutButton.getScene().getWindow().hide();
        AppStages.loginStage();
    }


}//end of class