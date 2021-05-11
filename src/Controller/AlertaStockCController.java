package Controller;

import Dao.alertaDao;
import Model.Alerta;
import Model.AlertaRepositorio;
import Play.Main;
import Utils.Dialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AlertaStockCController extends Controllers implements Initializable {

    @FXML
    TextField articulo_Code;
    @FXML
    ChoiceBox condicion;
    @FXML
    TextField cantidad;
    @FXML
    TextField almacen_Code;
    @FXML
    TextField emailTo;
    @FXML
    ChoiceBox periodo;

    AlertaRepositorio a= AlertaRepositorio.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        condicion.getItems().addAll(
                "<",
                ">",
                "<=",
                ">=");


        periodo.getItems().addAll(
                "Diariamente",
                "Semanalmente",
                "Mensualmente",
                "Anualmente");
    }


    @FXML
    public void cancelar() throws IOException {
        Main.setRoot("alertasHome");
    }
    @FXML
    public void crearAlerta() throws IOException, SQLException, ClassNotFoundException {
        if(articulo_Code.getText().equals("") || cantidad.getText().equals("")|almacen_Code.getText().equals("")||emailTo.getText().equals("")){
            Dialog.showError("ERROR","Error creando alerta","Completar todos los parámetros antes de crear la alerta");
        }else{
            addAlerta();
            Main.setRoot("alertasHome");
        }


    }
    @FXML
    public void addAlerta() throws SQLException, ClassNotFoundException {
        Alerta nuevo=new Alerta();
        nuevo.setArticulo_id(articulo_Code.getText());
        nuevo.setCondicion((String) condicion.getValue());
        nuevo.setCantidad(Double.parseDouble(cantidad.getText()));
        nuevo.setAlmacenID(almacen_Code.getText());
        nuevo.setEmailTo(emailTo.getText());
        nuevo.setPeriodicidad((String) periodo.getValue());

        nuevo.setTipoAlerta(Alerta.tipoA.STK.name());
        alertaDao nuevoDao=new alertaDao(nuevo);
        nuevoDao.save();

    }
}
