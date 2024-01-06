package Views;

import Controllers.UsuarioController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainView {
    private AnchorPane vistaInicio;
    private AnchorPane vistaHistorial;
    private AnchorPane vistaAnadir;
    private AnchorPane vistaPerfil;
    private final StringProperty menuSeleccionado;

    public MainView(){
        this.menuSeleccionado = new SimpleStringProperty("");
    }

    public void ventanaBase(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Base.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SpendWise");
        stage.show();
    }

    public void ventanaLogin(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Login.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SpendWise");
        stage.show();
    }
    public void ventanaRegistro(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Registro.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SpendWise");
        stage.show();
    }

    public void ventanaInicio(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Usuario.fxml"));
        UsuarioController usuarioController = new UsuarioController();
        loader.setController(usuarioController);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SpendWise");
        stage.show();
    }
    public void ventanaContacto(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Contacto.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("SpendWise");
        stage.show();
    }
    
    public void ventanaGasto(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Gasto.fxml"));
        Scene scene = null; 
    }

    // métodos Menú Aplicación

    public AnchorPane getVistaInicio(){
        if(vistaInicio == null){
            try {
                vistaInicio = new FXMLLoader(getClass().getResource("/Resources/FXML/Inicio.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return vistaInicio;
    }

    public AnchorPane getVistaHistorial(){
        if(vistaHistorial == null){
            try {
                vistaHistorial = new FXMLLoader(getClass().getResource("/Resources/FXML/History.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return vistaHistorial;
    }

    public AnchorPane getVistaAnadir(){
        if(vistaAnadir == null){
            try {
                vistaAnadir = new FXMLLoader(getClass().getResource("/Resources/FXML/Gasto.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return vistaAnadir;
    }

    public AnchorPane getVistaPerfil(){
        if(vistaPerfil == null){
            try {
                vistaPerfil = new FXMLLoader(getClass().getResource("/Resources/FXML/Perfil.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return vistaPerfil;
    }

    public void cerrarStage(Stage stage){
        stage.close();
    }

    public StringProperty getMenuSeleccionado(){
        return menuSeleccionado;
    }
}
