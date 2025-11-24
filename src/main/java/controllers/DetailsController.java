package controllers;

import common.DataProvider;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import user.User;
import user.UserDAO;
import utils.AuthService;
import videojuego.Videogame;
import videojuego.VideogameDAO;

import javax.sql.DataSource;
import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @javafx.fxml.FXML
    private TextField tfPlataforma;
    @javafx.fxml.FXML
    private BorderPane ventana;
    @javafx.fxml.FXML
    private TextField tfTitulo;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private TextArea taDescripcion;
    @javafx.fxml.FXML
    private ImageView ivImagen;
    @javafx.fxml.FXML
    private TextField tfId;
    @javafx.fxml.FXML
    private TextField tfIdUsuario;
    @javafx.fxml.FXML
    private TextField tfAño;
    private VideogameDAO gameDAO;
    private UserDAO userDAO;
    private AuthService authService;
    private User currentUser;
    private Stage stage;
    private Videogame videogame;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        gameDAO = new VideogameDAO(ds);
        userDAO = new UserDAO(ds);
        authService = new AuthService(userDAO);
        currentUser = authService.getCurrentUser().get();

    }
    // **NUEVO MÉTODO** para recibir el objeto del MainController
    public void setVideogame(Videogame game) {
        this.videogame = game;
        displayGameDetails(); // Llamamos a un método para rellenar los campos
    }
    // **NUEVO MÉTODO** para rellenar los controles
    private void displayGameDetails() {
        if (this.videogame != null) {
            // Rellenar los TextField/TextArea con los datos del objeto Videogame
            tfId.setText(videogame.getId().toString());
            tfTitulo.setText(videogame.getTitle());
            tfPlataforma.setText(videogame.getPlatform());
            tfAño.setText(videogame.getYear().toString());
            // Suponiendo que el objeto Videogame tiene un metodo getDescription()
            taDescripcion.setText(videogame.getDescription());


            // Si el campo tfIdUsuario tiene el ID del creador, lo puedes añadir:
            // tfIdUsuario.setText(videogame.getUserId().toString());

            // Lógica para cargar la imagen (si es necesario y posible con tu ImageView)
            // if (videogame.getImagePath() != null) {
            //     Image image = new Image(videogame.getImagePath());
            //     ivImagen.setImage(image);
            // }
        }
    }
}
