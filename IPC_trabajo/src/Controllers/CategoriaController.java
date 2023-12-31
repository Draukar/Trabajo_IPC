package Controllers;

import Model.Model;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale.Category;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.webkit.Timer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

public class CategoriaController implements Initializable {

    @FXML
    private Button boton_anadir;
    @FXML
    private Button boton_limpiar;
    @FXML
    private TextField campo_nombre;
    @FXML
    private TextArea campo_descripcion;
    @FXML
    private Label lbl_error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        /*
        boton_anadir y boton_limpiar deshabilitados
            boton_anadir se activa cuando haya texto en campo_nombre y en campo_descripcion
            boton_limpiar cuando haya texto en alguno de los dos
        */
        boton_anadir.setDisable(true);
        boton_limpiar.setDisable(true);
        
        campo_nombre.textProperty().addListener((a, b, c) -> {     
            if(!campo_nombre.getText().isEmpty()){
                boton_limpiar.setDisable(false);
                if(!campo_descripcion.getText().isEmpty())boton_anadir.setDisable(false);
            }else {
                boton_limpiar.setDisable(true);
                boton_anadir.setDisable(true);
            }
        });
        
        campo_descripcion.textProperty().addListener((a, b, c) -> {           
            if(!campo_descripcion.getText().isEmpty()){
                boton_limpiar.setDisable(false);
                if(!campo_nombre.getText().isEmpty()) boton_anadir.setDisable(false);
            }else {
                boton_limpiar.setDisable(true);
                boton_anadir.setDisable(true);
            }
        });

        campo_nombre.focusedProperty().addListener((a, b, c) -> {
            if(!c){
                try {
                    validarCategoria();
                } catch (AcountDAOException | IOException ex) {
                    Logger.getLogger(CategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }   

    public void validarCategoria() throws AcountDAOException, IOException{
        List<model.Category> categorias = Acount.getInstance().getUserCategories();
        boolean existeCategoria = false;
        for (int i = 0; i < categorias.size(); i++) {
            if (campo_nombre.getText().equals(categorias.get(i).getName())) {
                 // La categoría ya existe en la lista               
                existeCategoria = true;
                break;  // Puedes salir del bucle tan pronto como encuentres la categoría
            }
        }

        if (existeCategoria) { //La categoría ya existe
            lbl_error.visibleProperty().set(true);
            campo_nombre.requestFocus();
        }else{
            lbl_error.visibleProperty().set(false);
        }
    }
    @FXML
    private void anadir(ActionEvent event) throws AcountDAOException, IOException {
        validarCategoria();

            boolean aux = Acount.getInstance().registerCategory(campo_nombre.getText(), campo_descripcion.getText());
            if (aux) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Categoría creada correctamente");
                alert.setHeaderText(null);
                cerrar();
                alert.showAndWait();
            }
    }

    @FXML
    private void limpiar(ActionEvent event) {
        campo_nombre.setText("");
        campo_descripcion.setText("");
        campo_nombre.requestFocus();
    }
    private void cerrar(){
        Stage stage = (Stage) boton_limpiar.getScene().getWindow();
        stage.hide();
    }
}
