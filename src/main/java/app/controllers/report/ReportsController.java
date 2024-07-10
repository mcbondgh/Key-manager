package app.controllers.report;

import app.conf.DAO;
import app.entities.UsersEntity;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private Label pageTitleLabel;
    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private TextField filterField;
    @FXML
    private TableView<UsersEntity> logsTable;
    @FXML
    private TableColumn<UsersEntity, ?> idColumn, dateColumn, nameColumn;
    @FXML
    private Button loadButton;

    public static String pageTitlePlaceholder;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
    }

    private void populateTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("statement"));
        DAO DAO_OBJ = new DAO();
        ObservableList<UsersEntity> data = DAO_OBJ.fetchActivityLogs(
                startDate.getValue().atStartOfDay().toLocalDate(), endDate.getValue().atStartOfDay().toLocalDate()
        );
        logsTable.setItems(data);
    }

    public void loadTable(ActionEvent actionEvent) {
        populateTable();
    }
}//end of class...
