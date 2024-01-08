package Controllers;

import Model.Model;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class HistorialController implements Initializable {
    public ListView Historial_listView;
    public TableColumn categoria;
    public TableColumn fecha;
    public TableColumn cantidad;
    public TableColumn concepto;
    public TableColumn unidades;
    public TableView mov_tableview;
    public Button boton_exportar;
    @FXML
    private Button boton_borrar;
    
    private ObservableList<Charge> listaDeGastos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        mov_tableview.setItems(listaDeGastos);
                
        categoria.setCellFactory(column -> {
            return new TableCell<Charge, Category>() {
                @Override
                protected void updateItem(Category category, boolean empty) {
                    super.updateItem(category, empty);

                    if (empty || category == null) {
                        setText(null);
                    } else {
                        setText(category.getName());
                    }
                }
            };
        });

        categoria.setCellValueFactory(new PropertyValueFactory<>("category"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("date"));
        cantidad.setCellValueFactory(new PropertyValueFactory<>("cost"));
        unidades.setCellValueFactory(new PropertyValueFactory<>("units"));
        concepto.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            Acount acount = Acount.getInstance();
            List<Charge> cargos = acount.getUserCharges();
            mov_tableview.setItems(FXCollections.observableList(cargos));
        } catch (AcountDAOException | IOException e) {
            e.printStackTrace();
        }
        
        //Activar o desactivar el boton de borrar si hay algún gasto seleccionado o no
        boton_borrar.setDisable(true);

        // Agregar un listener a la selección de la TableView
        mov_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Habilitar el botón de borrar si hay algo seleccionado
                boton_borrar.setDisable(false);
            } else {
                // Desactivar el botón de borrar si no hay nada seleccionado
                boton_borrar.setDisable(true);
            }
        });
        
        

    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws AcountDAOException, IOException{
        Charge selectedCharge = (Charge) mov_tableview.getSelectionModel().getSelectedItem();

        // Verificar si hay algo seleccionado
        if (selectedCharge != null) {
            // Eliminar el Charge de la base de datos
            Acount.getInstance().removeCharge(selectedCharge);

            // Eliminar el Charge de la TableView
            listaDeGastos.remove(selectedCharge);

            //Actualizar la TableView
            mov_tableview.refresh();
            
            // Desactivar el botón de borrar después de eliminar
            boton_borrar.setDisable(true);   
        }
    }

    @FXML
    private void exportarPDF(ActionEvent event) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        
        if(printerJob != null && printerJob.showPrintDialog(boton_exportar.getScene().getWindow())){
            Node imprimir = contenidoPDF();
            if(printerJob.printPage(imprimir)) printerJob.endJob();
        }
    }
    
    private Node contenidoPDF(){
       // Crear una tabla temporal para copiar el contenido de la TableView
        TableView<Charge> tablaTemporal = new TableView<>();
        TableColumn<Charge, Category> columnaCategoria = new TableColumn<>("Categoría");
        TableColumn<Charge, LocalDate> columnaFecha = new TableColumn<>("Fecha");
        TableColumn<Charge, Double> columnaCantidad = new TableColumn<>("Cantidad");
        TableColumn<Charge, String> columnaConcepto = new TableColumn<>("Concepto");
        TableColumn<Charge, Integer> columnaUnidades = new TableColumn<>("Unidades");

        // Asignar las propiedades de las entidades a las columnas
        columnaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnaFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columnaConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        columnaUnidades.setCellValueFactory(new PropertyValueFactory<>("unidades"));

        // Agregar las columnas a la tabla
        tablaTemporal.getColumns().addAll(columnaCategoria, columnaFecha, columnaCantidad, columnaConcepto, columnaUnidades);

        // Copiar los datos de la TableView original a la tabla temporal
        tablaTemporal.setItems(mov_tableview.getItems());


        return tablaTemporal;
    }
}
