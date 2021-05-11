package Controller;

import Dao.alertaDao;
import Model.Alerta;
import Play.Main;
import Utils.Dialog;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AlertasHomeController extends Controllers implements Initializable {

    private java.sql.Connection con;

    public ObservableList<Alerta> data;

    @FXML
    private TableView<Alerta> table;
    @FXML
    private TableColumn<Alerta, String> n_Alerta;
    @FXML
    public ComboBox tipoAlerta=;
    @FXML
    Button añadir;
    @FXML
    Button borrar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        this.data = FXCollections.observableArrayList();

        //se ejecuta automaticamente cuando un controlador (es decir, una escena) es cargada
        List<Alerta> misAlertas = alertaDao.getTodasAlertas(con);
        data.addAll(misAlertas);

        this.n_Alerta.setCellValueFactory(eachRowData -> {
            return new SimpleObjectProperty<>(eachRowData.getValue().getTipoAlerta());

        });
        table.setItems(data);


        tipoAlerta.getItems().addAll(
                "Stock",
                "Prueba"

        );
     /*   //editables

        n_Alerta.setCellFactory(TextFieldTableCell.forTableColumn());
        n_Alerta.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Alerta, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Alerta, String> t) {

                        Alerta selected = (Alerta) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());

                        selected.setTipoAlerta(t.getNewValue());  //<<- update lista en vista

                        alertaDao dao = new alertaDao(selected); //update en mysql
                        dao.save();
                    }
                }
        );*/
    }


    @FXML

    public void deleteAlerta() {
        Alerta selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (!showConfirm(selected.getTipoAlerta())) {
                return;
            }
            data.remove(selected);
        } else {
            showWarning("¡Cuidado!", "Ninguna alerta que borrar", "Seleccione una alerta antes de borrar");
        }
    }

    public void showWarning(String tittle, String header, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(tittle);
        alert.setHeaderText(header);
        alert.setContentText(header);
        alert.showAndWait();
    }

    public boolean showConfirm(String title){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Apunto de eliminar");
        alert.setContentText("A punto de borrar "+n_Alerta);

        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            return true;
        }else{
            return false;
        }
    }

    @FXML
    public void crearAlerta() throws IOException {
        String opcion= (String) tipoAlerta.getValue();
        switch (opcion){
            case "Stk":Main.setRoot("alertaStockC");break;
            default:
                Dialog.showError("Error","Tipo de alerta no seleccionada","Seleccione primero un tipo de alerta, antes deobrrar");
        }


    }
}
