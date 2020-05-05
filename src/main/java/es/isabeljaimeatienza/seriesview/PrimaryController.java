package es.isabeljaimeatienza.seriesview;

import es.isabeljaimeatienza.seriesview.entities.Serie;
import java.net.URL;
import java.util.List;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
    private ComboBox<?> comboBoxGenero;
    @FXML
    private Button buttonSuprimir;
    @FXML
    private Button buttonNuevo;
    @FXML
    private Button buttonEditar;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        

    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombreSerie.setCellValueFactory(new PropertyValueFactory<>("título"));
        columnGeneroSerie.setCellValueFactory(new PropertyValueFactory<>("genero"));
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
//                     
                        textFieldCapitulos.setText(String.valueOf(serieSeleccionada.getCapitulos()));
                        // ATENCIÓN!!! HACER AQUÍ LISTA DESPEGABLE
//                        textFieldGenero.setText(serieSeleccionada.getGenero());
                    } else {
                        textFieldTitulo.setText("");
                        textFieldCapitulos.setText ("");
                    }
                });
    }
    public void cargarTodasSeries() {
    Query querySerieFindAll = entityManager.createNamedQuery("Serie.findAll");
    List<Serie> listSerie = querySerieFindAll.getResultList();
    tableSeries.setItems(FXCollections.observableArrayList(listSerie));
}  

    @FXML
    private void onActionGuardar(ActionEvent event) {
       // A partir de que el usuario seleccione una serie poder guardar modificaciones,
       // es decir, debe haber una serie seleccionada para poder modificarla
                if (serieSeleccionada != null) {
            serieSeleccionada.setTítulo(textFieldTitulo.getText());
            serieSeleccionada.setCapitulos(Integer.valueOf(textFieldCapitulos.getText()));
//            serieSeleccionada.setGenero(textFieldGenero.get());
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
    }

    @FXML
    private void onActionNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionEditar(ActionEvent event) {
    }
}
