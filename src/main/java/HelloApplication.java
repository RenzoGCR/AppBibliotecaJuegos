package org.example.gestorjuegosfx;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.gestorjuegosfx.utils.JavaFXUtil;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/org/example/gestorjuegosfx/login-view.fxml");
    }
}
