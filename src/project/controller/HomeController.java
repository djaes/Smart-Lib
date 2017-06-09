package project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import project.MainApp;

import java.io.IOException;

/**
 * Created by mahmoud on 07/06/17.
 */


public class HomeController {

    @FXML
    private Button accessMedia;
    @FXML
    private void accessSmartLib(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) accessMedia.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("RootLayout.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    private MainApp mainApp ;

    public void setMainApp(MainApp  mainApp){
        this.mainApp = mainApp;

    }

    //mainApp.
}
