package app;

import app.conf.DbConfig;
import app.stages.AppStages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            AppStages.loginStage();
//            AppStages.keyTransactionsHistoryStage().showAndWait();
        }catch (Exception e) {
            e.printStackTrace();
            AppStages.databaseFailedStage();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}