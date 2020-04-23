package es.isabeljaimeatienza.seriesview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JavaFX App
 */
public class App extends Application {

    private EntityManagerFactory emf;
    private EntityManager em;
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        emf = Persistence.createEntityManagerFactory("CatalogoSeriesPU");
        em = emf.createEntityManager();            

        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        em.close();
        emf.close();
      
        try {
            DriverManager.getConnection("jdbc:derby:CatalogoSeries;shutdown=true");
        } catch (SQLException ex) {
        }
    }
    

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    SeriesViewController seriesViewController = (SeriesViewController) fxmlLoader.getController(); 

    public static void main(String[] args) {
        launch();
    }

}
