package app.entities;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class KeyTransactionsEntity {
//    id, key_code, block_alias, is_booked, card_number, title, purpose, transaction_date, username
    private int id, userId;
    private  String cardNumber, keyCode, blockAlias, purpose, username;
    private Timestamp transactionDate, returnedDate;
    private boolean booked;
    private Button actionButton = new Button("Collect Key");
    private Label dateLabel = new Label();
    String issuedTo, issuedBy, returnedBy;



    public KeyTransactionsEntity(String cardNumber, String keyCode, String blockAlias, String purpose, int userId) {
        this.cardNumber = cardNumber;
        this.keyCode = keyCode;
        this.blockAlias = blockAlias;
        this.purpose = purpose;
        this.userId = userId;
    }
    public KeyTransactionsEntity(int id, String keyCode, String cardNumber, String blockAlias, String purpose, String returnedBy, Timestamp transactionDate, Timestamp returnedDate, String issuedBy) {
        this.id = id;
        this.keyCode = keyCode;
        this.cardNumber = cardNumber;
        this.blockAlias = blockAlias;
        this.purpose = purpose;
        this.returnedBy = returnedBy;
        this.issuedBy = issuedBy;
        this.transactionDate = transactionDate;
        this.returnedDate = returnedDate;
    }
    public KeyTransactionsEntity(int id, String keyCode, String cardNumber, String blockAlias, String purpose, boolean booked, Timestamp transactionDate) {
        this.id = id;
        this.keyCode = keyCode;
        this.cardNumber = cardNumber;
        this.blockAlias = blockAlias;
        this.purpose = purpose;
        this.booked = booked;
        this.transactionDate = transactionDate;
        formatNodes();
    }

    private void formatNodes() {
        if (booked) {
            actionButton.setDisable(false);
            actionButton.setStyle("-fx-font-size: 12px; -fx-background-color:#11ae1b; -fx-text-fill:#ffff; -fx-text-align:center; -fx-font-family:roboto");
        } else  actionButton.setDisable(true);

        dateLabel.setText(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(transactionDate.toLocalDateTime()));
        LocalDate obtainedDate = transactionDate.toLocalDateTime().toLocalDate();
        if (obtainedDate.isBefore(LocalDate.now())) {
            dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill:#ff0000; -fx-text-align:center; -fx-font-family:roboto");
        } else {
            dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill:#11ae1b; -fx-text-align:center; -fx-font-family:roboto");
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getKeyCode() {
        return keyCode;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Button getActionButton() {
        return actionButton;
    }

    public void setActionButton(Button actionButton) {
        this.actionButton = actionButton;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(Label dateLabel) {
        this.dateLabel = dateLabel;
    }

    public Timestamp getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Timestamp returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getReturnedBy() {
        return returnedBy;
    }

    public void setReturnedBy(String returnedBy) {
        this.returnedBy = returnedBy;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getBlockAlias() {
        return blockAlias;
    }

    public void setBlockAlias(String blockAlias) {
        this.blockAlias = blockAlias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }



    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}
