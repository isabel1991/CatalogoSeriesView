package es.isabeljaimeatienza.seriesview;

import static es.isabeljaimeatienza.seriesview.SecondaryController.CARPETA_FOTOS;
import es.isabeljaimeatienza.seriesview.entities.Genero;
import es.isabeljaimeatienza.seriesview.entities.Serie;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PrimaryController implements Initializable {

    private EntityManager entityManager;
    private Serie serieSeleccionada;

    @FXML
    private TableView<Serie> tableSeries;

    @FXML
    private TableColumn<Serie, String> columnNombreSerie;
    @FXML
    private TableColumn<Serie, String> columnGeneroSerie;
    @FXML
    private TextField textFieldTitulo;
    private TextField textFieldGenero;
    @FXML
    private GridPane textFieldNombre;
    @FXML
    private TableColumn<Serie, Integer> columnCapitulos;
    @FXML
    private TextField textFieldCapitulos;
    @FXML
    private ComboBox<Genero> comboBoxGenero;
    @FXML
    private Button buttonSuprimir;
    @FXML
    private Button buttonNuevo;
    @FXML
    private Button buttonEditar;
    @FXML

    private AnchorPane rootSeriesView;
    @FXML
    private ImageView imageViewFoto;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxGenero.setConverter(new StringConverter<Genero>() {
            @Override
            public String toString(Genero genero) {
                if (genero == null) {
                    return null;
                } else {
                    return genero.getNombre();
                }
            }

            @Override
            public Genero fromString(String Nombre) {
                return null;
            }
        }
        );

        columnNombreSerie.setCellValueFactory(new PropertyValueFactory<>("título"));
        columnCapitulos.setCellValueFactory(new PropertyValueFactory<>("capitulos"));
        columnGeneroSerie.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getGenero() != null) {
                        property.setValue(cellData.getValue().getGenero().getNombre());
                    }
                    return property;
                });
        tableSeries.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            serieSeleccionada = newValue;
            if (serieSeleccionada != null) {
                textFieldTitulo.setText(serieSeleccionada.getTítulo());
                textFieldCapitulos.setText(String.valueOf(serieSeleccionada.getCapitulos()));
                if (serieSeleccionada.getGenero() != null) {
                    comboBoxGenero.setValue(serieSeleccionada.getGenero());
                }

                if (serieSeleccionada.getFoto() != null) {
                    String imageFileName = serieSeleccionada.getFoto();
                    File file = new File(CARPETA_FOTOS + "/" + imageFileName);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageViewFoto.setImage(image);
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION, "No se encuentra la imagen");
                        alert.showAndWait();
                    }
                }
                // ATENCIÓN!!! HACER AQUÍ LISTA DESPEGABLE
//                        textFieldGenero.setText(serieSeleccionada.getGenero());
            } else {
                textFieldTitulo.setText("");
                textFieldCapitulos.setText("");
            }
        });
    }

    public void cargarTodasSeries() {
        Query querySerieFindAll = entityManager.createNamedQuery("Serie.findAll");
        List<Serie> listSerie = querySerieFindAll.getResultList();
        Query queryGeneroFindAll = entityManager.createNamedQuery("Genero.findAll");
        List listGenero = queryGeneroFindAll.getResultList();
        comboBoxGenero.setItems(FXCollections.observableList(listGenero));
        tableSeries.setItems(FXCollections.observableArrayList(listSerie));

    }

    @FXML
    private void onActionGuardar(ActionEvent event) {
        // A partir de que el usuario seleccione una serie poder guardar modificaciones,
        // es decir, debe haber una serie seleccionada para poder modificarla
        if (serieSeleccionada != null) {
            serieSeleccionada.setTítulo(textFieldTitulo.getText());
            serieSeleccionada.setCapitulos(Integer.valueOf(textFieldCapitulos.getText()));
            serieSeleccionada.setGenero(comboBoxGenero.getValue());
            entityManager.getTransaction().begin();
            entityManager.merge(serieSeleccionada);
            entityManager.getTransaction().commit();

            // Selecciona determinado objeto de la tabla, el que modificaremos
            int numFilaSeleccionada = tableSeries.getSelectionModel().getSelectedIndex();
            tableSeries.getItems().set(numFilaSeleccionada, serieSeleccionada);
            TablePosition pos = new TablePosition(tableSeries, numFilaSeleccionada, null);
            tableSeries.getFocusModel().focus(pos);
            tableSeries.requestFocus();
        }
    }

    @FXML
    private void onActionSuprimir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText(serieSeleccionada.getTítulo() + " "
                + serieSeleccionada.getCapitulos());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // Acciones a realizar si el usuario acepta
            entityManager.getTransaction().begin();
            serieSeleccionada = entityManager.merge(serieSeleccionada);
            entityManager.remove(serieSeleccionada);
            entityManager.getTransaction().commit();

            tableSeries.getItems().remove(serieSeleccionada);

            tableSeries.getFocusModel().focus(null);
            tableSeries.requestFocus();
        } else {
            // Acciones a realizar si el usuario cancela
            int numFilaSeleccionada = tableSeries.getSelectionModel().getSelectedIndex();
            tableSeries.getItems().set(numFilaSeleccionada, serieSeleccionada);
            TablePosition pos = new TablePosition(tableSeries, numFilaSeleccionada, null);
            tableSeries.getFocusModel().focus(pos);
            tableSeries.requestFocus();
        }
    }

    @FXML
    private void onActionNuevo(ActionEvent event) {
        try {
            // Cargar la vista de detalle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Secondary.fxml"));
            Parent rootDetalleView = fxmlLoader.load();
            SecondaryController secondaryController = (SecondaryController) fxmlLoader.getController();
            secondaryController.setRootSeriesView(rootSeriesView);
            secondaryController.setTableViewPrevio(tableSeries);
            serieSeleccionada = new Serie();
            secondaryController.setSerie(entityManager, serieSeleccionada, true);
            secondaryController.mostrarDatos();
            // Ocultar la vista de la lista
            rootSeriesView.setVisible(false);

            // Añadir la vista de detalle al StackPane principal para que se muestre
            StackPane rootMain = (StackPane) rootSeriesView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
        int numFilaSeleccionada = tableSeries.getSelectionModel().getSelectedIndex();
        if (numFilaSeleccionada != -1) {
            try {
                // Cargar la vista de detalle
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Secondary.fxml"));
                Parent rootDetalleView = fxmlLoader.load();
                SecondaryController secondaryController = (SecondaryController) fxmlLoader.getController();
                secondaryController.setRootSeriesView(rootSeriesView);
                secondaryController.setTableViewPrevio(tableSeries);
                secondaryController.setSerie(entityManager, serieSeleccionada, false);
                secondaryController.mostrarDatos();
                // Ocultar la vista de la lista
                rootSeriesView.setVisible(false);

                // Añadir la vista de detalle al StackPane principal para que se muestre
                StackPane rootMain = (StackPane) rootSeriesView.getScene().getRoot();
                rootMain.getChildren().add(rootDetalleView);
            } catch (IOException e) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, e);

            }

        } else {
            Alert alert = new Alert(AlertType.WARNING, "No ha saleccionado una serie");
            alert.showAndWait();
        }
    }
}
