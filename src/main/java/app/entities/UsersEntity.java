package app.entities;

import app.interfaces.UsersRepository;
import app.models.UsersModel;
import io.github.palexdev.materialfx.collections.ObservableStack;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersEntity extends UsersModel implements UsersRepository {
    //DEFAULT PASSWORD = key123

    int user_id;
    String username, password;
    boolean is_active, is_deleted;
    String role_name;
    Timestamp date_created, date_modified;
    int modified_by;
    Button actionButton = new Button("");
    String statement;

    public UsersEntity() {

    }//end of constructor

    private void formatActionButton() {
        String activeColor = "-fx-background-color:green; -fx-text-fill:#fff; -fx-font-size:12px; font-family:roboto;";
        String inactiveColor = "-fx-background-color:#ff0000; -fx-text-fill:#fff; -fx-font-size:12px; font-family:roboto;";
//        actionButton.setStyle(is_active ? activeColor : inactiveColor);
        if (is_active) {
            actionButton.setStyle(activeColor);
            actionButton.setText("Disable");
        }else {
            actionButton.setStyle(inactiveColor);
            actionButton.setText("Enable");
        }
        actionButton.setCursor(Cursor.HAND);
    }


    public UsersEntity(int user_id, String username, String password, boolean is_active, boolean is_deleted, String role_name, Timestamp data_created, Timestamp date_modified, int modified_by) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.is_active = is_active;
        this.is_deleted = is_deleted;
        this.role_name = role_name;
        this.date_created = data_created;
        this.date_modified = date_modified;
        this.modified_by = modified_by;
        formatActionButton();
    }
    public UsersEntity(int user_id, String username, String statement) {
        this.user_id = user_id;
        this.username = username;
        this.statement = statement;
    }

    @Override
    public ObservableList<UsersEntity> getAllUsers() {
        return fetchAllUsers();
    }

    @Override
    public int getUserIdByName(String name) {
        AtomicInteger userId = new AtomicInteger();
        getAllUsers().forEach(item -> {
            userId.set(item.getUsername().toLowerCase().equals(name) ? item.getUser_id() : 0);
        });
        return userId.get();
    }

    @Override
    public String getNameById(int id) {
        return "";
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public Timestamp getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Timestamp date_modified) {
        this.date_modified = date_modified;
    }

    public int getModified_by() {
        return modified_by;
    }

    public void setModified_by(int modified_by) {
        this.modified_by = modified_by;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }
}
