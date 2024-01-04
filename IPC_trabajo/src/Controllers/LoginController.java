package Controllers;

import Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Label usuario_lbl;
    public TextField campo_usuario;
    public Label contraseña_lbl;
    public PasswordField campo_contraseña;
    public Button boton_login;
    public Label error_lbl;
    public Button boton_registro;
    public Button boton_contacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_login.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaInicio());
        boton_registro.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaRegistro());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
    }
}
