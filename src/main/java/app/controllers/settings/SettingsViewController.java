package app.controllers.settings;

import app.conf.AlertDialogs;
import app.entities.SettingsEntity;
import app.methods.SpecialMethods;
import app.models.SettingsModel;
import app.roles.Roles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;


public class SettingsViewController extends SettingsModel implements Initializable {

    public CheckBox blocksAndOffices;
    public CheckBox issueKeys;
    public CheckBox keyTransactions;
    public CheckBox managePeople;
    public CheckBox manageUsers;
    public CheckBox systemConfig;
    public CheckBox manageKeys;
    public Button saveButton;
    @FXML
    private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @FXML
    private GridPane gridPane;
    @FXML
    private ComboBox<Roles> roleSelector;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setRoles(roleSelector);
    }

    @FXML void roleSelectorOnAction() {
        setRoleParameters(roleSelector.getValue().toString());
    }

    @FXML
    void checkRoleSelector() {
        saveButton.setDisable(roleSelector.getValue() == null);
    }

    void setRoleParameters(String roleValue) {
        for (Node nodes : gridPane.getChildren()){
            CheckBox checkBox = (CheckBox) nodes;
            String itemId = nodes.getId();
            checkBox.setSelected(false);
            for (SettingsEntity entity : fetchAccessControlList()) {
                boolean matchesCondition = entity.getRole_name().contains(roleValue) && entity.getView_id().contains(itemId);
                if (matchesCondition) {
                    checkBox.setSelected(entity.isAllowed());
                }
            }
        }
    }

    public void saveButtonClicked(ActionEvent actionEvent) {
        AtomicInteger status = new AtomicInteger(0);
        for (Node item : gridPane.getChildren()) {
            CheckBox checkBox = (CheckBox) item;
            String viewId = item.getId();
            String roleValue = roleSelector.getValue().toString();
            status.addAndGet(saveConfiguration(viewId, roleValue, checkBox.isSelected()));
        }
        if (status.get() > 0) {
            new AlertDialogs("ACCESS CONTROL", "Nice! access control for the specified role updated").successAlert();
        } else {
            new AlertDialogs("PROCESS FAILED", "Access control update failed, restart the process").errorAlert();
        }
    }
}
