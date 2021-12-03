module com.javafx.brick_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens BrickDestroy to javafx.fxml;
    exports BrickDestroy;
    exports BrickDestroy.Controllers;
    opens BrickDestroy.Controllers to javafx.fxml;
    opens BrickDestroy.Models to javafx.fxml;
    exports BrickDestroy.Models;
}