package client;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Client extends Application{

    public void start(Stage primaryStage) {
        try {
            URL fxmlURL=getClass().getResource("../vue/PenduVue.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Node root = fxmlLoader.load();
            Scene scene = new Scene((AnchorPane) root, 720, 480);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Pendu");
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println("Une erreur est survenue:\n" + e);
        }
    }
}
