/*
Alumna: Isabel Jaime Atienza
Asignatura: Programación
Curso: 19/20
Trabajo: se trata de una aplicación dónde podremos llevar un registro de las series que hayamos visto o queramos ver, con sus datos
pertinentes. 
Dicha aplicación dispone de dos pantallas, una primera donde de un modo resumido vemos todas las series en una tabla
y otra donde podemos crear una serie nueva o editar una existente de un modo más detallado. 
He trabajado con diferentes tipos de datos, entre los que encontramos: boolean, varias opciones (char), imágenes...
También se ha establecido una conexión a una base de datos ya creada. 
Además de trabajar por primera vez en este proyecto con las distintas opciones que ofrece 
Scene Builder. Aunque nos centraremos más en las líneas de código. 
Vemos como hay diferentes paquetes en los que encontraremos distintas clases, en la principal (APP) vemos que se encuentran los Primary
Controller y Secondary Controller. 
Después vemos otro paquete dentro de la carpeta Source donde tenemos las clases entidad, que será de donde cojamos los campos de las 
tablas con las que trabajaremos (¡¡¡IMPORTANTE!! Debemos dejar la base de datos (en service) desconectada para trabajar con ella). 
Es importante tambien el paquete META-INF puesto que ahí encontraremos nuestro Persistence, donde direccionaremos hacia la base de datos
con la que trabajaremos.
En Other Sources también encontramos primary.fxml y secondary.fxml donde si clicamos con doble click, abrirá una nueva ventana
con el Scene Builder, en la cual podremos trabajar con los elementos "gráficos" con los que trabajaremos, aquí también podremos 
trabajar con el formato y la disposición de botones y otros elementos. Es importante de esta parte el id en code que le asignemos a cada
elemento, ya que a través de estos, en los Controllers correspondientes podremos asignarles lo que llamaremos una "acción". 
Además, aquí introduciremos la hoja CSS para que obtengan a través de dicho Scene Builder la hoja de CSS, otra opción, que he usado
es asignarle tú las características CSS en cada elemento (eso sí, tendremos más limitaciones a la hora de editar). 
 */
package es.isabeljaimeatienza.seriesview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
        try {
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

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "La base de datos ya está siendo usada,\n"
                    + "por favor cierre la que se encuentra abierta");
            alert.showAndWait();

        }

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
