package Controllers;

import Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroController implements Initializable {
    public Button boton_login;
    public Button boton_contacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_login.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaLogin());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
    }
}
