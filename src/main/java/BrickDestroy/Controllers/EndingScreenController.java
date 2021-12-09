package BrickDestroy.Controllers;

import BrickDestroy.TextAnimator;
import BrickDestroy.TextOutput;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class EndingScreenController implements Initializable {

    TextAnimator textAnimator;

    @FXML
    private Text text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextOutput textOutput = textOut -> Platform.runLater(() -> text.setText(textOut));
        textAnimator = new TextAnimator("CONGRATULATIONS GAME HAS ENDED!",
                100, textOutput);
    }

    @FXML
    void start() {
        Thread thread = new Thread(textAnimator);
        thread.start();
    }
}
