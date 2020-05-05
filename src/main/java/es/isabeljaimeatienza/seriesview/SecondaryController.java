/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.isabeljaimeatienza.seriesview;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author cadit
 */
public class SecondaryController implements Initializable {

    @FXML
    private TextField textFieldTitulo;
    @FXML
    private ComboBox<?> comboBoxGenero;
    @FXML
    private ComboBox<?> comboBoxIdioma;
    @FXML
    private ComboBox<?> comboBoxPais;
    @FXML
    private RadioButton radioButtonMeGusta;
    @FXML
    private RadioButton radioButtonNoMeGusta;
    @FXML
    private CheckBox checkBoxVista;
    @FXML
    private CheckBox checkBoxNoVista;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
    }
    
}
