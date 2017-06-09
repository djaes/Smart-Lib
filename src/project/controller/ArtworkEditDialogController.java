package project.controller;

/**
 * Created by djaes on 04/06/2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.repository.Queries;
import project.service.Artwork;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


/**
 * Dialog to edit details of a artwork.
 *
 * @author Marco Jakob
 */
public class ArtworkEditDialogController implements Initializable{

    @FXML
    private TextField titleField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField supportField;
    @FXML
    private TextField originField;
    @FXML
    private TextField productYearField;

    @FXML
    private TextArea commentField;



    @FXML
    private Label idLabel;
    @FXML
    private TextField creatorField;

    //ComboBox
    @FXML
    private ComboBox<String> categoryCB;
    @FXML
    private ComboBox<String> genreCB;
    @FXML
    private ComboBox<String> originCB;
    @FXML
    private ComboBox<String> supportCB;
    @FXML
    private ComboBox<String> creatorCB;
    @FXML
    private ComboBox<Integer> ratingCB;


    private Stage dialogStage;
    private Artwork artwork;
    private boolean okClicked = false;
    private String createUpdate ;
    private int choiceValueOf;



    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the artwork to be edited in the dialog.
     *
     * @param artwork
     */
    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;

        idLabel.setText(Integer.toString(artwork.getId()));
        titleField.setText(artwork.getTitle());
        categoryField.setText(artwork.getCategory());
        genreField.setText(artwork.getGenre());
        supportField.setText(artwork.getSupport());
        originField.setText(artwork.getOrigin());
        productYearField.setText(Integer.toString(artwork.getYear()));
        creatorField.setText(artwork.getCreator());
        // TODO: On a oublier L'origine
        commentField.setText(artwork.getComment());
        //rateField.setText(Integer.toString(artwork.getRating()));
//
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked(String update) {
        if (update.equals("update")) {
            //System.out.println("update");
            Queries q = new Queries();
            try {
                q.createUpdateArtwork("update", artwork);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //System.out.println(artwork.getTitle());
        }

        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            artwork.setId(Integer.parseInt(idLabel.getText()));
            artwork.setTitle(titleField.getText());
            artwork.setCategory(categoryField.getText());
            artwork.setGenre(genreField.getText());
            artwork.setSupport(supportField.getText());
            artwork.setOrigin(originField.getText());
            // TODO: a voir pour le comment
            artwork.setComment(commentField.getText());
            artwork.setYear(Integer.parseInt(productYearField.getText()));


            //artwork.setRating(Integer.parseInt(rateField.getText()));



            artwork.setCreator(creatorField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Titre invalide!\n";
        }
        if (categoryField.getText() == null || categoryField.getText().length() == 0) {
            errorMessage += "Categorie invalide!\n";
        }
        if (genreField.getText() == null || genreField.getText().length() == 0) {
            errorMessage += "Genre invalide )!\n";
        }
        if (supportField.getText() == null || supportField.getText().length() == 0) {
            errorMessage += "Support invalide )!\n";
        }
        if (originField.getText() == null || originField.getText().length() == 0) {
            errorMessage += "No valid )!\n";
        }
        if (productYearField.getText() == null || productYearField.getText().length() == 0) {
            errorMessage += "L'année de production est invalide!\n";
        }
         else {
            try {
                Integer.parseInt(productYearField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "le format de l'année de production est invalide)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalide");
            alert.setHeaderText("Veuillez corriger les champs invalide");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    private ObservableList<String> listCategory = FXCollections.observableArrayList();
    private ObservableList<String> listGenre = FXCollections.observableArrayList();
    private ObservableList<String> listSupport = FXCollections.observableArrayList();
    private ObservableList<String> listOrigin = FXCollections.observableArrayList();
    private ObservableList<String> listCreator = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genreCB.setDisable(true);
        supportCB.setDisable(true);
        originCB.setDisable(true);
        creatorCB.setDisable(true);
        ratingCB.setDisable(true);

        Queries q = new Queries();
        try {
            listCategory = q.getListCharat("category");
            listGenre = q.getListCharat("genre");
            listCreator = q.getListCharat("creator");
            listOrigin = q.getListCharat("origin");
            listSupport = q.getListCharat("support");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        categoryCB.getItems().add("Selection Categorie");
        //searchCatCB.getItems().addAll(filteredData.stream().map(Artwork::getCategory).distinct().collect(Collectors.toList()));
        categoryCB.getItems().addAll(listCategory);
        categoryCB.setOnAction(e -> {
            Object selectedItem = categoryCB.getSelectionModel().getSelectedItem();
            String choiceCat = String.valueOf(selectedItem);
            categoryField.setText(choiceCat);
            activeOtherCB(choiceCat);

        });
        categoryField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(activeOtherCB(newValue)) {

                categoryCB.getSelectionModel().select(newValue);
                /*
                //todo: la on recupere une list de genre,support, etc... grace a une requete sur le db
                 */
                genreCB.getItems().add("Selection Genre");
                genreCB.getItems().addAll(listGenre);
                genreCB.getSelectionModel().select("Selection Genre");
                genreCB.setOnAction(e -> {
                            Object selectedItem = genreCB.getSelectionModel().getSelectedItem();
                            String choiceValue = String.valueOf(selectedItem);
                            genreField.setText(choiceValue);
                        });
                supportCB.getItems().add("Selection Support");
                supportCB.getItems().addAll(listSupport);
                supportCB.getSelectionModel().select("Selection Support");
                supportCB.setOnAction(e -> {
                    Object selectedItem = supportCB.getSelectionModel().getSelectedItem();
                    String choiceValue = String.valueOf(selectedItem);
                    supportField.setText(choiceValue);
                });
                originCB.getItems().add("Selection Origine");
                originCB.getItems().addAll(listOrigin);
                originCB.getSelectionModel().select("Selection Origine");
                originCB.setOnAction(e -> {
                    Object selectedItem = originCB.getSelectionModel().getSelectedItem();
                    String choiceValue = String.valueOf(selectedItem);
                    originField.setText(choiceValue);
                });
                creatorCB.getItems().add("Selection Createur");
                creatorCB.getItems().addAll(listCreator);
                creatorCB.getSelectionModel().select("Selection Createurs");
                creatorCB.setOnAction(e -> {
                    Object selectedItem = creatorCB.getSelectionModel().getSelectedItem();
                    String choiceValue = String.valueOf(selectedItem);
                    creatorField.setText(choiceValue);
                });
                ratingCB.getItems().addAll(0,1,2,3,4,5);
                ratingCB.getSelectionModel().select(artwork.getRating());
                ratingCB.setOnAction(e ->{
                    int selectedItem = ratingCB.getSelectionModel().getSelectedItem();
                    choiceValueOf = selectedItem;
                    artwork.setRating(choiceValueOf);
                });


            }
        });
    }
    private boolean activeOtherCB(String newValue){
        List<String> tmpCat = listCategory.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        if (newValue != null && tmpCat.contains(newValue.toLowerCase())){
            genreCB.setDisable(false);
            supportCB.setDisable(false);
            originCB.setDisable(false);
            creatorCB.setDisable(false);
            ratingCB.setDisable(false);
            return true;
        }
        return false;
    }
}