package app.controllers.personnel;

import app.entities.PeopleEntity.PeopleEntity;
import app.methods.SpecialMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class PersonnelController implements Initializable {
    @FXML
    private Label pageTitleLabel;
    @FXML private ComboBox<String> departmentSelector, titleSelector;
    @FXML private DatePicker issueDate, expiryDate;
    @FXML private ImageView imageView;
    @FXML private TextField nameField, numberField, mailField, cardNumberField;
    @FXML private Button uploadButton, saveButton, clearButton;

    @FXML private TableView<PeopleEntity> peopleTable;
    @FXML private TableColumn<PeopleEntity, ?> nameColumn, numberColumn, cardColumn, titleColumn, actionColumn, endDateColumn;

    public static String pageTitlePlaceholder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pageTitleLabel.setText(pageTitlePlaceholder);
        SpecialMethods.setTitle(titleSelector);
        SpecialMethods.setDepartment(departmentSelector);
    }

    final static String DEFAUT_PATH = "src/main/resources/app/images/student-100.png";
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
            String card;
        }
        return false;
    }

    boolean checkDates() {
        return expiryDate.getValue().isBefore(issueDate.getValue());
    }

    InputStream getSelectedImageStream() throws FileNotFoundException {
        InputStream stream;
        try {
            stream = new FileInputStream(imageView.getImage().getUrl());
        }catch (FileNotFoundException ex) {
            stream = new FileInputStream(DEFAUT_PATH);
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


    /*******************************************************************************************************************
     IMPLEMENTATION OF CLASS METHODS
     *******************************************************************************************************************/


    /*******************************************************************************************************************
     INPUT FIELD VALIDATIONS
     *******************************************************************************************************************/
    @FXML void validateNumberField(KeyEvent event) {
        if (!event.getCharacter().matches("[0-9]")) {
            numberField.deletePreviousChar();
        }
        if (numberField.getCharacters().length() > 10) {
            numberField.deletePreviousChar();
        }
    }





    /*******************************************************************************************************************
     ACTION EVENT METHODS
     *******************************************************************************************************************/

    @FXML void uploadImageButtonClicked() throws IOException {
        Image image = new Image(getSelectedFile().getPath());
        imageView.setImage(image);
    }

    @FXML void saveButtonClicked() throws IOException {
        byte[] stream = getSelectedImageStream().readAllBytes();
        System.out.println(Arrays.toString(stream));
    }

    @FXML void clearButtonClicked() {

    }

}//END OF CLASS
