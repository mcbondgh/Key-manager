package app.methods;

import app.AppStarter;
import app.conf.DAO;
import app.entities.BlocksAndRoomsEntity;
import app.roles.Roles;
import io.github.palexdev.materialfx.collections.ObservableStack;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

import javax.management.relation.Role;
import java.io.IOException;

public class SpecialMethods {

    private static final FadeTransition transition = new FadeTransition();
    static DAO DAO_OBJ = new DAO();

    public static void ChangeView(String fxmlFile, BorderPane pane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource(fxmlFile));
        transition.setNode(pane);
        transition.setFromValue(0.3);
        transition.setToValue(1);
        transition.setCycleCount(1);
//        transition.setAutoReverse(true);
        pane.setCenter(fxmlLoader.load());
        transition.play();
    }

    public static void setPositions(ComboBox<String> comboBox) {
        String items[] = {"Security", "Recruit", "Chief Security", "Supervisor" , "Administrator"};
    }

    public static void setRoles(ComboBox<Roles> comboBox) {
        ObservableList<Roles> item = new ObservableStack<>();
        item.addAll(Roles.ADMIN, Roles.SECURITY, Roles.CHIEF_SECURITY);
        comboBox.setItems(item);
    }

    public static void setRoomTypes(ComboBox<String> comboBox) {
        ObservableList<String> item = new ObservableStack<>();
        item.addAll("Lecture Hall", "Office", "Wash Room", "Library", "Lab", "Conference Room", "Auditorium", "Store Room");
        comboBox.setItems(item);
    }

    public static void setGender(ComboBox<String> comboBox) {
        ObservableList<String> item = new ObservableStack<>();
        item.addAll("Male", "Female", "Other");
        comboBox.setItems(item);
    }

    public static void setRoomNumbers(ComboBox<String> comboBox)
    {
        for (BlocksAndRoomsEntity item : DAO_OBJ.fetchAllRooms()) {
            comboBox.getItems().clear();
            comboBox.getItems().add(item.getRoom_number());
        }
    }
    public static void setRoomNumbers(ComboBox<String> comboBox, String input)
    {
        comboBox.setValue(null);
        comboBox.getItems().clear();
       DAO_OBJ.fetchRoomsByBlockAlias(input).forEach(item -> comboBox.getItems().add(item));
    }

    public static void setBlockAlias(ComboBox<String> comboBox)
    {
        for (BlocksAndRoomsEntity item : DAO_OBJ.fetchAllBlocks()) {
            comboBox.getItems().add(item.getBlock_alias());
        }
    }

}//end of class...
