import javafx.application.Application;
import javafx.stage.Stage;
import utils.JavaFXUtil;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        JavaFXUtil.initStage(stage);
        JavaFXUtil.setScene("/appbibliotecajuegos/login-view.fxml");
    }
}
