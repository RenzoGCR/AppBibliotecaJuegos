package controllers;

import common.DataProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import user.User;
import user.UserDAO;
import utils.AuthService;
import utils.JavaFXUtil;
import videojuego.Videogame;
import videojuego.VideogameDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @javafx.fxml.FXML
    private TableColumn<Videogame,String> colId;
    private Stage stage;
    private VideogameDAO gameDAO;
    private UserDAO userDAO;
    private AuthService authService;
    private User currentUser;
    private ObservableList<User> datos = FXCollections.observableArrayList();
    private ImageView cover;
    private AudioClip clapAudio;
    private AudioClip fondo = new AudioClip(getClass().getResource("/audio/chiptune-loop.wav").toString());
    private MediaPlayer player;

    @javafx.fxml.FXML
    private Button btnSalir;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private MenuItem menuItemSalir;
    @javafx.fxml.FXML
    private MenuItem menuItemImportar;
    @javafx.fxml.FXML
    private TableColumn <Videogame,String> colYear;
    @javafx.fxml.FXML
    private TableColumn <Videogame,String> colTitle;
    @javafx.fxml.FXML
    private TableColumn <Videogame,String> colPlatform;
    @javafx.fxml.FXML
    private TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataSource ds = DataProvider.getDataSource();
        gameDAO = new VideogameDAO(ds);
        userDAO = new UserDAO(ds);
        authService = new AuthService(userDAO);
        currentUser = authService.getCurrentUser().get();

        authService.getCurrentUser().ifPresent(System.out::println);
        colId.setCellValueFactory( ( row)->{
            return new SimpleStringProperty(row.getValue().getId().toString());
        });
        colTitle.setCellValueFactory( ( TableColumn.CellDataFeatures<Videogame, String>row)->{
            return new SimpleStringProperty(row.getValue().getTitle());
        });
        colPlatform.setCellValueFactory((row)->{
            return new SimpleStringProperty(row.getValue().getPlatform());
        });
        colYear.setCellValueFactory((row)->{
            return new SimpleStringProperty(row.getValue().getYear().toString());
        });

        // **NUEVO CÓDIGO:** Manejar el doble click en la tabla
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Llama al nuevo método para manejar la visualización de detalles
                viewDetails();
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        table.getItems().clear();
        table.getItems().addAll( gameDAO.findAllByUserId( currentUser.getId()) );
    }

    // **NUEVO MÉTODO**
    private void viewDetails() {
        // 1. Obtener el objeto Videogame seleccionado de la tabla.
        Videogame selectedGame = (Videogame) table.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            try {
                // 2. Crear el FXMLLoader y especificar la ruta del FXML
                // Usamos getClass().getResource() ya que MainController está en el mismo paquete que el FXML.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appbibliotecajuegos/details-view.fxml"));

                // 3. Cargar el FXML. Esto construye la jerarquía de nodos.
                Parent root = loader.load();

                // 4. OBTENER EL CONTROLADOR: El loader ahora tiene el controlador.
                DetailsController detailsController = loader.getController();

                // 5. PASAR EL OBJETO Videogame
                // Esto llama al método setVideogame() que debe existir en DetailsController
                detailsController.setVideogame(selectedGame);

                // 6. Crear y mostrar el nuevo Stage (ventana de detalles)
                Stage detailsStage = new Stage();
                detailsStage.setTitle("Detalles del Videojuego");

                // Configurar como modal para bloquear la ventana principal hasta que se cierren los detalles
                detailsStage.initModality(Modality.APPLICATION_MODAL);

                // Usamos 'stage' (que está declarado en MainController) como dueño, si está inicializado.
                // Si 'stage' no se inicializa en MainController, puedes omitir initOwner.
                // detailsStage.initOwner(this.stage);

                detailsStage.setScene(new Scene(root));
                detailsStage.showAndWait();

            } catch (IOException e) {
                System.err.println("Error al cargar la vista de detalles:");
                e.printStackTrace();
            }
        }
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        authService.logout();
        JavaFXUtil.setScene("/appbibliotecajuegos/login-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        JavaFXUtil.setScene("/appbibliotecajuegos/newGameForm-view.fxml");
        refreshTable();
    }


}
