package app.entities;

import app.conf.DAO;
import javafx.scene.control.Button;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class PeopleEntity extends DAO{
    //record_id, fullname, mobile_number, email, card_number, date_issued, expiry_date, image, title, date_modified, modified_by

    private int id, modifiedBy;
    private String name, mobile, email, cardNumber, title, department;
    private Date issueDate, expiryDate;
    private byte[] imageData;
    private Timestamp dateModified;
    private Boolean isAllowed;
    private Button actionButton = new Button("Allow Access");
    private String formattedIssueDate;
    private String formattedExpiryDate;
    public PeopleEntity(){

    }

    public PeopleEntity(String name,  String title, String department, Date expiryDate, byte[] imageData, Boolean isAllowed) {
        this.name = name;
        this.title = title;
        this.department = department;
        this.expiryDate = expiryDate;
        this.imageData = imageData;
        this.isAllowed = isAllowed;
    }

    public PeopleEntity(int id, String name, String mobile, String cardNumber, String title, String department, Date issueDate, Date expiryDate, boolean allowed, byte[] imageData) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.cardNumber = cardNumber;
        this.title = title;
        this.department = department;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.imageData = imageData;
        isAllowed = allowed;
        formatButton();
        formattedIssueDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(issueDate.toLocalDate());
        formattedExpiryDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).format(expiryDate.toLocalDate());
    }

    private void formatButton() {
        if (isAllowed) {
            actionButton.setText("Revoke");
            actionButton.setStyle("-fx-background-color:green;-fx-font-size:10px;  -fx-font-family:roboto; -fx-text-fill:#fff; -fx-width:100px; -fx-font-weight:bold");
        } else {
            actionButton.setStyle("-fx-background-color:red; -fx-font-size:10px; -fx-font-family:roboto; -fx-text-fill:#fff; -fx-width:100px; -fx-font-weight:bold");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }

    public String getFormattedIssueDate() {
        return formattedIssueDate;
    }

    public void setFormattedIssueDate(String formattedIssueDate) {
        this.formattedIssueDate = formattedIssueDate;
    }

    public String getFormattedExpiryDate() {
        return formattedExpiryDate;
    }

    public void setFormattedExpiryDate(String formattedExpiryDate) {
        this.formattedExpiryDate = formattedExpiryDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}// end of class...
