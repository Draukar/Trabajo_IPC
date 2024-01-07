package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.Utils;
import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Acount;
import model.AcountDAOException;
import model.User;

public class PerfilController implements Initializable{

    public Button boton_guardar;
    public Button boton_cancelar;
    public Button boton_imagen;
    @FXML
    private TextField campo_nombre;
    @FXML
    private TextField campo_correo;
    @FXML
    private Label errorlbl_correo;
    @FXML
    private TextField campo_usuario;
    @FXML
    private PasswordField campo_contraseña;
    @FXML
    private Label errorlbl_contraseña;
    @FXML
    private PasswordField campo_rep_contraseña;
    @FXML
    private Label errorlbl_rep_contraseña;
    @FXML
    private Label fecha_registro;
    @FXML
    private ImageView avatar;
    
    
    
    //propiedades para controlar si el valor de los campos es válido
    private BooleanProperty validCorreo = new SimpleBooleanProperty();
    private BooleanProperty validUsuario = new SimpleBooleanProperty();
    private BooleanProperty validContraseña = new SimpleBooleanProperty();
    private BooleanProperty equalContraseñas = new SimpleBooleanProperty();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_guardar.setDisable(true);
        boton_imagen.setOnAction(actionEvent -> selectedAvatar());
        boton_cancelar.setOnAction(actionEvent -> Model.getInstance().getMainView().getMenuSeleccionado().set("Inicio"));
        //Inicializar todos los campos con los datos del usuario
        String nombre = null;
        String apellidos = null;
        String correo = null;
        String usuario = null;
        Image imagen = null;
        LocalDate registro = null;
        
        try {
            nombre = Acount.getInstance().getLoggedUser().getName();
            apellidos = Acount.getInstance().getLoggedUser().getSurname();
            correo = Acount.getInstance().getLoggedUser().getEmail();
            usuario = Acount.getInstance().getLoggedUser().getNickName();
            imagen = Acount.getInstance().getLoggedUser().getImage();
            registro = Acount.getInstance().getLoggedUser().getRegisterDate();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        campo_nombre.setText(nombre + apellidos);
        campo_correo.setText(correo);
        campo_usuario.setText(usuario);
        avatar.setImage(imagen);
        fecha_registro.setText(registro.toString());
        
        //Inicializo las focusedProperties
        validCorreo.setValue(Boolean.TRUE);
        validUsuario.setValue(Boolean.TRUE);
        validContraseña.setValue(Boolean.FALSE);
        equalContraseñas.setValue(Boolean.FALSE);

        bindValidacionCorreo();
        bindValidacionCampoContraseña();
        bindValidacionConfirmarContraseña();

    }

    @FXML
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
        boolean condicionesCumplidas = validCorreo.get() && validContraseña.get() && equalContraseñas.get();
        boton_guardar.setDisable(!condicionesCumplidas);
        if (condicionesCumplidas) {
            boton_guardar.setOnAction(this::actualizarPerfil);
        } else {
            boton_guardar.setOnAction(null); // Desasignar el evento si las condiciones no se cumplen
        }
    }

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
            Image fotoPerfil = new Image(selectedFile.toURI().toString());
            avatar.setImage(fotoPerfil);
        }
    }

    private void actualizarPerfil(ActionEvent event) {
        try {
            Acount acount = Acount.getInstance();
            User user = acount.getLoggedUser();
            user.setName(campo_nombre.getText());
            user.setEmail(campo_correo.getText());
            user.setPassword(campo_contraseña.getText());
            user.setImage(avatar.getImage());

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Se han guardado los datos correctamente");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
