package es.isabeljaimeatienza.seriesview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JavaFX App
 */
public class App extends Application {

    private EntityManagerFactory emf;
    private static EntityManager em;
    private static Scene scene;
    private static PrimaryController primaryController;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Series Netflix"); // Cambia el nombre del título de la ventana

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:fotos/netflix.png")); // Para cambiar el icono de la ventana

        emf = Persistence.createEntityManagerFactory("CatalogoSeriesPU");
        em = emf.createEntityManager();

        StackPane rootMain = new StackPane();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Pane rootSeriesView = fxmlLoader.load();

        rootMain.getChildren()
                .add(rootSeriesView);

// Carga del EntityManager, etc ...
        Scene scene = new Scene(rootMain, 800, 500);
        PrimaryController seriesViewController = (PrimaryController) fxmlLoader.getController();

        seriesViewController.setEntityManager(em);

        seriesViewController.cargarTodasSeries();

        primaryStage.setScene(scene);

        primaryStage.show();

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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class
                .getResource(fxml + ".fxml"));
        primaryController = (PrimaryController) fxmlLoader.getController();
        System.out.println("PrimaryControler:" + primaryController);
        // Después de obtener el objeto del controlador y del EntityManager:
        primaryController.setEntityManager(em);
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch();
    }

}
