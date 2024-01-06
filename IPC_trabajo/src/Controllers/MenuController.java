package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.Model;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Stage;

public class MenuController implements Initializable {
    public Button boton_inicio;
    public Button boton_historial;
    public Button boton_anadir;
    public Button boton_perfil;
    public Button boton_logout;
    public Button boton_contacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boton_anadir.setOnAction(actionEvent -> anadir());
        //boton_historial.setOnAction(actionEvent -> historial());
        //boton_perfil.setOnAction(actionEvent -> registro());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());
    }
    
    public void anadir(){
        Stage stage = (Stage) boton_anadir.getScene().getWindow();
        Model.getInstance().getMainView().ventanaGasto();
        stage.showAndWait();
    }
    
    
}
