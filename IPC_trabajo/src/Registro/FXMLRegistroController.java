/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Registro;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Elena
 */
public class FXMLRegistroController implements Initializable {

    @FXML
    private Text titulo;
    @FXML
    private GridPane grid;
    @FXML
    private ImageView fotoPerf;
    @FXML
    private Text nombre_usuario;
    @FXML
    private TextField nombre_textfield;
    @FXML
    private Text nickname;
    @FXML
    private TextField nickname_textfield;
    @FXML
    private Label nickname_err;
    @FXML
    private Text correo;
    @FXML
    private TextField correo_textfield;
    @FXML
    private Label correo_err;
    @FXML
    private Text contrasena;
    @FXML
    private PasswordField contraseña_paswfield;
    @FXML
    private Label contraseña_err;
    @FXML
    private VBox confCont_paswfield;
    @FXML
    private Text confCont;
    @FXML
    private Label confContr_err;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
