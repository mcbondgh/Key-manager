package app.stages;

import app.AppStarter;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class AppStages {
    public AppStages() {
    }

    public static void mainView() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Key Manager");
        stage.setScene(scene);
        stage.getScene().getWindow().setOnCloseRequest(event -> {
            try {
                loginStage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.hide();
        });
        stage.show();
    }

    public static void databaseFailedStage() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("views/db_views/db-failed-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Key Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void loginStage() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Key Manager");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setFullScreen(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public static void passwordResetStage() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("views/password-reset.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setFullScreen(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public static Stage issueKeyStage() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("views/keys/issue-key-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ISSUE KEY");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        return stage;
    }
    public static Stage keyTransactionsHistoryStage() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("views/keys/transaction-history-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("KEY TRANSACTIONS HISTORY");
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setFullScreen(false);
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.DECORATED);
        return stage;
    }
}
