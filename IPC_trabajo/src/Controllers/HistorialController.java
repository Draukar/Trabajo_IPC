package Controllers;

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
import java.util.List;
import java.util.ResourceBundle;


public class HistorialController implements Initializable {
    public ListView Historial_listView;
    public TableColumn categoria;
    public TableColumn fecha;
    public TableColumn cantidad;
    public TableColumn concepto;
    public TableColumn saldo;
    public TableView mov_tableview;
    public Button boton_exportar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //boton_exportar.setOnAction(actionEvent -> exportarAPdf());
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
        concepto.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            Acount acount = Acount.getInstance();
            List<Charge> cargos = acount.getUserCharges();
            mov_tableview.setItems(FXCollections.observableList(cargos));
        } catch (AcountDAOException | IOException e) {
            e.printStackTrace();
        }

    }
}
