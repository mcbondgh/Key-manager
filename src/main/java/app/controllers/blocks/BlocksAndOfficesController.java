package app.controllers.blocks;

import app.conf.AlertDialogs;
import app.controllers.MainViewController;
import app.entities.BlocksAndRoomsEntity;
import app.methods.SpecialMethods;
import app.models.BlocksAndRoomsModel;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class BlocksAndOfficesController extends BlocksAndRoomsModel implements Initializable {

    @FXML
    private Label pageTitleLabel;
    @FXML
    private TextField filterField;
    @FXML
    private TextField blockNameField, blockAliasField, roomCountField;
    @FXML
    private TextArea blockCommentField, roomCommentField;
    @FXML
    private Button saveBlockBtn, clearBlockBtn, saveRoomBtn, clearRoomBtn;
    @FXML
    private ComboBox<String> roomTypeSelector, blockSelector;
    @FXML
    private CheckBox statusCheckbox;
    @FXML
    private TextField roomNoField;

    public static String pageTitlePlaceholder;

    //BLOCKS TABLE
    @FXML
    private TableView<BlocksAndRoomsEntity> blocksTable;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, String> blockNameColumn;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, String> blockAliasColumn;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, Integer> blockCountColumn;

    //ROOMS TABLE
    @FXML
    private TableView<BlocksAndRoomsEntity> roomsTable;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, ?> roomNoColumn;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, ?> roomTypeColumn;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, ?> roomBlockColumn;
    @FXML
    private TableColumn<BlocksAndRoomsEntity, ?> statusColumn;

    String ACTIVE_USERNAME = MainViewController.loggedInUsername;

    AlertDialogs ALERTS_DIALOGS;
    BlocksAndRoomsEntity BLOCKS_AND_ROOMS_OBJ = new BlocksAndRoomsEntity();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setRoomTypes(roomTypeSelector);
        populateBlocksTable();
        populateBlockSelector();
        populateRoomsTable();
    }


    /****************************************************************************************************************
     TRUE OR FALSE STATEMENTS
     ****************************************************************************************************************/
    boolean isFieldEmpty() {
        return blockNameField.getText().isEmpty() || roomCountField.getText().isEmpty();
    }

    boolean emptyFieldsInRoomsLayout() {
        return roomNoField.getText().isEmpty() || blockSelector.getValue() == null || roomTypeSelector.getValue() == null;
    }

    int checkForDuplicateAliasNames() {
        AtomicInteger counter = new AtomicInteger();
        blocksTable.getItems().forEach(item -> {
            if (item.getBlock_alias().equalsIgnoreCase(blockAliasField.getText())) {
                counter.incrementAndGet();
            }
        });
        return counter.get();
    }

    int checkForDuplicateRoomNumbers() {
        AtomicInteger counter = new AtomicInteger(0);
        roomsTable.getItems().forEach(item -> {
            if (item.getRoom_number().equalsIgnoreCase(roomNoField.getText())) {
                counter.incrementAndGet();
            }
        });
        return counter.get();
    }

    /****************************************************************************************************************
     IMPLEMENTATION OF OTHER METHODS
     ****************************************************************************************************************/
    void clearBlocksField() {
        blockNameField.clear();
        blockCommentField.clear();
        roomCountField.clear();
        blockAliasField.clear();
    }

    void clearRoomsField() {
        roomNoField.clear();
        blockSelector.setValue(null);
        roomTypeSelector.setValue(null);
        roomCommentField.clear();
    }

    void refreshRoomsTable() {
        roomsTable.getItems().clear();
        populateRoomsTable();
        clearRoomsField();
    }

    void refreshBlocksTable() {
        blocksTable.getItems().clear();
        populateBlocksTable();
    }

    boolean isBlocksTableItemSelected() {
        return blocksTable.getSelectionModel().isEmpty();
    }

    void populateBlocksTable() {
        blockNameColumn.setCellValueFactory(new PropertyValueFactory<>("block_name"));
        blockAliasColumn.setCellValueFactory(new PropertyValueFactory<>("block_alias"));
        blockCountColumn.setCellValueFactory(new PropertyValueFactory<>("room_count"));
        blocksTable.setItems(fetchAllBlocks());
    }

    void populateRoomsTable() {
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<>("room_number"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("room_type"));
        roomBlockColumn.setCellValueFactory(new PropertyValueFactory<>("block_alias"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));
        roomsTable.setItems(fetchAllRooms());
    }

    void populateBlockSelector() {
        blockSelector.getItems().clear();
        blocksTable.getItems().forEach(
                item -> blockSelector.getItems().add(item.getBlock_alias()));
    }

    //KEY EVENTS IMPLEMENTATION
    @FXML
    void validateRoomCountField(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) {
            roomCountField.deletePreviousChar();
        }
    }
    int getBlockIdByAlias(String alias) {
        for (int i = 0; i < blocksTable.getItems().size(); i++) {
            if (blocksTable.getItems().get(i).getBlock_alias().equals(alias)) {
                return blocksTable.getItems().get(i).getBlock_id();
            }
        }
        return 0;
    }
    int getRoomId() {
        return roomsTable.getSelectionModel().getSelectedItem().getRoom_id();
    }

    public void filterRoomsTable(KeyEvent event) {
        try {
            roomsTable.getItems().clear();
            FilteredList<BlocksAndRoomsEntity> filteredList =  new FilteredList<>(fetchAllRooms(), p -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(item -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (item.getRoom_number().toLowerCase().contains(searchKeyWord)) {
                        return true;
                    } else return item.getBlock_alias().toLowerCase().contains(searchKeyWord);
                });
            });
            SortedList<BlocksAndRoomsEntity> sortedResult = new SortedList<>(filteredList);
            sortedResult.comparatorProperty().bind(roomsTable.comparatorProperty());
            roomsTable.setItems(sortedResult);
        }catch (Exception ignored) {}
    }

    /****************************************************************************************************************
     IMPLEMENTATION OF ACTION EVENT METHODS.
     ****************************************************************************************************************/
    @FXML
    void checkForEmptyFieldsInBlocksLayout() {
        saveBlockBtn.setDisable(isFieldEmpty());
    }

    @FXML
    void checkForEmptyFieldsInRoomsLayout() {
        saveRoomBtn.setDisable(emptyFieldsInRoomsLayout());
    }

    @FXML
    void blocksTableSelected() {
        if (!isBlocksTableItemSelected()) {
            saveBlockBtn.setText("UPDATE");
            BLOCKS_AND_ROOMS_OBJ = blocksTable.getSelectionModel().getSelectedItem();
            blockNameField.setText(BLOCKS_AND_ROOMS_OBJ.getBlock_name());
            blockAliasField.setText(BLOCKS_AND_ROOMS_OBJ.getBlock_alias());
            roomCountField.setText(String.valueOf(BLOCKS_AND_ROOMS_OBJ.getRoom_count()));
            blockCommentField.setText(BLOCKS_AND_ROOMS_OBJ.getNotes());
        }
    }

    @FXML
    void roomsTableSelected() {
        if (!roomsTable.getSelectionModel().isEmpty()) {
            saveRoomBtn.setText("UPDATE");
            BLOCKS_AND_ROOMS_OBJ = roomsTable.getSelectionModel().getSelectedItem();
            roomNoField.setText(BLOCKS_AND_ROOMS_OBJ.getRoom_number());
            roomTypeSelector.setValue(BLOCKS_AND_ROOMS_OBJ.getRoom_type());
            blockSelector.setValue(BLOCKS_AND_ROOMS_OBJ.getBlock_alias());
            roomCommentField.setText(BLOCKS_AND_ROOMS_OBJ.getNotes());
            statusCheckbox.setSelected(BLOCKS_AND_ROOMS_OBJ.isRoom_status());
        }
    }

    @FXML
    void saveButtonClicked() {
        BLOCKS_AND_ROOMS_OBJ.setBlock_name(blockNameField.getText());
        BLOCKS_AND_ROOMS_OBJ.setBlock_alias(blockAliasField.getText());
        BLOCKS_AND_ROOMS_OBJ.setRoom_count(Integer.parseInt(roomCountField.getText()));
        BLOCKS_AND_ROOMS_OBJ.setNotes(blockCommentField.getText());

        if (saveBlockBtn.getText().equals("SAVE BLOCK")) {
            if (checkForDuplicateAliasNames() > 0) {
                ALERTS_DIALOGS = new AlertDialogs("DUPLICATE DATA", "Block Alias already exists, please enter a unique alias");
                ALERTS_DIALOGS.errorAlert();
                return;
            }
            ALERTS_DIALOGS = new AlertDialogs("SAVE BLOCK", "Do you wish to add this block to your list of blocks?",
                    "Please confirm to SAVE else CANCEL to abort");
            if (ALERTS_DIALOGS.confirmationAlert()) {
                if (saveBlock(BLOCKS_AND_ROOMS_OBJ) > 0) {
                    refreshBlocksTable();
                    clearBlocksField();
                    populateBlockSelector();
                } else {
                    ALERTS_DIALOGS = new AlertDialogs("OPERATION FAILED", "Attempt to save this block failed, please redo");
                    ALERTS_DIALOGS.errorAlert();
                }
            }
        } else {
            //Perform item update here...
            ALERTS_DIALOGS = new AlertDialogs("UPDATE BLOCK", "Do you wish to update selected block details?");
            if (ALERTS_DIALOGS.confirmationAlert()) {
                int itemId = blocksTable.getSelectionModel().getSelectedItem().getBlock_id();
                int userId = fetchUserIdByName(ACTIVE_USERNAME);
                BLOCKS_AND_ROOMS_OBJ.setBlock_id(itemId);
                BLOCKS_AND_ROOMS_OBJ.setModified_by(userId);
                if (updateBlock(BLOCKS_AND_ROOMS_OBJ) > 0) {
                    refreshBlocksTable();
                    populateBlocksTable();
                }
            }
        }
    }

    @FXML
    void clearBlocksButtonClicked() {
        populateBlocksTable();
//        blocksTable.refresh();
        clearBlocksField();
        saveBlockBtn.setText("SAVE BLOCK");
    }

    @FXML void saveRoomButtonClicked() {
        BLOCKS_AND_ROOMS_OBJ.setRoom_number(roomNoField.getText());
        BLOCKS_AND_ROOMS_OBJ.setRoom_status(statusCheckbox.isSelected());
        BLOCKS_AND_ROOMS_OBJ.setRoom_type(roomTypeSelector.getValue());
        BLOCKS_AND_ROOMS_OBJ.setBlock_id(getBlockIdByAlias(blockSelector.getValue()));
        BLOCKS_AND_ROOMS_OBJ.setNotes(roomCommentField.getText());

        if (saveRoomBtn.getText().equals("SAVE ROOM")) {
            if(checkForDuplicateRoomNumbers()> 0) {
                ALERTS_DIALOGS = new AlertDialogs("DUPLICATE DATA", "Block Alias already exists, please enter a unique alias");
                ALERTS_DIALOGS.errorAlert();
                return;
            }
            ALERTS_DIALOGS = new AlertDialogs("SAVE ROOM", "Do you wish to add to your list of rooms?", "please confirm to SAVE else CANCEL to abort");
            if (ALERTS_DIALOGS.confirmationAlert()) {
                if (insertIntoRoomsTable(BLOCKS_AND_ROOMS_OBJ)> 0) {
                    refreshRoomsTable();
                }else {
                    ALERTS_DIALOGS = new AlertDialogs("OPERATION FAILED", "Attempt to save this room failed, please redo");
                    ALERTS_DIALOGS.errorAlert();
                }
            }
        } else {
            //Perform item update here...
            ALERTS_DIALOGS = new AlertDialogs("UPDATE ROOM", "Do you wish to update selected room details?");
            if (ALERTS_DIALOGS.confirmationAlert()) {
                BLOCKS_AND_ROOMS_OBJ.setRoom_id(getRoomId());
                if(updateRoom(BLOCKS_AND_ROOMS_OBJ)> 0) {
                    refreshRoomsTable();
                }
            }
        }
    }

    @FXML void clearRoomsButtonClicked() {
        clearRoomsField();
        populateRoomsTable();
        saveRoomBtn.setText("SAVE ROOM");
    }



}//END OF CLASS...
