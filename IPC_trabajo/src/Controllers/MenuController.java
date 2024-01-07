package Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Model.Model;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        boton_inicio.setOnAction(event -> inicio());
        boton_anadir.setOnAction(actionEvent -> {
            Model.getInstance().getMainView().ventanaGasto();
        });
        boton_historial.setOnAction(event -> historial());
        boton_perfil.setOnAction(event -> perfil());
        boton_logout.setOnAction(actionEvent -> logout());
        boton_contacto.setOnAction(actionEvent -> Model.getInstance().getMainView().ventanaContacto());

    }

    private void inicio(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Inicio");
    }

    private void historial(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Historial");
    }

    private void perfil(){
        Model.getInstance().getMainView().getMenuSeleccionado().set("Perfil");
    }
    public void logout(){
        Stage stage = (Stage) boton_inicio.getScene().getWindow();
        Model.getInstance().getMainView().cerrarStage(stage);
        Model.getInstance().getMainView().ventanaBase();

    }

}
