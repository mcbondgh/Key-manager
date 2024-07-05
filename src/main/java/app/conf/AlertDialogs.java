package app.conf;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertDialogs {
    Alert ALERTS;

    private final String title;
    private final String header;
    private String content;

    public AlertDialogs(@NamedArg("title")String title, @NamedArg("header")String header, @NamedArg("content") String content) {
        this.title = title;
        this.header = header;
        this.content = content;
    }

    public AlertDialogs(@NamedArg("title")String title, @NamedArg("header")String header) {
        this.title = title;
        this.header = header;
    }

    public void successAlert() {
        ALERTS = new Alert(Alert.AlertType.INFORMATION, content );
        ALERTS.setTitle(title);
        ALERTS.setHeaderText(header);
        ALERTS.getButtonTypes().retainAll();
        ALERTS.getButtonTypes().add(ButtonType.CLOSE);
        ALERTS.showAndWait();
    }

    public void errorAlert() {
        ALERTS = new Alert(Alert.AlertType.ERROR, content );
        ALERTS.setTitle(title);
        ALERTS.setHeaderText(header);
        ALERTS.showAndWait();
    }

    public boolean confirmationAlert() {
        ALERTS = new Alert(Alert.AlertType.CONFIRMATION, content);
        ALERTS.setTitle(title);
        ALERTS.setHeaderText(header);
        ALERTS.getButtonTypes().add(ButtonType.YES);
        ALERTS.getButtonTypes().remove(ButtonType.OK);
        return ALERTS.showAndWait().get().equals(ButtonType.YES);
    }


}
