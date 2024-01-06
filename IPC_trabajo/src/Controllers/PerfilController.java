package Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Acount;
import model.AcountDAOException;
import model.User;

public class PerfilController implements Initializable{

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
    @FXML
    private Button boton_guardar;
    @FXML
    private Button boton_cancelar;
    
    
    
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
        validCorreo.setValue(Boolean.FALSE);
        validUsuario.setValue(Boolean.FALSE);
        validContraseña.setValue(Boolean.FALSE);   
        equalContraseñas.setValue(Boolean.FALSE);
        
        //Comprobar que los datos introducidos en los campos son correctos
        /*campo_correo.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                comprobarEmail();
            }
        });
        
        campo_usuario.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                comprobarUsuario();
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
        });*/
    }

    @FXML
    private void Acceder(ActionEvent event) {
    }
}
