package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import Model.Model;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;


public class GastoController implements Initializable {


    public TextField concepto;
    public TextField cantidad;
    public ChoiceBox<model.Category> categoria;
    public Button boton_imagen;
    public TextArea descripcion;
    public Button boton_anadir;
    public Button boton_limpiar;
    public Label errorlbl_cantidad;
    public TextField unidades;
    public DatePicker fecha;
    public ImageView imgScan;
    public Button boton_categoria;
    private BooleanProperty validCantidad = new SimpleBooleanProperty();
    private BooleanProperty validUnidades = new SimpleBooleanProperty();
    private BooleanProperty valido = new SimpleBooleanProperty(false);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        validCantidad.setValue(Boolean.FALSE);
        validUnidades.setValue(Boolean.FALSE);
        valido.setValue(Boolean.FALSE);
        boton_anadir.setDisable(true);
        bindValidCantidad();
        bindValidUnidades();

        boton_imagen.setOnAction(e -> scanIMG());
        boton_anadir.setOnAction(actionEvent -> {
            try {
                anadirGasto();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Añadido correctamente");
                alert.setHeaderText(null);
                cerrar();
                alert.showAndWait();
            } catch (AcountDAOException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        boton_categoria.setOnAction(actionEvent -> {
            Model.getInstance().getMainView().ventanaCategoria();
            cerrar();
        });

        //Cargar categorías
        try {
            cargarCategorias();
        } catch (AcountDAOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cerrar(){
        Stage stage = (Stage) boton_limpiar.getScene().getWindow();
        stage.hide();
    }
    private void cargarCategorias() throws AcountDAOException, IOException {
        List<Category> categorias = Acount.getInstance().getUserCategories();
        ObservableList<model.Category> listaCategorias = FXCollections.observableArrayList(categorias);
        categoria.setItems(listaCategorias);
        categoria.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.getName(): "";
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
    }
    private void bindValidCantidad() {
        cantidad.textProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!isValidCantidad(newVal)) {
                errorlbl_cantidad.setText("Máximo 2 decimales, separados por punto");
                errorlbl_cantidad.visibleProperty().set(true);
                cantidad.setStyle("-fx-border-color: red;");
            } else {
                errorlbl_cantidad.visibleProperty().set(false);
                cantidad.setStyle(""); // Restablecer el estilo
                validCantidad.set(true);
            }
        }); validCantidad.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAnadir());
    }
    private boolean isValidCantidad(String text) {
        String decimalPattern = "\\d*||\\d*+.\\d{1,2}";
        return Pattern.matches(decimalPattern, text);
    }
    private void bindValidUnidades() {
        unidades.textProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!isValidUnidades(newVal)) {
                errorlbl_cantidad.setText("Las unidades deben ser enteras");
                errorlbl_cantidad.visibleProperty().set(true);
                unidades.setStyle("-fx-border-color: red;");
            } else {
                errorlbl_cantidad.visibleProperty().set(false);
                unidades.setStyle(""); // Restablecer el estilo
                validUnidades.set(true);
            }
        }); validUnidades.addListener((observable, oldValue, newValue) -> actualizarEstadoBotonAnadir());
    }
    private boolean isValidUnidades(String text) {
        String decimalPattern = "\\d";
        return Pattern.matches(decimalPattern, text);
    }
    private void actualizarEstadoBotonAnadir() {
        boton_anadir.setDisable(!(validCantidad.get() && validUnidades.get()));
    }

    private void anadirGasto() throws AcountDAOException, IOException {
        Acount acount = Acount.getInstance();

        String concepto = this.concepto.getText();
        String descripcion = this.descripcion.getText();

        try {
            Double cantidad = Double.parseDouble(this.cantidad.getText());
            int unidades = Integer.parseInt(this.unidades.getText());
            LocalDate fecha = this.fecha.getValue();
            Image imagen = this.fotoTicket;
            Category categoria = (Category) this.categoria.getValue();

            acount.registerCharge(concepto, descripcion, cantidad, unidades, imagen, fecha, categoria);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private Image fotoTicket = null;

    private void scanIMG() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Mostrar el diálogo para seleccionar el archivo
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Actualizar la imagen en el ImageView con la nueva imagen
            fotoTicket = new Image(selectedFile.toURI().toString());
            imgScan.setImage(fotoTicket);
        }
    }


}
