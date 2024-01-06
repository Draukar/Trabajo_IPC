package Controllers;

import Model.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {
    public BorderPane usuario_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getMainView().getMenuSeleccionado().addListener(((observableValue, viejoValor, nuevoValor) -> {
            switch (nuevoValor) {
                case "Historial":
                    usuario_parent.setCenter(Model.getInstance().getMainView().getVistaHistorial());
                    break;      
                case "Añadir":
                    usuario_parent.setCenter(Model.getInstance().getMainView().getVistaAnadir());
                    break;
                case "Perfil":
                    usuario_parent.setCenter(Model.getInstance().getMainView().getVistaPerfil());
                    break;
                case "Logout": 
                    usuario_parent.setCenter(Model.getInstance().getMainView().getVistaPerfil());
                default:
                    usuario_parent.setCenter(Model.getInstance().getMainView().getVistaInicio());
                    break;
            }
        }));
    }
}
