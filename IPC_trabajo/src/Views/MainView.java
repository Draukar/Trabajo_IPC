package Views;

import Controllers.UsuarioController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainView {
    private AnchorPane vistaInicio;
    private AnchorPane vistaHistorial;
    private AnchorPane vistaPerfil;
    private final StringProperty menuSeleccionado;

    public MainView(){
        this.menuSeleccionado = new SimpleStringProperty("");
    }

    public void ventanaBase(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Base.fxml"));
        crearStage(loader, "SpendWise");
    }

    public void ventanaLogin(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Login.fxml"));
        crearStage(loader, "SpendWise");
    }
    public void ventanaRegistro(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Registro.fxml"));
        crearStage(loader, "SpendWise");
    }

    public void ventanaInicio(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Usuario.fxml"));
        UsuarioController usuarioController = new UsuarioController();
        loader.setController(usuarioController);
        crearStage(loader, "SpendWise");
    }
    public void ventanaContacto(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Contacto.fxml"));
        crearStage(loader, "Contacto");
    }

    public Stage ventanaGasto() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Gasto.fxml"));
        Stage stageGasto = getStage(loader, "Añadir Gasto");
        stageGasto.initModality(Modality.APPLICATION_MODAL);
        stageGasto.showAndWait();

        return stageGasto;
    }
    public Stage ventanaCategoria(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resources/FXML/Categoria.fxml"));
        Stage stageCategoria = getStage(loader, "Añadir Categoría");
        stageCategoria.initModality(Modality.APPLICATION_MODAL);
        stageCategoria.showAndWait();

        return stageCategoria;
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

    public void crearStage(FXMLLoader loader, String titulo){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
        stage.getIcons().add(new Image("Resources/icons/Ahorros.png"));
        stage.setResizable(false);
    }
    public Stage getStage(FXMLLoader loader, String titulo) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.getIcons().add(new Image("Resources/icons/Ahorros.png"));
        stage.setResizable(false);

        return stage;
    }
    public void cerrarStage(Stage stage){
        stage.close();
    }

    public StringProperty getMenuSeleccionado(){
        return menuSeleccionado;
    }
}
