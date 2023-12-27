/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.EQUALS;
import javafx.scene.text.Text;


public class FXMLSignUpController implements Initializable {


 
    //properties to control valid fieds values. 
    private BooleanProperty validPassword;
    private BooleanProperty validEmail;

    //private BooleanBinding validFields;
    
    //When to strings are equal, compareTo returns zero
    @FXML
    private TextField email;
    @FXML
    private Label lIncorrectEmail;
    @FXML
    private Label lIncorrectPassword;
    @FXML
    private PasswordField password;
   
    private final int EQUALS = 0;
    @FXML
    private Button bAccept;
    @FXML
    private Button bCancel;
    @FXML
    private Button bRegistro;

    /**
     * Updates the boolProp to false.Changes to red the background of the edit. 
     * Makes the error label visible and sends the focus to the edit. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        textField.requestFocus();
 
    }
    /**
     * Updates the boolProp to true. Changes the background 
     * of the edit to the default value. Makes the error label invisible. 
     * @param errorLabel label added to alert the user
     * @param textField edit text added to allow user to introduce the value
     * @param boolProp property which stores if the value is correct or not
     */
    private void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
        
    }
    /**
     * Changes to red the background of the edit and
     * makes the error label visible
     * @param errorLabel
     * @param textField 
     */
    private void showErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    /**
     * Changes the background of the edit to the default value
     * and makes the error label invisible.
     * @param errorLabel
     * @param textField 
     */
    private void hideErrorMessage(Label errorLabel,TextField textField)
    {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("");
    }


    

    
    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        validEmail = new SimpleBooleanProperty();
        validPassword = new SimpleBooleanProperty();   
        
        validPassword.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);

        //Listener a la propiedad focused que indica cuándo el usuario está en el TextField
        email.focusedProperty().addListener((observable,oldValue,newValue)->{
            if(!newValue){//focus lost
                checkEditEmail();
            }
        });
        
        password.focusedProperty().addListener((observable,oldValue,newValue) -> {
            if(!newValue){
                checkEditPassword();
            }
        });
        
        BooleanBinding validFields = Bindings.and(validEmail, validPassword);
         
        bAccept.disableProperty().bind(Bindings.not(validFields));
        
        bCancel.setOnAction( (event)->{bCancel.getScene().getWindow().hide();});
        
    } 
    
    private void checkEditEmail(){
        if(!Utils.checkEmail(email.textProperty().getValueSafe()))
            manageError(lIncorrectEmail,email,validEmail);
        else
            manageCorrect(lIncorrectEmail,email,validEmail);
            
    }
    
    private void checkEditPassword(){
        if(!Utils.checkPassword(password.getText()))
            manageError(lIncorrectPassword,password,validPassword);
        else
            manageCorrect(lIncorrectPassword,password,validPassword);
    }

    @FXML
    private void aceptar(ActionEvent event) {
        email.textProperty().setValue("");
        password.textProperty().setValue("");
        
        validEmail.setValue(Boolean.FALSE);
        validPassword.setValue(Boolean.FALSE);
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }

    
}