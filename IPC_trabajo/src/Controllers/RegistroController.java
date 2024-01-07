package Controllers;

import Model.Model;
import Model.Utils;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

public class RegistroController implements Initializable {
    public Button boton_login;
    public Button boton_contacto;
    public ImageView perfil;
    @FXML
    private Button boton_aceptar;
    @FXML
    private TextField campo_nombre;
    @FXML
    private TextField campo_correo;
    @FXML
    private TextField campo_usuario;
    @FXML
    private PasswordField campo_contraseña;
    @FXML
    private PasswordField campo_rep_contraseña;
    
    //propiedades para controlar si el valor de los campos es válido
    private BooleanProperty validCorreo = new SimpleBooleanProperty();
    private BooleanProperty validUsuario = new SimpleBooleanProperty();
    private BooleanProperty validContraseña = new SimpleBooleanProperty();
    private BooleanProperty equalContraseñas = new SimpleBooleanProperty();
    private BooleanProperty ValidacionesExitosas = new SimpleBooleanProperty(false);
    @FXML
    private Label errorlbl_correo;
    @FXML
    private Label errorlbl_usuario;
    @FXML
    private Label errorlbl_contraseña;
    @FXML
    private Label errorlbl_rep_contraseña;
    private Button imagen;
    @FXML
    private Button avatar;
    
    private Image image = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_login.setOnAction(actionEvent -> login());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
        avatar.setOnAction(e -> selectedAvatar());

        //inicializo las boolean properties
        validCorreo.setValue(Boolean.FALSE);
        validUsuario.setValue(Boolean.FALSE);
        validContraseña.setValue(Boolean.FALSE);   
        equalContraseñas.setValue(Boolean.FALSE);

        boton_aceptar.setDisable(true);

        bindValidacionCampoUsuario();
        bindValidacionCorreo();
        bindValidacionCampoContraseña();
        bindValidacionConfirmarContraseña();
    }
    public void login(){
        Stage stage = (Stage) boton_login.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaLogin();

    }
    //Funciones para comprobar si los campos son correctos

    private void bindValidacionCorreo() {
        campo_correo.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Utils.checkEmail(newValue)) {
                validCorreo.set(false);
                errorlbl_correo.visibleProperty().set(true);
            } else {
                validCorreo.set(true);
                errorlbl_correo.visibleProperty().set(false);
            }
        });
        validCorreo.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAceptar());
    }
    /*
    Comprobar el usuario
    1º El usuario no puede ser null
    2º El usuario no puede contener espacios
    3ª El usuario no puede existir ya
    */
    private void bindValidacionCampoUsuario() {
        campo_usuario.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue == null) {
                validUsuario.set(false);
                errorlbl_usuario.setText("No puede ser nulo");
                errorlbl_usuario.visibleProperty().set(true);
            } else if (newValue.contains(" ")) {
                validUsuario.set(false);
                errorlbl_usuario.setText("No puede contener espacios");
                errorlbl_usuario.visibleProperty().set(true);
            } else {
                try {
                    if (Acount.getInstance().existsLogin(newValue)) {
                        validUsuario.set(false);
                        errorlbl_usuario.setText("El usuario ya existe");
                        errorlbl_usuario.visibleProperty().set(true);
                    } else {
                        validUsuario.set(true);
                        errorlbl_usuario.visibleProperty().set(false);
                    }
                } catch (AcountDAOException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        validUsuario.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAceptar());
    }

    private void bindValidacionCampoContraseña() {
        campo_contraseña.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!Utils.checkPassword(newValue)) {
                validContraseña.set(false);
                errorlbl_contraseña.visibleProperty().set(true);
            } else {
                validContraseña.set(true);
                errorlbl_contraseña.visibleProperty().set(false);
            }
        });
        validContraseña.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAceptar());
    }

    private void bindValidacionConfirmarContraseña() {
        campo_rep_contraseña.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.equals(campo_contraseña.textProperty().getValueSafe())) {
                equalContraseñas.set(false);
                campo_rep_contraseña.setStyle("-fx-background-color: #FCE5E0");
                campo_contraseña.setStyle("-fx-background-color: #FCE5E0");
                errorlbl_rep_contraseña.visibleProperty().set(true);
            } else {
                equalContraseñas.set(true);
                errorlbl_rep_contraseña.visibleProperty().set(false);
                campo_contraseña.setStyle("");
                campo_rep_contraseña.setStyle("");
            }
        });
        equalContraseñas.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAceptar());
    }

    private void actualizarEstadoBotonAceptar() {
        boton_aceptar.setDisable(!(validUsuario.get() && validCorreo.get() && validContraseña.get() && equalContraseñas.get()));
    }

    private Image fotoPerfil = new Image("Resources/icons/perfil.png");

    private void selectedAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagen de perfil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Mostrar el diálogo para seleccionar el archivo
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Actualizar la imagen en el ImageView con la nueva imagen
            fotoPerfil = new Image(selectedFile.toURI().toString());
            perfil.setImage(fotoPerfil);
        }
    }

    private Image getSelectedAvatar(){
        return fotoPerfil;
    }

    @FXML
    private void Acceder(ActionEvent event) throws AcountDAOException, IOException {

        String partes_nombre[] = campo_nombre.getText().split(" ");
        String nombre = partes_nombre[0];
        StringBuilder apellidos = new StringBuilder();
        for (int i = 1; i < partes_nombre.length; i++) {
            apellidos.append(partes_nombre[i]);
        }
        String correo = campo_correo.getText();
        String usuario = campo_usuario.getText();
        String contraseña = campo_contraseña.getText();

        Image avatar = getSelectedAvatar();

        LocalDate fecha_registro = LocalDate.now();

        boolean res = Acount.getInstance().registerUser(nombre, apellidos.toString(), correo, usuario, contraseña, avatar, fecha_registro);
        if (res) {
            Alert alert = new Alert(AlertType.INFORMATION, "Usuario creado correctamente");
            alert.setHeaderText(null);
            alert.setOnHidden(actionEvent -> crear());
            alert.showAndWait();
        }
    }


    public void crear(){
        Stage stage = (Stage) boton_login.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaLogin();
    }


}
