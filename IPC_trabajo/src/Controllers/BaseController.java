<<<<<<< Updated upstream
package Controllers;

import Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;



public class BaseController implements Initializable {
    public Button boton_iniciar;
    public Button boton_reg;
    public Button boton_contacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_iniciar.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaLogin());
        boton_reg.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaRegistro());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
    }
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author elena
 */
public class BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
>>>>>>> Stashed changes
