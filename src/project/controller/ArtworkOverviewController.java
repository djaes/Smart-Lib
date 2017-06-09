package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.MainApp;
import project.repository.Queries;
import project.service.Artwork;

import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * Created by djaes on 06/06/2017.
 */
public class ArtworkOverviewController {

    @FXML
    private TextField searchTitleField;

    @FXML
    private TableColumn<Artwork, Integer> idListColumn;

    @FXML
    private TableColumn<Artwork, Integer> yearsListColumn;

    @FXML
    private TableColumn<Artwork, String> genreListColumn;

    @FXML
    private TableColumn<Artwork, String> supportListColumn;

    @FXML
    private TableColumn<Artwork, String> categoryListColumn;

    @FXML
    private TableColumn<Artwork, Integer> ratingListColumn;

    @FXML
    private TableColumn<Artwork, String> creatorListColumn;

    @FXML
    private TableColumn<Artwork, String> titleListColumn;



    @FXML
    private Label idLabelSelect;
    @FXML
    private Label titleLabelSelect;
    @FXML
    private Label categoryLabelSelect;
    @FXML
    private Label ratingLabelSelect;
    @FXML
    private TextArea commentTextAreaSelect;

    @FXML
    private Label commentLabelSelect;

    @FXML
    private Label genreLabelSelect;
    @FXML
    private Label creatorLabelSelect;
    @FXML
    private Label supportLabelSelect;
    @FXML
    private Label yearLabelSelect;
    @FXML
    private Label originLabelSelect;



    //Combox on MainSearchController
    @FXML
    private ComboBox<Integer> searchRatingCB;

    @FXML
    private ComboBox<String> searchCreatorCB;

    @FXML
    private ComboBox<String> searchSupportCB;

    @FXML
    private ComboBox<String> searchKindCB;

    @FXML
    private ComboBox<String> searchCatCB;

    //TableView on MainSearchController
    @FXML
    private TableView<Artwork> artworkListTableView;

    //ObservableList
    private ObservableList<Artwork> artworkListData = FXCollections.observableArrayList();
    private MainApp mainApp;
    // Reference to the main application.

    private Queries q;

    public ArtworkOverviewController() {
        q = new Queries();
        artworkListData = q.getMediateque();
    }

