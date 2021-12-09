package BrickDestroy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class trial extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader Menu = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/EndingScreen-view.fxml"));
        Scene scene = new Scene(Menu.load());
        scene.getStylesheets().add(getClass().getResource("/CSS/EndingScreen.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
}
