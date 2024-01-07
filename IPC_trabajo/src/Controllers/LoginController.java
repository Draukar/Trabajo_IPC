package Controllers;

import Model.Model;
import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

public class LoginController implements Initializable {
    public Label usuario_lbl;
    public TextField campo_usuario;
    public Label contraseña_lbl;
    public PasswordField campo_contraseña;
    public Button boton_login;
    public Label error_lbl;
    public Button boton_registro;
    public Button boton_contacto;
    private BooleanProperty camposNoVacios = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_registro.setOnAction(actionEvent -> registro());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
        
        //boton_login deshabilitado hasta que haya texto tanto en campo_usuario como en campo_contraseña
        boton_login.setDisable(true);
        bindValidacionCamposNoVacios();
    }
    private void bindValidacionCamposNoVacios() {
        campo_usuario.textProperty().addListener((observableValue, oldValue, newValue) -> {
            actualizarBotonLogin();
        });

        campo_contraseña.textProperty().addListener((observableValue, oldValue, newValue) -> {
            actualizarBotonLogin();
        });
    }

    private void actualizarBotonLogin() {
        camposNoVacios.set(!campo_usuario.getText().isEmpty() && !campo_contraseña.getText().isEmpty());
        boton_login.setDisable(!camposNoVacios.get());
    }
    /*
        Comprobar que el usuario y la contraseña son correctos, existen
        Si existe: hacer logoin y acceder a la pantalla de los gastos
        Posibles errores: no existe el usuario
                          la contraseña no corresponde con el usuario
        En caso de error: mostrar la alerta correspondiente
                          borrar el contenido de los campos
                          situar al usuario en el campo_usuario 
        */

    public void registro(){
        Stage stage = (Stage) boton_login.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaRegistro();

    }
    
    public void inicio(){
        Stage stage = (Stage) boton_login.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaInicio();
    }


    @FXML
    private void acceder(ActionEvent event) throws AcountDAOException, IOException {
        String usuario = campo_usuario.getText();
        String password = campo_contraseña.getText();

        if (Acount.getInstance().logInUserByCredentials(usuario, password)) {
            error_lbl.visibleProperty().set(false);
            inicio();
        } else {
            error_lbl.visibleProperty().set(true);
            campo_usuario.setText("");
            campo_contraseña.setText("");
            campo_usuario.requestFocus();
        }
    }
}