    private void refreshData() {
        artworkListData = q.getMediateque();
        // Traitement correspondant au filtrage par combobox par unité et par groupe
        artworkListData = FXCollections.observableArrayList(
                artworkListData.stream()
                    .filter(artwork -> {
                        String value = searchCatCB.getValue();
                        return value.equals("Toutes Categories") || artwork.getCategory().equals(value);
                    })
                        .filter(artwork -> {
                            String value = searchKindCB.getValue();
                            return value.equals("Tous Genres") || artwork.getGenre().equals(value);
                        })
                        .filter(artwork -> {
                            String value = searchCreatorCB.getValue();
                            return value.equals("Tous Createurs") || artwork.getCreator().equals(value);
                        })
                        .filter(artwork -> {
                            String value = searchSupportCB.getValue();
                            return value.equals("Tous Supports") || artwork.getSupport().equals(value);
                        })
                        .filter(artwork -> {
                            Integer value = searchRatingCB.getValue();
                            return value == null || artwork.getRating() == value;
                        })

                    .collect(Collectors.toList()));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Artwork> filteredData = new FilteredList<>(artworkListData);

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Artwork> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(artworkListTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        artworkListTableView.setItems(sortedData);
    }


    public void setMainApp(MainApp  mainApp){
        this.mainApp = mainApp;
        // Add observable list data to the table
        artworkListTableView.refresh();
    }



    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
//        commentTextAreaSelect.setDisable(true);
//        commentLabelSelect.setDisable(true);

        // Initialize the person table with the two columns.
        // 0. Initialize the columns.
        idListColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleListColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        ratingListColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
        categoryListColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        supportListColumn.setCellValueFactory(cellData -> cellData.getValue().supportProperty());
        genreListColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        creatorListColumn.setCellValueFactory(cellData -> cellData.getValue().creatorProperty());
        yearsListColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

        //Récupération du choix dans les combo boxes via la méthode refreshData
        searchCatCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });
        searchKindCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });
        searchCreatorCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });
        searchSupportCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });

        searchRatingCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                refreshData();
            }
        });




        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Artwork> filteredData = new FilteredList<>(artworkListData);

        // 2. Set the filter Predicate whenever the filter changes.
        searchTitleField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(artwork -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                return artwork.getTitle().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Artwork> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(artworkListTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        artworkListTableView.setItems(sortedData);




        // Clear artwork details.
        showArtworkDetails(null);

        // Listen for selection changes and show the person details when changed.
        artworkListTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showArtworkDetails(newValue));

        searchCatCB.getItems().add("Toutes Categories");
        searchCatCB.getItems().addAll(filteredData.stream().map(Artwork::getCategory).distinct().collect(Collectors.toList()));
        //searchCatCB.setPromptText("Category");
        searchCatCB.getSelectionModel().select("Toutes Categories");

        searchCreatorCB.getItems().add("Tous Createurs");
        searchCreatorCB.getItems().addAll(filteredData.stream().map(Artwork::getCreator).distinct().collect(Collectors.toList()));
        searchCreatorCB.getSelectionModel().select("Tous Createurs");

        searchSupportCB.getItems().add("Tous Supports");
        searchSupportCB.getItems().addAll(filteredData.stream().map(Artwork::getSupport).distinct().collect(Collectors.toList()));
        searchSupportCB.getSelectionModel().select("Tous Supports");

        searchKindCB.getItems().add("Tous Genres");
        searchKindCB.getItems().addAll(filteredData.stream().map(Artwork::getGenre).distinct().collect(Collectors.toList()));
        searchKindCB.getSelectionModel().select("Tous Genres");

        searchRatingCB.setPromptText("Note");
        searchRatingCB.getItems().addAll(null,0,1,2,3,4,5);
        //searchRatingCB.getSelectionModel().select("Toutes Categories");

    }

    /**
     * Fills all text fields to show details about the artwork.
     * If the specified artwork is null, all text fields are cleared.
     *
     * @param artwork the artwork or null
     */
    private void showArtworkDetails(Artwork  artwork) {
        if (artwork != null) {
            commentTextAreaSelect.setDisable(false);
            commentLabelSelect.setDisable(false);
            // Fill the labels with info from the artwork object.
            idLabelSelect.setText("id : "+Integer.toString(artwork.getId()));
            titleLabelSelect.setText("Titre : "+artwork.getTitle());
            commentTextAreaSelect.setText(artwork.getComment());
            categoryLabelSelect.setText("Categorie : "+artwork.getCategory());
            ratingLabelSelect.setText("Note : "+Integer.toString(artwork.getRating()));
            genreLabelSelect.setText("Genre : "+artwork.getGenre());
            creatorLabelSelect.setText("Createur : "+artwork.getCreator());
            supportLabelSelect.setText("Support : "+artwork.getSupport());
            yearLabelSelect.setText("Année de parution : "+Integer.toString(artwork.getYear()));
            originLabelSelect.setText("Origine : "+artwork.getOrigin());

            // TODO: On est bien cousin

        } else {
            commentTextAreaSelect.setDisable(true);
            commentLabelSelect.setDisable(true);
            // Person is null, remove all the text.
            idLabelSelect.setText("");
            titleLabelSelect.setText("");
            commentTextAreaSelect.setText("");
            categoryLabelSelect.setText("");
            ratingLabelSelect.setText("");
            genreLabelSelect.setText("");
            creatorLabelSelect.setText("");
            supportLabelSelect.setText("");
            yearLabelSelect.setText("");
            originLabelSelect.setText("");

        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
//
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteArtwork() {
        int selectedIndex = artworkListTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            q.deleteArtwork(artworkListTableView.getSelectionModel().getSelectedItem().getId());
            refreshData();

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Aucune oeuvre selectionné");
            alert.setContentText("Veuillez selectionner une oeuvre pour continuer.");

            alert.showAndWait();
        }

    }


    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewArtwork() throws SQLException {
        Artwork tempArtwork = new Artwork();
        boolean okClicked = mainApp.showArtworkEditDialog(tempArtwork, "create");
        if (okClicked) {
            q.createUpdateArtwork("create", tempArtwork );
            artworkListData.add(tempArtwork);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditArtwork() {
        Artwork selectedArtwork = artworkListTableView.getSelectionModel().getSelectedItem();
        if (selectedArtwork != null) {
            boolean okClicked = mainApp.showArtworkEditDialog(selectedArtwork, "update");
            if (okClicked) {
                showArtworkDetails(selectedArtwork);

            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Pas de selection");
            alert.setHeaderText("Aucune oeuvre selectionné");
            alert.setContentText("Veuillez selectionner une oeuvre pour continuer.");
            alert.showAndWait();
        }
    }

}
