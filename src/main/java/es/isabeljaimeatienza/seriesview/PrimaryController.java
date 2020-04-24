package es.isabeljaimeatienza.seriesview;

import es.isabeljaimeatienza.seriesview.entities.Serie;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PrimaryController implements Initializable {


    private EntityManager entityManager;
    @FXML
    private TableView<Serie> tableSeries;

    @FXML
    private TableColumn<Serie, String> columnNombreSerie;
    @FXML
    private TableColumn<Serie, String> columnGeneroSerie;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        

    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombreSerie.setCellValueFactory(new PropertyValueFactory<>("título"));
        columnGeneroSerie.setCellValueFactory(new PropertyValueFactory<>("genero"));
        
    }
    public void cargarTodasSeries() {
    Query querySerieFindAll = entityManager.createNamedQuery("Serie.findAll");
    List<Serie> listSerie = querySerieFindAll.getResultList();
    tableSeries.setItems(FXCollections.observableArrayList(listSerie));
}  
}
