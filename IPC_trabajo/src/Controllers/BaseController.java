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
        boton_iniciar.setOnAction(actionEvent -> login());
        boton_reg.setOnAction(actionEvent -> registro());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
    }

    public void login(){
        Stage stage = (Stage) boton_iniciar.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaLogin();

    }

    public void registro(){
        Stage stage = (Stage) boton_reg.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaRegistro();

    }
}
