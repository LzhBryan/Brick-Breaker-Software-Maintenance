package BrickDestroy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GraphicsMain extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader Menu = new FXMLLoader(GraphicsMain.class.getResource("/BrickDestroy/FXML/HomeMenu-view.fxml"));
        Scene scene = new Scene(Menu.load(), 450, 300);
        primaryStage.setTitle("Brick Destroy");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

}
