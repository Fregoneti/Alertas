package Play;

import Controller.AppController;
import Controller.Controllers;
import Controller.Scenes;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.net.URL;

public class Main extends Application {

    public static Scene scene;
    public Stage mainStage;
    public AppController controller;
    public BorderPane rootLayout;



    @Override
    public void start (Stage stage) throws IOException{
        scene=new Scene(loadFXML("../FXML/login"),640, 480);
        stage.setScene(scene);
        stage.setTitle("Alertas Automatizadas");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../FXML/"+fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

