package app.controllers.keys;

import app.AppStarter;
import app.conf.AlertDialogs;
import app.controllers.MainViewController;
import app.entities.BlocksAndRoomsEntity;
import app.entities.KeysEntity;
import app.entities.UsersEntity;
import app.methods.SpecialMethods;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class KeyInventoryController extends KeysEntity implements Initializable {

    @FXML private Label pageTitleLabel;
    public static String pageTitlePlaceholder;

    @FXML private TableView<KeysEntity> keysTable;
    @FXML private TableColumn<KeysEntity, ?>idColumn, codeColumn, blockColumn, keyCountColumn, statusColumn, roomColumn;

    @FXML private TextField codeField, keyCountField, filterField;
    @FXML private ComboBox<String> blockSelector, roomSelector;
    @FXML private TextArea notesField;
    @FXML private Button saveButton, clearButton;
    @FXML private CheckBox checkBox;

    @FXML private VBox roomSelectorBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setBlockAlias(blockSelector);
        populateTable();
    }



    /*******************************************************************************************************************
        TRUE OR FALSE STATEMENTS
     */
    boolean isKeyCodeEmpty() {
        return codeField.getText().isBlank();
    }
    boolean isBlockSelectorEmpty()
    {
        return blockSelector.getValue() == null;
    }
    boolean roomSelectorEmpty()
    {
        return roomSelector.getValue() == null;
    }
    boolean keyCountEmpty()
    {
        return keyCountField.getText().isBlank();
    }
    boolean checkForDuplicateKeyCodes() {
        for(KeysEntity item : keysTable.getItems()) {
            boolean matchesParams = item.getKey_code().equalsIgnoreCase(codeField.getText()) &&
                    item.getRoom_number().equalsIgnoreCase(roomSelector.getValue());
            if (matchesParams) {
                return true;
            }
        }
        return false;
    }


    /*******************************************************************************************************************
     IMPLEMENTATION OF OTHER METHODS
     */

    void populateTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("key_id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("key_code"));
        blockColumn.setCellValueFactory(new PropertyValueFactory<>("block_alias"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room_number"));
        keyCountColumn.setCellValueFactory(new PropertyValueFactory<>("key_count"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));
        keysTable.setItems(fetchAllKeys());
    }

    void resetFields() {
        keyCountField.clear();
        codeField.clear();
        blockSelector.setValue(null);
        roomSelector.setValue(null);
        notesField.clear();
        saveButton.setText("SAVE KEY");
    }

    public void filterKeysTable(KeyEvent event) {
        try {
            keysTable.getItems().clear();
            FilteredList<KeysEntity> filteredList =  new FilteredList<>(keysTable.getItems(), p -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(item -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (item.getKey_code().toLowerCase().contains(searchKeyWord)) {
                        return true;
                    } else return item.getBlock_alias().toLowerCase().contains(searchKeyWord);
                });
            });
            SortedList<KeysEntity> sortedResult = new SortedList<>(filteredList);
            sortedResult.comparatorProperty().bind(keysTable.comparatorProperty());
            keysTable.setItems(sortedResult);
        }catch (Exception ignored) {}
    }

    /*******************************************************************************************************************
     KEY AND MOUSE EVENTS
     */
    @FXML void validateCountField(KeyEvent event)
    {
        if (!event.getCharacter().matches("[0-9]"))
        {
            keyCountField.deletePreviousChar();
        }
    }
    @FXML void checkForRequiredFields()
    {
        saveButton.setDisable(isBlockSelectorEmpty() ||
                isKeyCodeEmpty() || roomSelectorEmpty()
                || keyCountEmpty());
    }

    @FXML void tableItemSelected() {
        if (!keysTable.getSelectionModel().isEmpty()){
            saveButton.setText("UPDATE");
            KeysEntity items = keysTable.getSelectionModel().getSelectedItem();
            codeField.setText(items.getKey_code());
            blockSelector.setValue(items.getBlock_alias());
            roomSelector.setValue(items.getRoom_number());
            keyCountField.setText(String.valueOf(items.getKey_count()));
            notesField.setText(items.getNotes());
            checkBox.setSelected(items.isKey_status());
        }
    }

    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION
     */
    @FXML void getBlockValueOnSelection()
    {
        SpecialMethods.setRoomNumbers(roomSelector, blockSelector.getValue());
        roomSelectorBox.setDisable(false);
    }

    @FXML void saveButtonClicked()
    {
        AlertDialogs ALERT;
        int ACTIVE_USER_ID = fetchUserIdByName(MainViewController.loggedInUsername);
        KeysEntity KEYS_ENTITY = new KeysEntity();
        KEYS_ENTITY.setKey_code(codeField.getText());
        KEYS_ENTITY.setBlock_id(getBlockIdByAlias(blockSelector.getValue()));
        KEYS_ENTITY.setRoom_id(getRoomIdByName(roomSelector.getValue()));
        KEYS_ENTITY.setKey_status(checkBox.isSelected());
        KEYS_ENTITY.setKey_count(Integer.parseInt(keyCountField.getText()));

        //CHECK IF THE USER IS REGISTERING A NEW KEY
        if(saveButton.getText().equalsIgnoreCase("SAVE KEY")) {
            if (checkForDuplicateKeyCodes()) {
                ALERT = new AlertDialogs("DUPLICATE KEYS", "Key code already exist, please enter a unique key name");
                ALERT.errorAlert();
                return;
            }
            ALERT = new AlertDialogs("SAVE KEY", "Do you wish to add this key to your list of keys?", "please confirm to add else CANCEL to abort");
            if (ALERT.confirmationAlert()) {
               if (saveKey(KEYS_ENTITY)> 0) {
                   clearButtonClicked();
               }
            }else {
                ALERT = new AlertDialogs("FAILED OPERATION", "Attempt to save key failed, please try again");
                ALERT.errorAlert();
            }
        } else {
            ALERT = new AlertDialogs("UPDATE KEY", "Do you wish to updated selected key's details? ", "please confirm to execute else CANCEL to abort");
            if (ALERT.confirmationAlert()) {
               int keyId = keysTable.getSelectionModel().getSelectedItem().getKey_id();
               KEYS_ENTITY.setModified_by(ACTIVE_USER_ID);
               KEYS_ENTITY.setKey_id(keyId);
               if (updateKey(KEYS_ENTITY) > 0) {
                   clearButtonClicked();
               }
            }


        }

    }

    @FXML void clearButtonClicked() {
        resetFields();
        populateTable();
    }





}//end of class
