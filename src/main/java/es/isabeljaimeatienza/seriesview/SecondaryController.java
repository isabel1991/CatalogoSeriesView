/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.isabeljaimeatienza.seriesview;

import es.isabeljaimeatienza.seriesview.entities.Genero;
import es.isabeljaimeatienza.seriesview.entities.Idioma;
import es.isabeljaimeatienza.seriesview.entities.Nacionalidad;
import es.isabeljaimeatienza.seriesview.entities.Serie;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author cadit
 */
public class SecondaryController implements Initializable {

    private Pane rootSeriesView;
    private TableView tableViewPrevio;
    private Serie serie;
    private Genero genero;
    private EntityManager entityManager;
    private boolean nuevaSerie;
    public static final char BUENA = 'B';
    public static final char MALA = 'M';
    public static final String CARPETA_FOTOS = "Fotos";

    @FXML
    private TextField textFieldTitulo;
    @FXML
    private ComboBox<Genero> comboBoxGenero;
    @FXML
    private ComboBox<Idioma> comboBoxIdioma;
    @FXML
    private ComboBox<Nacionalidad> comboBoxPais;
    @FXML
    private RadioButton radioButtonMeGusta;
    @FXML
    private RadioButton radioButtonNoMeGusta;
    @FXML
    private CheckBox checkBoxVista;
    @FXML
    private DatePicker datePickerFecha;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldCapitulos;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private Button buttonExaminar;
    @FXML
    private AnchorPane rootSerieDetalle;
    @FXML
    private ToggleGroup valora;

    /**
     * Initializes the controller class.
     */
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

        comboBoxIdioma.setConverter(new StringConverter<Idioma>() {
            @Override
            public String toString(Idioma idioma) {
                if (idioma == null) {
                    return null;
                } else {
                    return idioma.getNombre();
                }
            }

            @Override
            public Idioma fromString(String Nombre) {
                return null;
            }
        }
        );

        comboBoxPais.setConverter(new StringConverter<Nacionalidad>() {
            @Override
            public String toString(Nacionalidad nacionalidad) {
                if (nacionalidad == null) {
                    return null;
                } else {
                    return nacionalidad.getPais();
                }
            }

            @Override
            public Nacionalidad fromString(String Nombre) {
                return null;
            }
        }
        );
    }

    public void setRootSeriesView(Pane rootSeriesView) {
        this.rootSeriesView = rootSeriesView;
    }

    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }

    public void setSerie(EntityManager entityManager, Serie serie, boolean nuevaSerie) {
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        if (!nuevaSerie) {
            this.serie = entityManager.find(Serie.class, serie.getId());
        } else {
            this.serie = serie;
        }
        this.nuevaSerie = nuevaSerie;

    }

    public void mostrarDatos() {
        textFieldTitulo.setText(serie.getTítulo());
        
        if (serie.getCapitulos()
                != null) {
            textFieldCapitulos.setText(String.valueOf(serie.getCapitulos()));
        }
        Query queryGeneroFindAll = entityManager.createNamedQuery("Genero.findAll");
        List listGenero = queryGeneroFindAll.getResultList();
        comboBoxGenero.setItems(FXCollections.observableList(listGenero));

        Query queryIdiomaFindAll = entityManager.createNamedQuery("Idioma.findAll");
        List listIdioma = queryIdiomaFindAll.getResultList();
        comboBoxIdioma.setItems(FXCollections.observableList(listIdioma));

        Query queryNacionalidadFindAll = entityManager.createNamedQuery("Nacionalidad.findAll");
        List listNacionalidad = queryNacionalidadFindAll.getResultList();
        comboBoxPais.setItems(FXCollections.observableList(listNacionalidad));

        if (serie.getGenero()
                != null) {
            comboBoxGenero.setValue(serie.getGenero());
        }

        if (serie.getIdioma()
                != null) {
            comboBoxIdioma.setValue(serie.getIdioma());
        }

        if (serie.getPais()
                != null) {
            comboBoxPais.setValue(serie.getPais());
        }

        if (serie.getFechaEstreno()
                != null) {
            Date date = serie.getFechaEstreno();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerFecha.setValue(localDate); // El datepicker guardará la fecha de estreno que tenga la serie
        }

        if (serie.getPrecio()
                != null) {
            textFieldPrecio.setText(String.valueOf(serie.getPrecio()));
        }

        if (serie.getVisionada()
                != null) {
            checkBoxVista.setSelected(serie.getVisionada());
        }

        if (serie.getFoto()
                != null) {
            String imageFileName = serie.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }

        if (serie.getValoracion() != null) {
            switch (serie.getValoracion()) {
                case BUENA:
                    radioButtonMeGusta.setSelected(true);
                    break;
                case MALA:
                    radioButtonNoMeGusta.setSelected(true);
                    break;

            }
        }

        // Falta implementar el código para el resto de controles
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        StackPane rootMain = (StackPane) rootSerieDetalle.getScene().getRoot();
        rootMain.getChildren().remove(rootSerieDetalle);

        serie.setTítulo(textFieldTitulo.getText());
        if (textFieldCapitulos.getText().isEmpty()== false) {
            try {
                serie.setCapitulos(Integer.valueOf(textFieldCapitulos.getText()));
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.WARNING, "El número de capítulos no es correcto");
                alert.showAndWait();

            }

        }
        serie.setGenero(comboBoxGenero.getValue());
        serie.setIdioma(comboBoxIdioma.getValue());
        serie.setPais(comboBoxPais.getValue());
        if (textFieldPrecio.getText().isEmpty()== false) {
            try {
                serie.setPrecio(BigDecimal.valueOf(Double.valueOf(textFieldPrecio.getText())));
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.WARNING, "El precio no es correcto");
                alert.showAndWait();

            }

        }

        serie.setVisionada(checkBoxVista.isSelected());

        if (radioButtonMeGusta.isSelected()) {
            serie.setValoracion(BUENA);
        } else if (radioButtonNoMeGusta.isSelected()) {
            serie.setValoracion(MALA);

        }

        if (datePickerFecha.getValue() != null) {
            LocalDate localDate = datePickerFecha.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            serie.setFechaEstreno(date);
        } else {
            serie.setFechaEstreno(null);
        }

        if (nuevaSerie) {
            entityManager.persist(serie);
        } else {
            entityManager.merge(serie);
        }
        entityManager.getTransaction().commit();

        int numFilaSeleccionada;
        if (nuevaSerie) {
            tableViewPrevio.getItems().add(serie);
            numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        } else {
            numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada, serie);
        }
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
        rootSeriesView.setVisible(true);
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane) rootSerieDetalle.getScene().getRoot();
        rootMain.getChildren().remove(rootSerieDetalle);
        entityManager.getTransaction().rollback();

        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
        rootSeriesView.setVisible(true);
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootSerieDetalle.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                serie.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }
}
