package controllers;

import common.DataProvider;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import user.User;
import user.UserDAO;
import utils.AuthService;
import utils.JavaFXUtil;
import videojuego.Videogame;
import videojuego.VideogameDAO;

import javax.sql.DataSource;
import java.net.URL;
import java.util.ResourceBundle;

public class NewGameFormController implements Initializable {
    @javafx.fxml.FXML
    private TextField tfPlataforma;
    @javafx.fxml.FXML
    private TextField tfTitulo;
    @javafx.fxml.FXML
    private TextField tfDescripcion;
    @javafx.fxml.FXML
    private TextField tfImagen;
    @javafx.fxml.FXML
    private TextField tfAño;
    private DataSource dataSource;
    private UserDAO userDAO;
    private VideogameDAO videogameDAO;
    private Stage stage;
    private AuthService authService;
    private User currentUser;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/appbibliotecajuegos/main-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        Videogame nuevoJuego = new Videogame();
        nuevoJuego.setTitle(tfTitulo.getText());
        nuevoJuego.setPlatform(tfPlataforma.getText());
        nuevoJuego.setDescription(tfDescripcion.getText());
        nuevoJuego.setImage_url(tfImagen.getText());
        try {
            nuevoJuego.setYear(Integer.parseInt(tfAño.getText()));
        } catch (NumberFormatException e) {
            // Mostrar alerta si el año no es un número válido
            JavaFXUtil.showModal(Alert.AlertType.ERROR, "Error", "Formato Inválido", "El campo Año debe ser un número.");
            return;
        }
        nuevoJuego.setUser_id(currentUser.getId());
        videogameDAO.save(nuevoJuego).
                ifPresentOrElse(videogame -> {
                    // Si el guardado fue exitoso (Optional.isPresent())
                    JavaFXUtil.showModal(Alert.AlertType.INFORMATION, "Éxito", null, "Juego guardado con éxito.");
                    // 5. Cerrar el formulario solo si el guardado fue exitoso
                    if (stage != null) {
                        stage.close();
                    }

                },
                // Si falla el guardado (Optional.isEmpty())
                () -> {
                    JavaFXUtil.showModal(Alert.AlertType.ERROR, "Error", null, "Fallo al guardar en la base de datos.");
                }
                        );

        JavaFXUtil.setScene("/appbibliotecajuegos/main-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        videogameDAO = new VideogameDAO(ds);
        userDAO = new UserDAO(ds);
        authService = new AuthService(userDAO);
        currentUser = authService.getCurrentUser().get();

        authService.getCurrentUser().ifPresent(System.out::println);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
