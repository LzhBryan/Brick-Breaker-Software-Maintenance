package BrickDestroy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BrickBreaker extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader Menu = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/HomeMenu-view.fxml"));
        Scene scene = new Scene(Menu.load(), 626, 313);
        scene.getStylesheets().add(getClass().getResource("/CSS/HomeMenu.css").toExternalForm());
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
