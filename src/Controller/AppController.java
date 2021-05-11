package Controller;

import java.io.IOException;

import com.sun.javafx.util.Utils;
import com.sun.webkit.network.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppController {

    public Scenes backScene;
    public Scenes currentScene;


    /**
     * Carga en el Layout de la app principal la escena que se le pase en la
     * zona central. (requisito: el layout principal debe ser borderpane). Le
     * pasa automaticamente al controlador de la escena la referncia a la clase
     * principal para poder tener acceso a su controlador.
     *
     * @param scene La escena a cargar @see(Scenes)
     */
  /*  public void changeScene(Scenes scene) {
        try {
            MapEntry<Parent, Controllers> m = loadFXML(scene.getUrl());
            this.app.rootLayout.setCenter(m.getKey());
            if (m.getValue() != null) {
                m.getValue().setMainApp(this.app);
            }
            if (backScene != currentScene) {
                backScene = currentScene;
            }
            this.currentScene = scene;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    *//**
     * No se usa en este proyecto, dado que nunca se cambiará el rootLayout. Se
     * deja como documentación
     *
     * @param fxml
     * @throws IOException
     *//*
    private void setRoot(Scenes scene) throws IOException {
        this.backScene = null;
        this.currentScene = null;
        MapEntry<Parent, Controllers> m = loadFXML(scene.getUrl());
        this.app.scene.setRoot(m.getKey());
        this.app.controller = (AppController) m.getValue();
    }


    @FXML
    public void closeApp() {
        System.exit(0);
    }*/

}
