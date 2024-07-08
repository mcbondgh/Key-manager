package app.controllers.keys;

import app.conf.AlertDialogs;
import app.entities.KeyTransactionsEntity;
import app.models.UsersModel;
import io.github.palexdev.materialfx.collections.ObservableStack;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionHistoryController extends UsersModel implements Initializable {

    @FXML
    private DatePicker startDate, endDate;
    @FXML
    private TextField searchField;
    @FXML
    Button loadButton, exportBtn;
    @FXML
    private TableView<KeyTransactionsEntity> transactionTable;
    @FXML
    private TableColumn<KeyTransactionsEntity, ?> IdColumn, codeColumn, blockColumn, purpose, issuedTo, issuedDate, returnedDate, returnedBy, issuedBy;

    AlertDialogs ALERT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actionEventMethodsImplementation();
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());

    }

    void populateTable() {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("keyCode"));
        blockColumn.setCellValueFactory(new PropertyValueFactory<>("blockAlias"));
        issuedTo.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        issuedDate.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        returnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        returnedBy.setCellValueFactory(new PropertyValueFactory<>("returnedBy"));
        issuedBy.setCellValueFactory(new PropertyValueFactory<>("issuedBy"));
        purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        transactionTable.setItems(fetchTransactionHistory(startDate.getValue(), endDate.getValue()));
    }

    void actionEventMethodsImplementation() {
        searchField.setOnKeyReleased(event -> {
            try {
                transactionTable.getItems().clear();
                FilteredList<KeyTransactionsEntity> filteredList =  new FilteredList<>(fetchTransactionHistory(startDate.getValue(), endDate.getValue()), p -> true);
                searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    filteredList.setPredicate(item -> {
                        if (newValue.isEmpty() || newValue.isBlank()) {
                            return true;
                        }
                        String searchKeyWord = newValue.toLowerCase();
                        if (item.getKeyCode().toLowerCase().contains(searchKeyWord)) {
                            return true;
                        } else return item.getCardNumber().toLowerCase().contains(searchKeyWord);
                    });
                });
                SortedList<KeyTransactionsEntity> sortedResult = new SortedList<>(filteredList);
                sortedResult.comparatorProperty().bind(transactionTable.comparatorProperty());
                transactionTable.setItems(sortedResult);
            }catch (Exception ignored) {}
        });

        loadButton.setOnAction(event -> {
                populateTable();
        });

        exportBtn.setOnAction(event -> {

        });

    }//end of method.



}//end of class..
