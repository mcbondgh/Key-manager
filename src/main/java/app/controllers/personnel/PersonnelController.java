package app.controllers.personnel;

import app.conf.AlertDialogs;
import app.controllers.MainViewController;
import app.entities.PeopleEntity;
import app.methods.SpecialMethods;
import app.models.UsersModel;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PersonnelController extends UsersModel implements Initializable {
    @FXML
    private Label pageTitleLabel;
    @FXML
    private ComboBox<String> departmentSelector, titleSelector;
    @FXML
    private DatePicker issueDate, expiryDate;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField nameField, numberField, mailField, cardNumberField, filterField;
    @FXML
    private Button uploadButton, saveButton, clearButton;

    @FXML
    private TableView<PeopleEntity> peopleTable;
    @FXML
    private TableColumn<PeopleEntity, ?> nameColumn, numberColumn, cardColumn, titleColumn, actionColumn, endDateColumn;

    public static String pageTitlePlaceholder;

    TextInputDialog dialoa = new TextInputDialog();
    AlertDialogs ALERT;
    PeopleEntity PEOPLE_ENTITY_OBJ = new PeopleEntity();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setTitle(titleSelector);
        SpecialMethods.setDepartment(departmentSelector);
        loadPeopleTable();
    }

    final static String DEFAULT_PATH = "src/main/resources/app/images/student-100.png";

    /*******************************************************************************************************************
     TRUE OR FALSE STATEMENTS
     *******************************************************************************************************************/
    boolean checkFormEmptyFields() {
        return nameField.getText().isBlank() || numberField.getText().isBlank() || issueDate.getValue() == null ||
                expiryDate.getValue() == null || departmentSelector.getValue() == null ||
                titleSelector.getValue() == null || cardNumberField.getText().isBlank();
    }

    boolean checkForDuplicationCardNumbers() {
        for (PeopleEntity item : peopleTable.getItems()) {
            String value1 = item.getCardNumber().replaceAll("[-/]", "");
            String value2 = cardNumberField.getText().replaceAll("[-/]", "");
            if (value1.equalsIgnoreCase(value2)) {
                return true;
            }
        }
        return false;
    }

    boolean checkDates() {
        return expiryDate.getValue().isBefore(issueDate.getValue());
    }
    boolean checkForEmptyTable() {
        return peopleTable.getSelectionModel().isEmpty();
    }


    /*******************************************************************************************************************
     IMPLEMENTATION OF CLASS METHODS
     *******************************************************************************************************************/
    InputStream getSelectedImageStream() throws FileNotFoundException {
        InputStream stream;
        try {
            stream = new FileInputStream(imageView.getImage().getUrl());
        } catch (FileNotFoundException | NullPointerException ex) {
            stream = new FileInputStream(DEFAULT_PATH);
        }
        return stream;
    }


    File getSelectedFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("UPLOAD IMAGE");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files (*.jpg, *.png)", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(this.uploadButton.getScene().getWindow());
        return file;
    }
    void resetFields() {
        nameField.clear();
        cardNumberField.clear();
        numberField.clear();
        titleSelector.setValue(null);
        departmentSelector.setValue(null);
        expiryDate.setValue(LocalDate.now());
        issueDate.setValue(LocalDate.now());
        saveButton.setText("SAVE");
        peopleTable.getSelectionModel().clearSelection();
    }

    void loadPeopleTable() {
        peopleTable.setEditable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        cardColumn.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedExpiryDate"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        peopleTable.setItems(fetchAllPeople());
    }
    /*******************************************************************************************************************
     INPUT FIELD VALIDATIONS
     *******************************************************************************************************************/
    @FXML
    void validateNumberField(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) {
            numberField.deletePreviousChar();
        }
        if (numberField.getCharacters().length() > 10) {
            numberField.deletePreviousChar();
        }
    }

    @FXML
    void checkForEmptyFieldsOnMouseMoved() {
        saveButton.setDisable(checkFormEmptyFields());
    }

    @FXML void filterTableByKeyword() {
        try {
            peopleTable.getItems().clear();
            FilteredList<PeopleEntity> filteredList =  new FilteredList<>(fetchAllPeople(), p -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(item -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String searchKeyWord = newValue.toLowerCase();
                    if (item.getCardNumber().toLowerCase().contains(searchKeyWord)) {
                        return true;
                    } else return item.getName().toLowerCase().contains(searchKeyWord);
                });
            });
            SortedList<PeopleEntity> sortedResult = new SortedList<>(filteredList);
            sortedResult.comparatorProperty().bind(peopleTable.comparatorProperty());
            peopleTable.setItems(sortedResult);
        }catch (Exception ignored) {}
    }

    /*******************************************************************************************************************
     ACTION EVENT METHODS
     *******************************************************************************************************************/

    @FXML private void tableItemSelected() {
        if(!checkForEmptyTable()) {
            saveButton.setText("UPDATE");
            PEOPLE_ENTITY_OBJ = peopleTable.getSelectionModel().getSelectedItem();
            nameField.setText(PEOPLE_ENTITY_OBJ.getName());
            numberField.setText(PEOPLE_ENTITY_OBJ.getMobile());
            cardNumberField.setText(PEOPLE_ENTITY_OBJ.getCardNumber());
            issueDate.setValue(PEOPLE_ENTITY_OBJ.getIssueDate().toLocalDate());
            expiryDate.setValue(PEOPLE_ENTITY_OBJ.getExpiryDate().toLocalDate());
            titleSelector.setValue(PEOPLE_ENTITY_OBJ.getTitle());
            departmentSelector.setValue(PEOPLE_ENTITY_OBJ.getDepartment());
            Image image = SpecialMethods.loadImageData(PEOPLE_ENTITY_OBJ.getImageData());
            imageView.setImage(image);
        }
        //listen to item button click event...
        peopleTable.getItems().forEach(item -> {
            Button button = item.getActionButton();
            Platform.runLater(() -> {
                button.setOnAction(action -> {
                    if (button.getText().equalsIgnoreCase("Revoke")) {
                        if (updatePersonStatusById(item.getId(), false)> 0) {
                            loadPeopleTable();
                        }
                    } else {
                        if (updatePersonStatusById(item.getId(), true)> 0) {
                            loadPeopleTable();
                        }
                    }
                });
            });
        });
    }


    @FXML
    void uploadImageButtonClicked() throws IOException {
        Image image = new Image(getSelectedFile().getPath());
        imageView.setImage(image);
    }

    @FXML
    void saveButtonClicked() throws IOException {
        //Check if expiry date is not before issue date
        if (checkDates()) {
            ALERT = new AlertDialogs("INVALID DATE", "Expiry date cannot cannot be before issue date", "please provide a valid expiry date to proceed");
            ALERT.errorAlert();
            return;
        }
        //set values for the people entity
        PEOPLE_ENTITY_OBJ.setName(nameField.getText());
        PEOPLE_ENTITY_OBJ.setCardNumber(cardNumberField.getText());
        PEOPLE_ENTITY_OBJ.setMobile(numberField.getText());
        PEOPLE_ENTITY_OBJ.setExpiryDate(Date.valueOf(expiryDate.getValue()));
        PEOPLE_ENTITY_OBJ.setIssueDate(Date.valueOf(issueDate.getValue()));
        PEOPLE_ENTITY_OBJ.setTitle(titleSelector.getValue());
        PEOPLE_ENTITY_OBJ.setDepartment(departmentSelector.getValue());
        PEOPLE_ENTITY_OBJ.setImageData(getSelectedImageStream().readAllBytes());
        if (saveButton.getText().equals("SAVE")) {
            if (checkForDuplicationCardNumbers()) {
                ALERT = new AlertDialogs("DUPLICATE IDENTITY", "This Card Number is associated to another Person",
                        "please provide a unique Card Number");
                ALERT.errorAlert();
                return;
            }
            ALERT = new AlertDialogs("SAVE DATA", "Do you wish to add this person to your list of people?",
                    "please confirm to add else CANCEL to abort");
            if (ALERT.confirmationAlert()) {
                if (savePerson(PEOPLE_ENTITY_OBJ) > 0) {
                    resetFields();
                    loadPeopleTable();
                }
            }
        } else {
            ALERT = new AlertDialogs("UPDATE DATA", "Do you wish to update information about this person?",
                    "please confirm to add else CANCEL to abort");
            if (ALERT.confirmationAlert()) {
                int modifiedBy = fetchUserIdByName(MainViewController.loggedInUsername);
                int recordId = peopleTable.getSelectionModel().getSelectedItem().getId();
                PEOPLE_ENTITY_OBJ.setModifiedBy(modifiedBy);
                PEOPLE_ENTITY_OBJ.setId(recordId);
                if (updatePerson(PEOPLE_ENTITY_OBJ)> 0) {
                    resetFields();
                    loadPeopleTable();
                }
            }
        }


    }

    @FXML
    void clearButtonClicked() {
        resetFields();
    }

}//END OF CLASS
