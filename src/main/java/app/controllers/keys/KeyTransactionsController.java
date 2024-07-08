package app.controllers.keys;

import app.entities.BlocksAndRoomsEntity;
import app.entities.KeyTransactionsEntity;
import app.entities.KeysEntity;
import app.models.UsersModel;
import app.stages.AppStages;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyTransactionsController extends UsersModel implements Initializable {


    public static String pageTitlePlaceholder;

    @FXML private Label pageTitleLabel;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem issueKeyButton, viewKeyTransactions;
    @FXML private TextField searchField;
    @FXML private TableView<KeyTransactionsEntity> transactionsTable;
    @FXML private TableColumn<KeyTransactionsEntity, ?> idColumn, keyCode, cardNumber, alias, purpose, issuedDate, action;

    public KeyTransactionsController() throws IOException {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        populateTable();
    }


    //USE THIS TO PREVENT THE STAGE FROM HAVING MULTIPLE INSTANCES ON BUTTON CLICK
    Stage issueKeyStage = AppStages.issueKeyStage();
    public void issueKeyButtonClicked(ActionEvent actionEvent) throws IOException {
       if (!issueKeyStage.isShowing()) {
           issueKeyStage.showAndWait();
       }
    }

    Stage transactionStage = AppStages.keyTransactionsHistoryStage();
    public void viewKeyTransactionsButtonClicked(ActionEvent actionEvent) {
        try {
            transactionStage.show();
        }catch (IllegalStateException ignore) {
            transactionStage.showAndWait();
        }
    }

    int aliasId(String alias) {
        for (BlocksAndRoomsEntity item : fetchAllBlocks()) {
            if (item.getBlock_alias().equalsIgnoreCase(alias)) {
                return item.getBlock_id();
            }
        }
        return 0;
    }

    Dialog<String> dialogPane(int itemId, String alias, String code) {
        Dialog<String> dialogPane = new Dialog<>();
        dialogPane.setHeaderText("RETURN KEY");
        Label header = new Label("RETURN KEY");
        header.setStyle("-fx-font-size:15px; -fx-font-family:roboto; -fx-background-color:gray; -fx-font-weight:bold; -fx-text-align:center; -fx-padding:10;");

        Label label1 = new Label("Transaction Id");
        label1.setStyle("-fx-font-size:13px;");
        TextField field1 = new TextField(String.valueOf(itemId));
        field1.setDisable(true);
        field1.setStyle("-fx-font-weight:bold; -fx-font-size:14px");

        VBox vBox = new VBox();
        VBox innerBox1 = new VBox(label1, field1);
        innerBox1.setSpacing(5);

        Label label2 = new Label("Returned By");
        label2.setStyle("-fx-font-family:roboto; -fx-font-size:13px;");
        TextField field2 = new TextField();

        field2.setStyle("-fx-font-weight:bold; -fx-font-size:14px");
        VBox innerBox2 = new VBox(label2, field2);

        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setSpacing(10);

        ButtonType OkButton = ButtonType.OK;
        ButtonType close = ButtonType.CLOSE;


        field2.setOnKeyTyped(keyCode ->
                dialogPane.getDialogPane().lookupButton(OkButton).setDisable(field2.getText().isEmpty() || field2.getText().length() < 5));

        dialogPane.getDialogPane().getButtonTypes().add(OkButton);
        dialogPane.getDialogPane().getButtonTypes().add(close);
        dialogPane.getDialogPane().lookupButton(OkButton).setDisable(true);
        vBox.getChildren().addAll(innerBox1, innerBox2);
        dialogPane.setHeaderText(header.getText());
        dialogPane.getDialogPane().setContent(vBox);

        Button saveButton = (Button) dialogPane.getDialogPane().lookupButton(OkButton);
        saveButton.setOnAction(action-> {
            action.consume();
            updateBookedKeyStatus(false, code, aliasId(alias));
            updateReturnedDate(itemId, field2.getText());
        });

        return dialogPane;
    }


    /******************************************************************************************************************

     *******************************************************************************************************************/
    void populateTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        keyCode.setCellValueFactory(new PropertyValueFactory<>("keyCode"));
        alias.setCellValueFactory(new PropertyValueFactory<>("blockAlias"));
        cardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        purpose.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        issuedDate.setCellValueFactory(new PropertyValueFactory<>("dateLabel"));
        action.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        transactionsTable.setItems(fetchKeyTransactions());
    }

    @FXML void filterTransactionsTable() {
        try {
            transactionsTable.getItems().clear();
            FilteredList<KeyTransactionsEntity> filteredList =  new FilteredList<>(fetchKeyTransactions(), p -> true);
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
            sortedResult.comparatorProperty().bind(transactionsTable.comparatorProperty());
            transactionsTable.setItems(sortedResult);
        }catch (Exception ignored) {}
    }

    @FXML void refreshTableOnMouseFocus() {
        populateTable();
        transactionsTable.getItems().forEach(item -> {
            Button button = item.getActionButton();
            button.setOnAction(event -> {
                int itemId = item.getId();
                String code = item.getKeyCode();
                String alias = item.getBlockAlias();
                dialogPane(itemId, alias, code).showAndWait();
            });
        });
    }

}//end of class...
