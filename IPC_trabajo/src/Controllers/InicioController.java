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

    }
}

