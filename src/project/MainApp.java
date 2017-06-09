package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.controller.ArtworkEditDialogController;
import project.service.Artwork;
import project.controller.ArtworkOverviewController;
import project.controller.HomeController;


import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by djaes on 06/06/2017.
 */
public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws SQLException {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Smart Lib");
        initRootLayout();
        //homePage();
        showArtworkOverview();

    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
    }

    private void homePage (){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Home.fxml"));
            AnchorPane artworkOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(artworkOverview);
            HomeController controller = loader.getController();
            //controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showArtworkOverview() {
        try {
            // Load Artwork overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ArtworkOverview.fxml"));
            AnchorPane artworkOverview = (AnchorPane) loader.load();
            // Set artwork overview into the center of root layout.
            rootLayout.setCenter(artworkOverview);
            // Give the controller access to the main app.
            ArtworkOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens a dialog to edit details for the specified artwork. If the user
     * clicks OK, the changes are saved into the provided artwork object and true
     * is returned.
     *
     * @param artwork the artwork object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showArtworkEditDialog(Artwork artwork, String createUpdate) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ArtworkEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajout/Edition d'oeuvre'");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the artwork into the controller.
            ArtworkEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArtwork(artwork);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked(createUpdate);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
