package BrickDestroy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main javafx application class to run.
 */
public class BrickBreaker extends Application {
    /**
     * The main method to run the entire game.
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     * @throws Exception throw exception when the FXMl could not be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader Menu = new FXMLLoader(getClass().getResource("/BrickDestroy/FXML/HomeMenu-view.fxml"));
        Scene scene = new Scene(Menu.load(), 626, 313);
        scene.getStylesheets().add(getClass().getResource("/CSS/HomeMenu.css").toExternalForm());
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

}
