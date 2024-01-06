package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public Button boton_inicio;
    public Button boton_historial;
    public Button boton_anadir;
    public Button boton_perfil;
    public Button boton_logout;
    public Button boton_contacto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        boton_inicio.setOnAction(event -> inicio());
        boton_historial.setOnAction(event -> historial());
        boton_anadir.setOnAction(event -> anadir());
        boton_perfil.setOnAction(event -> perfil());
    }

    private void inicio(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Inicio");
    }

    private void historial(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Historial");
    }

    private void anadir(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Añadir");
    }

    private void perfil(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Perfil");
    }
}
