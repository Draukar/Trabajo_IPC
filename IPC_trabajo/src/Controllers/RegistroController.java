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
        avatar.setOnAction(e -> selectAvatar());
        
        //inicializo las boolean properties
        validCorreo.setValue(Boolean.FALSE);
        validUsuario.setValue(Boolean.FALSE);
        validContraseña.setValue(Boolean.FALSE);   
        equalContraseñas.setValue(Boolean.FALSE);
        
         //Listener a la propiedad focused que indica cuándo el usuario está en el TextField
        campo_correo.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                comprobarEmail();
            }
        });
        
        campo_usuario.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){try {
                //focus lost
                comprobarUsuario();
                } catch (AcountDAOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        campo_contraseña.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                comprobarContraseña();
            }
        });
        
        campo_rep_contraseña.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                comprobarIguales();
            }
        });
        
        //boton_aceptar deshabilitado hasta que haya texto en todos los campos
    }
    public void login(){
        Stage stage = (Stage) boton_login.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaLogin();

    }
    //Funciones para comprobar si los campos son correctos

    private void comprobarEmail(){
        if(!Utils.checkEmail(campo_correo.textProperty().getValueSafe())){
            validCorreo.setValue(Boolean.FALSE);
            errorlbl_correo.visibleProperty().set(true);
            campo_correo.styleProperty().setValue("-fx-background-color: #FCE5E0");
            campo_correo.requestFocus();
        }else{
            validCorreo.setValue(Boolean.TRUE);
            errorlbl_correo.visibleProperty().set(false);
            campo_correo.styleProperty().setValue("");
        }
            
    }
    /*
    Comprobar el usuario
    1º El usuario no puede ser null
    2º El usuario no puede contener espacios
    3ª El usuario no puede existir ya
    */
    private void comprobarUsuario() throws AcountDAOException, IOException{
        
        if(campo_usuario.textProperty().getValueSafe() == null){ //error si es null
            validUsuario.setValue(Boolean.FALSE);
            errorlbl_usuario.setText("El usuario no puede ser nulo");
            errorlbl_usuario.visibleProperty().set(true);
            campo_usuario.requestFocus();
        }else if(campo_usuario.textProperty().getValueSafe().contains(" ")){ //error si tiene espacio
            validUsuario.setValue(Boolean.FALSE);
            errorlbl_usuario.setText("El usuario no puede contener espacios");
            errorlbl_usuario.visibleProperty().set(true);
            campo_usuario.requestFocus();
        }else if(Acount.getInstance().existsLogin(campo_usuario.textProperty().getValueSafe())){ //error si ya existe
            validUsuario.setValue(Boolean.FALSE);
            errorlbl_usuario.setText("El usuario ya existe");
            errorlbl_usuario.visibleProperty().set(true);
            campo_usuario.requestFocus();
        } else
            validUsuario.setValue(Boolean.TRUE);
            errorlbl_usuario.visibleProperty().set(false);
            campo_usuario.styleProperty().setValue("");        
    }
    
    private void comprobarContraseña(){
        if(!Utils.checkPassword(campo_contraseña.textProperty().getValueSafe())){
            validContraseña.setValue(Boolean.FALSE);
            errorlbl_contraseña.visibleProperty().set(true);
            campo_contraseña.styleProperty().setValue("-fx-background-color: #FCE5E0");
            campo_contraseña.requestFocus();
        }else{
            validContraseña.setValue(Boolean.TRUE);
            errorlbl_contraseña.visibleProperty().set(false);
            campo_contraseña.styleProperty().setValue("");
        }

    }
    
    private void comprobarIguales(){
        if(campo_contraseña.textProperty().getValueSafe().compareTo(
           campo_rep_contraseña.textProperty().getValueSafe()) != 0){
            campo_rep_contraseña.styleProperty().setValue("-fx-background-color: #FCE5E0");
            campo_contraseña.styleProperty().setValue("-fx-background-color: #FCE5E0");
            campo_rep_contraseña.requestFocus();
            errorlbl_rep_contraseña.visibleProperty().set(true);
            equalContraseñas.setValue(Boolean.FALSE);
            campo_rep_contraseña.textProperty().setValue("");
            campo_contraseña.textProperty().setValue("");
            campo_contraseña.requestFocus();
        }else{
            equalContraseñas.setValue(Boolean.TRUE);
            errorlbl_rep_contraseña.visibleProperty().set(false);
            campo_contraseña.styleProperty().setValue("");
            campo_rep_contraseña.styleProperty().setValue("");
        }
    }
    
    @FXML
    private void Acceder(ActionEvent event) throws AcountDAOException, IOException {

        String partes_nombre[] = campo_nombre.getText().split(" ");
        String nombre = partes_nombre[0];
        String apellidos = null;
        for(int i = 1; i < partes_nombre.length; i++){
            apellidos += (" " + partes_nombre[i]);
        }
        String correo = campo_correo.getText();
        String usuario = campo_usuario.getText();
        String contraseña = campo_contraseña.getText();

        Image avatar = null;
        
        LocalDate fecha_registro = LocalDate.now();
        
        boolean res = Acount.getInstance().registerUser(nombre, apellidos, correo,usuario, contraseña, avatar, fecha_registro);
        if(res){
            Alert alert = new Alert(AlertType.INFORMATION, "Usuario creado correctamente");
            alert.setHeaderText(null);
            alert.setOnHidden(evento -> Model.getInstance().getMainView().ventanaLogin());           
            alert.showAndWait();            
        }
    }

    private final Image defaultImage = new Image("Resources/icons/perfil.png");

    private void selectAvatar() {
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
            perfil.setImage(fotoPerfil);
        }
    }

}
