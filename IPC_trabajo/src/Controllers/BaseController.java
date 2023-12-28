package Controllers;

import Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
