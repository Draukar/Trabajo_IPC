package Controllers;

import Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class InicioController implements Initializable {

    public Label Saludo;
    public Label ingresos;
    public Label gastos;
    public TableView<Charge> mov_tableview;
    public TableColumn<Charge, Category> categoria;
    public TableColumn<Charge, LocalDate> fecha;
    public TableColumn<Charge, Double> cantidad;
    public TableColumn<Charge, String> concepto;
    public TableColumn<Charge, Integer> unidades;
    String nombre = null;
    @FXML
    private Pane compIngGas_pane;
    @FXML
    private Pane gastos_pane;
    @FXML
    private Button buton_borrar;
    
    private ObservableList<Charge> listaDeGastos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            nombre = Acount.getInstance().getLoggedUser().getName();
        } catch (AcountDAOException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Saludo.setText("Buenos días, " + nombre);


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
            List<Charge> charges = acount.getUserCharges();
            int startIndex = Math.max(0, charges.size() - 4);
            List<Charge> ultimosCargos = charges.subList(startIndex, charges.size());
            mov_tableview.setItems(FXCollections.observableList(ultimosCargos));
        } catch (AcountDAOException | IOException e) {
            e.printStackTrace();
        }
        
        //Activar o desactivar el boton de borrar si hay algún gasto seleccionado o no
        buton_borrar.setDisable(true);

        // Agregar un listener a la selección de la TableView
        mov_tableview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Habilitar el botón de borrar si hay algo seleccionado
                buton_borrar.setDisable(false);
            } else {
                // Desactivar el botón de borrar si no hay nada seleccionado
                buton_borrar.setDisable(true);
            }
        });

    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws AcountDAOException, IOException {
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
            buton_borrar.setDisable(true);
            
        }
        
    }
}

