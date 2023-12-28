package Views;

import Controllers.UsuarioController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView {
    private AnchorPane vistaInicio;

    public MainView(){}

    public AnchorPane getVistaInicio() throws IOException {
        if(vistaInicio == null){
            vistaInicio = new FXMLLoader(getClass().getResource("/Resources/FXML/Base.fxml")).load();
        }
        return vistaInicio;
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
}
