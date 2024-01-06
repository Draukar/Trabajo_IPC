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
    private BooleanProperty validNombre;
    private BooleanProperty validCorreo;
    private BooleanProperty validUsuario;
    private BooleanProperty validContraseña;
    private BooleanProperty equalContraseñas;  
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
        validCorreo = new SimpleBooleanProperty();
        validUsuario = new SimpleBooleanProperty();
        validContraseña = new SimpleBooleanProperty();   
        equalContraseñas = new SimpleBooleanProperty();
        
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
    
    //Funciones complementarias
    
    /*
    Función para cuando el campo es correcto. 
        Actualiza la boolean property y esconde el mensaje de error
    Los parámetros son:
        errorLabel el texto de alerta en caso de error
        textField el campo sobre el cual se ha hecho la comprobación
        boolProp la boolean property correspondiente
        
    */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    
    /*
    Función para cuando el campo es incorrecto
        Actualiza la boolean property. Muestra el mensaje de error. Devuelve el focus al campo para su corrección
    */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
 
    }
    
    //Funciones para mostrar o no los mensajes de error
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }
    
    //Comprobaciones sobre los campos
    private void comprobarEmail(){
        if(!Utils.checkEmail(campo_correo.textProperty().getValueSafe()))
            manageError(errorlbl_correo,campo_correo,validCorreo);
        else
            manageCorrect(errorlbl_correo,campo_correo,validCorreo);
            
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
            manageCorrect(errorlbl_usuario,campo_usuario,validUsuario);        
    }
    
    private void comprobarContraseña(){
        if(!Utils.checkPassword(campo_contraseña.textProperty().getValueSafe()))
            manageError(errorlbl_contraseña,campo_contraseña,validContraseña);
        else
            manageCorrect(errorlbl_contraseña,campo_contraseña,validContraseña);
    }
    
    private void comprobarIguales(){
        if(campo_contraseña.textProperty().getValueSafe().compareTo(
           campo_rep_contraseña.textProperty().getValueSafe()) != 0){
            showErrorMessage(errorlbl_rep_contraseña,campo_rep_contraseña);
            equalContraseñas.setValue(Boolean.FALSE);
            campo_rep_contraseña.textProperty().setValue("");
            campo_contraseña.textProperty().setValue("");
            campo_contraseña.requestFocus();
        }else{
            manageCorrect(errorlbl_rep_contraseña, campo_rep_contraseña, equalContraseñas);
        }
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
            Image fotoPerfil = new Image(selectedFile.toURI().toString());
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
        for(int i = 1; i < partes_nombre.length; i++){
            apellidos.append(partes_nombre[i]);
        }
        String correo = campo_correo.getText();
        String usuario = campo_usuario.getText();
        String contraseña = campo_contraseña.getText();

        Image avatar = getSelectedAvatar();
        
        LocalDate fecha_registro = LocalDate.now();
        
        boolean res = Acount.getInstance().registerUser(nombre, apellidos.toString(), correo,usuario, contraseña, avatar, fecha_registro);
        if(res){
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
