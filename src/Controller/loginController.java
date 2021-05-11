package Controller;

import Play.Main;
import Utils.ConDB;
import Utils.Dialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class loginController extends Controllers implements Initializable, EventHandler<ActionEvent> {

    @FXML
    TextField usuario;
    @FXML
    PasswordField contraseña;
    @FXML
    CheckBox recordarPass;
    @FXML
    CheckBox mostrarPass;
    @FXML
    Button entrar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void login(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
        Connection con = ConDB.getConBDU();
        ResultSet rs1;
        if (contraseña.getText().equals("") || usuario.getText().equals("")) {
            Dialog.showError("Error Autenticación", "No se puede loguear", "Introduce correctamente los datos");
        }
        Statement stmt = con.createStatement();
        String query = "SELECT user_id, user_name, full_name FROM imp.sdics_users";
        System.out.println(query);
        rs1 = stmt.executeQuery(query);
        String nombreUsuario = "";
        String contraseñaUsuario = "";
        boolean correct = false;
        boolean valid = false;

           while (rs1.next()) {
                nombreUsuario = "" + rs1.getString("user_name");
                if (nombreUsuario.equals(usuario.getText())) {
                    correct = true;
                }

            }
            if (correct) {
                goAlertas();
            } else {
                Dialog.showError("Error", "Fallo de autenticacion", "Asegúrese de que los datos son correctos");
            }



    }

    @FXML
    public void goAlertas() throws IOException {
        Main.setRoot("alertasHome");
    }


    public static void cerrarVentana(ActionEvent e) {
        Node source = (Node) e.getSource();     //Me devuelve el elemento al que hice click
        Stage stage = (Stage) source.getScene().getWindow();    //Me devuelve la ventana donde se encuentra el elemento
        stage.close();                          //Me cierra la ventana
    }

    @Override
    public void handle(ActionEvent event) {
        handleCloseButtonAction(event);
        if (event.getSource() == entrar) {
            cerrarVentana(event);
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) entrar.getScene().getWindow();
        stage.close();
    }
}
