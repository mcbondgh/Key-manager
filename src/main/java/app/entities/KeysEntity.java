package app.entities;

import app.models.KeyModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;

public class KeysEntity extends KeyModel {
    //key_id, key_code, key_count, room_id, block_id, key_status, notes, is_booked, date_modified, modified_by

    private int key_id, key_count, block_id, room_id;
    private boolean booked, key_status;
    private String key_code, block_alias, room_number, notes;
    private int modified_by;
    private Timestamp date_modified;
    private final Button actionButton = new Button("Action");
    private final Label statusText = new Label();

    public KeysEntity(){}
    public KeysEntity(int key_id, String key_code, int key_count, String block_alias, boolean key_status, String notes, boolean is_booked, Timestamp date_modified, int modified_by, String room_number){
        this.key_id = key_id;
        this.key_code = key_code;
        this.key_count = key_count;
        this.block_alias= block_alias;
        this.key_status = key_status;
        this.notes = notes;
        this.booked = is_booked;
        this.date_modified = date_modified;
        this.modified_by = modified_by;
        this.room_number = room_number;
        formatNodes();
    }

    private void formatNodes() {
        if (key_status) {
            statusText.setText("Active");
            statusText.setStyle("-fx-font-family:roboto; -fx-text-fill:green; -fx-font-weight:bold");
        } else {
            statusText.setText("Inactive");
            statusText.setStyle("-fx-font-family:roboto; -fx-text-fill:#ff0000;-fx-font-weight:bold");
        }
//        actionButton.setStyle("-fx-background-color:orange; -fx-text-fill:#fff; padding:auto 20px;");
    }

    public int getBlockIdByAlias(String blockName) {
        for (BlocksAndRoomsEntity item : fetchAllBlocks()) {
            if (item.getBlock_alias().equalsIgnoreCase(blockName)){
                return item.getBlock_id();
            }
        }
        return 0;
    }

    public int getBlock_id() {
        return block_id;
    }

    public void setBlock_id(int block_id) {
        this.block_id = block_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoomIdByName(String roomName) {
        for (BlocksAndRoomsEntity item : fetchAllRooms())
        {
            if (item.getRoom_number().equalsIgnoreCase(roomName))
            {
                return item.getRoom_id();
            }
        }
        return 0;
    }

    public int getKey_id() {
        return key_id;
    }

    public void setKey_id(int key_id) {
        this.key_id = key_id;
    }

    public int getKey_count() {
        return key_count;
    }

    public void setKey_count(int key_count) {
        this.key_count = key_count;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public boolean isKey_status() {
        return key_status;
    }

    public void setKey_status(boolean key_status) {
        this.key_status = key_status;
    }

    public String getKey_code() {
        return key_code;
    }

    public void setKey_code(String key_code) {
        this.key_code = key_code;
    }

    public String getBlock_alias() {
        return block_alias;
    }

    public void setBlock_alias(String block_alias) {
        this.block_alias = block_alias;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getModified_by() {
        return modified_by;
    }

    public void setModified_by(int modified_by) {
        this.modified_by = modified_by;
    }

    public Timestamp getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Timestamp date_modified) {
        this.date_modified = date_modified;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public Label getStatusText() {
        return statusText;
    }
}//end of class...
