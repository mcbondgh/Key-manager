package app.controllers.manage_users;

import app.conf.AlertDialogs;
import app.conf.DataEncryption;
import app.controllers.MainViewController;
import app.entities.UsersEntity;
import app.methods.SpecialMethods;
import app.models.UsersModel;
import app.roles.Roles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ManageUsersController extends UsersModel implements Initializable {

    @FXML
    private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Roles> roleSelector;
    @FXML
    private PasswordField passwordField, confirmField;
    @FXML
    private Button saveButton, clearButton;
    @FXML
    private TableView<UsersEntity> usersTable;
    @FXML
    private TableColumn<UsersEntity, Integer> idColumn;
    @FXML
    private TableColumn<UsersEntity, String> nameColumn, roleColumn;
    @FXML
    private TableColumn<UsersEntity, Button> actionColumn;
    @FXML
    private Label indicator;

    AlertDialogs ALERT_DIALOGS;
    UsersEntity USERS_ENTITY = new UsersEntity();
    String ACTIVE_USERNAME = MainViewController.loggedInUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setRoles(roleSelector);
        populateTable();
    }

    @FXML
    VBox formLayout, tableLayout;
    @FXML
    private HBox vBox;

    void toggleViews() {
        if (vBox.getChildren().contains(formLayout)) {
            vBox.getChildren().remove(formLayout);
        } else {
            vBox.getChildren().add(0, formLayout);
        }
    }


    /*********************************************************************************************************
     * TRUE OR FALSE STATEMENTS
     ********************************************************************************************************/
    boolean passwordMatch() {
        return !Objects.equals(passwordField.getText(), confirmField.getText());
    }

    private boolean isFieldEmpty() {
        return nameField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                confirmField.getText().isEmpty() ||
                roleSelector.getValue() == null;
    }

    private int userExists() {
        AtomicInteger exists = new AtomicInteger(0);
        try {
            USERS_ENTITY.getAllUsers().forEach(item -> {
                if (nameField.getText().toLowerCase().matches(item.getUsername().toLowerCase())) {
                    exists.incrementAndGet();
                }
            });
        } catch (NullPointerException ignore) {
        }
        return exists.get();
    }

    private boolean isUserSelectedFromTable() {
        return usersTable.getSelectionModel().isEmpty();
    }

    /*********************************************************************************************************
     * IMPLEMENTATION OF OTHER METHODS.
     ********************************************************************************************************/
    void populateTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role_name"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        usersTable.setItems(USERS_ENTITY.getAllUsers());
    }

    void resetFields() {
        nameField.clear();
        passwordField.clear();
        confirmField.clear();
        roleSelector.setValue(null);
    }

    void refreshTable() {
        resetFields();
        usersTable.getItems().clear();
        populateTable();
    }

    /*********************************************************************************************************
     * ACTION EVENT METHODS.
     ********************************************************************************************************/
    @FXML
    void checkFields() {
        saveButton.setDisable(isFieldEmpty() || passwordMatch());

    }

    @FXML
    void validatePasswordFields() {
        indicator.setVisible(passwordMatch());
    }


    @FXML
    void tableItemSelected() {
        //CHECK IF SELECTION IS NOT EMPTY.
        if (!isUserSelectedFromTable()) {
            saveButton.setText("UPDATE");
            String name = usersTable.getSelectionModel().getSelectedItem().getUsername();
            String role = usersTable.getSelectionModel().getSelectedItem().getRole_name();
            String pwd = DataEncryption.DefaultPassword;
            nameField.setText(name);
            roleSelector.setValue(Roles.valueOf(role));
            passwordField.setText(pwd);
            confirmField.setText(pwd);
        }
        //THIS METHOD SHALL BE INVOKED WHEN THE ACTION BUTTON OF A PARTICULAR USER IS CLICKED.
        usersTable.getItems().forEach(item -> {
            item.getActionButton().setOnAction(event -> {
                Button button = (Button) event.getSource();
                byte statusValue = (byte) (button.getText().equals("Disable") ? 0 : 1);
                updateUserStatus(item.getUser_id(), statusValue);
                refreshTable();
            });
        });
    }

    @FXML
    void saveUser() {
        //get form data and set them to the users entity for database process.
        int userId = USERS_ENTITY.getUserIdByName(ACTIVE_USERNAME) == 0 ? 1 : USERS_ENTITY.getUserIdByName(ACTIVE_USERNAME);
        String hashText = DataEncryption.encryptText(passwordField.getText());
        USERS_ENTITY.setUsername(nameField.getText());
        USERS_ENTITY.setRole_name(roleSelector.getValue().name());
        USERS_ENTITY.setPassword(hashText);
        USERS_ENTITY.setModified_by(userId);

        if (saveButton.getText().equals("SAVE")) {
            //check if user exists
            if (userExists() == 0) {
                ALERT_DIALOGS = new AlertDialogs("SAVE USER", "Do you want to proceed to add this user", "please confirm to SAVE else CANCEL to abort");
                if (ALERT_DIALOGS.confirmationAlert()) {

                    int responseStatus = saveUser(USERS_ENTITY);
                    //check if data was successfully saved.
                    if (responseStatus > 0) {
                        ALERT_DIALOGS = new AlertDialogs("USER SAVED", "NICE! user successfully added to your list of users.");
                        ALERT_DIALOGS.successAlert();
                        refreshTable();
                    } else {
                        ALERT_DIALOGS = new AlertDialogs("OPERATION FAILED", "Attempt to save this user failed, might be caused by network failure",
                                "contact system admin if failure persists");
                        ALERT_DIALOGS.errorAlert();
                    }
                }
            } else {
                ALERT_DIALOGS = new AlertDialogs("USER EXISTS", "This username already exists, please enter a different username");
                ALERT_DIALOGS.errorAlert();
            }
        } else {
            //  update user details here...
            ALERT_DIALOGS = new AlertDialogs("UPDATE USER", "Do you want to proceed to update user's data?", "please confirm to update else CANCEL to abort");
            if (ALERT_DIALOGS.confirmationAlert()) {
                int id = usersTable.getSelectionModel().getSelectedItem().getUser_id();
                int ACTIVE_USER_ID = USERS_ENTITY.getUserIdByName(ACTIVE_USERNAME.toLowerCase());
                USERS_ENTITY.setUser_id(id);
                USERS_ENTITY.setModified_by(ACTIVE_USER_ID);
                int responseStatus = updateUserDetails(USERS_ENTITY);
                if (responseStatus > 0) {
                    refreshTable();
                    saveButton.setText("SAVE");
                }
            }
        }
    }

    @FXML
    void clearFields() {
        refreshTable();
        saveButton.setText("SAVE");
    }


}//end of class...
