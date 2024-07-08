package app.controllers.keys;

import app.conf.AlertDialogs;
import app.controllers.MainViewController;
import app.entities.BlocksAndRoomsEntity;
import app.entities.KeyTransactionsEntity;
import app.entities.PeopleEntity;
import app.entities.UsersEntity;
import app.methods.SpecialMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class IssueKeyController extends UsersEntity implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton, issueKeyButton, clearButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel, departmentLabel, titleLabel, statusText;
    @FXML
    private ComboBox<String> blockSelector, keySelector;
    @FXML
    private VBox issueKeyBox;
    @FXML
    private TextArea purposeField;


    final String ERROR_COLOR = "-fx-background-color:#ffe9e8; -fx-text-fill: red";
    final String SUCCESS_COLOR = "-fx-background-color:#caffe1; -fx-text-fill:green";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpecialMethods.setBlockAlias(blockSelector);
    }

    boolean isSearchFieldEmpty() {
        return searchField.getText().isBlank() || searchField.getText().length() < 10;
    }

    int aliasId() {
        for (BlocksAndRoomsEntity item : fetchAllBlocks()) {
            if (item.getBlock_alias().equalsIgnoreCase(blockSelector.getValue())) {
                return item.getBlock_id();
            }
        }
        return 0;
    }

    //check and verify users card Number;
    private void verifyCardNumber(String userInput) {
        for (PeopleEntity item : fetchAllPeople()) {
            String cardNumber = item.getCardNumber().replace("[-/]", "").toLowerCase();
            if (Objects.equals(cardNumber, userInput)) {
                String date = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(item.getExpiryDate().toLocalDate());
                boolean isDateInvalidValid = item.getExpiryDate().toLocalDate().isBefore(LocalDate.now());
                nameLabel.setText(item.getName());
                titleLabel.setText(item.getTitle());
                departmentLabel.setText(item.getDepartment());
                imageView.setImage(SpecialMethods.loadImageData(item.getImageData()));

                if (isDateInvalidValid) {
                    statusText.setText("ACCESS DENIED, CARD HAS EXPIRED (" + date + ")");
                    statusText.setStyle(ERROR_COLOR);
                } else if (!item.getAllowed()) {
                    statusText.setText("PRIVILEGE TO ACCESS KEY HAS BEEN REVOKED");
                    statusText.setStyle(ERROR_COLOR);
                } else {
                    statusText.setText("REQUEST ACCESS GRANTED, CARD NUMBER IS VALID");
                    statusText.setStyle(SUCCESS_COLOR);
                    issueKeyBox.setDisable(false);
                }
                break;
            } else {
                statusText.setText("INVALID CARD NUMBER, PERSON NOT FOUND");
                statusText.setStyle(ERROR_COLOR);
                imageView.setImage(null);
                nameLabel.setText("-");
                titleLabel.setText("-");
                departmentLabel.setText("-");
            }
        }
    }

    @FXML
    void validateSearchField() {
        searchButton.setDisable(isSearchFieldEmpty());
    }

    @FXML
    void checkFormEmptySelectors() {
        try {
            issueKeyButton.setDisable(blockSelector.getValue() == null || keySelector.getValue() == null);
        } catch (NullPointerException ignore) {
        }

    }

    @FXML
    void blockItemSelected() {
        String alias = blockSelector.getValue();
        keySelector.getItems().clear();
        fetchAllKeys().forEach(item -> {
            boolean matchesCondition = Objects.equals(item.getBlock_alias(), alias) && !item.isBooked() && item.isKey_status();
            if (matchesCondition) {
                keySelector.getItems().add(item.getKey_code());
            }
        });
    }

    public void clearFieldsButtonClicked() {
        searchField.clear();
        nameLabel.setText("-");
        titleLabel.setText("-");
        departmentLabel.setText("-");
        blockSelector.setValue(null);
        keySelector.setValue(null);
        String src = "src/main/resources/app/images/student-100.png";
        imageView.setImage(new Image(src));
    }

    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    @FXML
    void searchButtonClicked() {
        String userInput = searchField.getText().toLowerCase().replace("[-/]", "");
        verifyCardNumber(userInput);
        statusText.setVisible(true);
    }

    @FXML
    void issueKeyButtonClicked() {
        boolean result = new AlertDialogs("ISSUE KEY", "Do you wish to issue selected key to this person?", "please confirm to ISSUE KEY else CANCEL to abort").confirmationAlert();
        if (result) {
            int userId = fetchUserIdByName(MainViewController.loggedInUsername);
            String code = keySelector.getValue();
            String cardNumber = searchField.getText();
            String purpose = purposeField.getText();
            String alias = blockSelector.getValue();

            KeyTransactionsEntity entity = new KeyTransactionsEntity(cardNumber, code, alias, purpose, userId);
            int responseId = issueKey(entity);
            responseId += updateBookedKeyStatus(true, code, aliasId());
            if (responseId == 2) {
                new AlertDialogs("ISSUE SUCCESS", "Nice! you have successfully issued out this key").successAlert();
                clearFieldsButtonClicked();
            }
        }
    }


}//end of class...
