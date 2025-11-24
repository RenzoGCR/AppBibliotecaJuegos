package controllers;

import common.DataProvider;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.UserDAO;
import utils.AuthService;
import utils.JavaFXUtil;

import javax.sql.DataSource;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private Label lblUsuario;
    @javafx.fxml.FXML
    private Button btnAceptar;
    @javafx.fxml.FXML
    private Label lblTitulo;
    @javafx.fxml.FXML
    private Label lblContraseña;
    @javafx.fxml.FXML
    private PasswordField tfContraseña;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private TextField tfUsuario;
    private UserDAO userDao;
    private AuthService authService;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        userDao = new UserDAO(ds);
        authService = new AuthService(userDao);
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        System.exit(0);
    }

    @javafx.fxml.FXML
    public void aceptar(ActionEvent actionEvent) {
        if(authService.login(tfUsuario.getText(),tfContraseña.getText()).isPresent()){
            JavaFXUtil.showModal(Alert.AlertType.INFORMATION,"Bienvenido",null,"Bienvenido a la aplicación");
            MainController mainController = JavaFXUtil.setScene("/appbibliotecajuegos/main-view.fxml");
        }else{
            JavaFXUtil.showModal(Alert.AlertType.ERROR,"Error",null,"Error al iniciar sesion");
        }
    }
}
