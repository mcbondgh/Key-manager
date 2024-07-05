package app.entities;

import app.interfaces.BlocksAndOffices;
import javafx.scene.control.Label;

import java.sql.Timestamp;

public class BlocksAndRoomsEntity implements BlocksAndOffices {
    private int block_id, room_count, modified_by;
    private String block_name,  block_alias, notes;
    private Timestamp date_modified;

//    room_id, room_number, room_type, room_status,
    private int room_id;
    private String room_number, room_type;
    private boolean room_status;
    private Label statusLabel = new Label("");


    public BlocksAndRoomsEntity() {
    }

    public BlocksAndRoomsEntity(int block_id, int room_count, int modified_by, String block_name, String block_alias, String notes, Timestamp date_modified) {
        this.block_id = block_id;
        this.room_count = room_count;
        this.modified_by = modified_by;
        this.block_name = block_name;
        this.block_alias = block_alias;
        this.notes = notes;
        this.date_modified = date_modified;
    }

    public BlocksAndRoomsEntity(String room_number, String room_type, boolean room_status, int room_id, String block_alias, String notes) {
        this.room_number = room_number;
        this.room_type = room_type;
        this.room_status = room_status;
        this.room_id = room_id;
        this.block_alias = block_alias;
        this.notes = notes;
        formatLabel();
    }

    void formatLabel() {
        if (room_status) {
            statusLabel.setText("Active");
            statusLabel.setStyle("-fx-text-fill:green; -fx-font-family:roboto; -fx-font-size:12px; -fx-font-weight:bold;");
        }else {
            statusLabel.setText("Inactive");
            statusLabel.setStyle("-fx-text-fill:red; -fx-font-family:roboto; -fx-font-size:12px; -fx-font-weight:bold;");
        }
    }

    public int getBlock_id() {
        return block_id;
    }

    public void setBlock_id(int block_id) {
        this.block_id = block_id;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public int getModified_by() {
        return modified_by;
    }

    public void setModified_by(int modified_by) {
        this.modified_by = modified_by;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_alias() {
        return block_alias;
    }

    public void setBlock_alias(String block_alias) {
        this.block_alias = block_alias;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Timestamp date_modified) {
        this.date_modified = date_modified;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public boolean isRoom_status() {
        return room_status;
    }

    public void setRoom_status(boolean room_status) {
        this.room_status = room_status;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
    }
}//end  of class...
