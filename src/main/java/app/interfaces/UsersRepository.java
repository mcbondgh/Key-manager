package app.interfaces;


import app.entities.UsersEntity;
import javafx.collections.ObservableList;

public interface UsersRepository {

    ObservableList<UsersEntity> getAllUsers();
    int getUserIdByName(String name);
    String getNameById(int id);
}
