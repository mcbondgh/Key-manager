package app.methods;

import app.AppStarter;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SpecialMethods {

    private static final FadeTransition transition = new FadeTransition();

    public static void ChangeView(String fxmlFile, BorderPane pane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource(fxmlFile));
        transition.setNode(pane);
        transition.setFromValue(0.3);
        transition.setToValue(1);
        transition.setCycleCount(1);
//        transition.setAutoReverse(true);
        pane.setCenter(fxmlLoader.load());
        transition.play();
    }


}//end of class...
