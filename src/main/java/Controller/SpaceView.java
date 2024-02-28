package Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.String;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.MyConnection;

public class SpaceView {
    @FXML
    private ImageView imageView;

    @FXML
    private VBox container;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private ImageView viewMoreIcon;

    @FXML
    private Label CatAf;

    @FXML
    private Label DescAf;

    @FXML
    private Label LocAf;

    @FXML
    private Label NomAf;

    @FXML
    private Label TypeAf;

    private static EspacePartenaire selectedEspace;


    @FXML
    void initialize() {
        imageView.setOnMouseClicked(event -> showAllImages(event));
        // Add event handler for mouse hover
        viewMoreIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewMoreIcon.setRotate(45); // Rotate the icon on hover
            }
        });

        // Add event handler for mouse exit
        viewMoreIcon.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewMoreIcon.setRotate(0); // Reset rotation when mouse exits
            }
        });
    }


    @FXML
    public void showAllImages(MouseEvent mouseEvent) {
        System.out.println(selectedEspace);
        if (selectedEspace != null) {
            // Create a new stage to display all images
            Stage stage = new Stage();

            // Create an HBox to hold the images in a single row
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.setSpacing(10);

            // Iterate through the list of concatenated image URLs
            for (String concatenatedUrls : selectedEspace.getPhotos()) {
                // Split the concatenated image URLs into individual URLs
                String[] imageUrls = concatenatedUrls.split(",\\s*"); // split by comma with optional spaces

                // Iterate through the array of image URLs
                for (String imageUrl : imageUrls) {
                    try {
                        // Trim any leading or trailing whitespaces
                        String trimmedImageUrl = imageUrl.trim();
                        // Create a File object from the image URL
                        File imageFile = new File(trimmedImageUrl);
                        if (!imageFile.exists()) {
                            System.err.println("File does not exist: " + trimmedImageUrl);
                            continue; // Skip this iteration if the file does not exist
                        }
                        // Create an Image object using the file path
                        Image image = new Image(imageFile.toURI().toString());
                        // Create an ImageView with the Image object
                        ImageView imageView = new ImageView(image);
                        // Set the fit width and height to control the size of the image
                        imageView.setFitWidth(400);
                        imageView.setFitHeight(400);
                        // Add the ImageView to the HBox
                        hbox.getChildren().add(imageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Create a scroll pane to scroll through the images if there are too many
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(hbox);
            scrollPane.setFitToWidth(true);

            // Create a scene and set it on the stage
            Scene scene = new Scene(scrollPane);
            stage.setScene(scene);

            // Set the title of the stage
            stage.setTitle("All Images");

            // Show the stage
            stage.show();
        }
    }



    private void displayImage(String imageUrl) {
        try {
            // Split the file paths using the comma as the delimiter
            String[] filePaths = imageUrl.split(",");

            // Get the first file path
            String firstImagePath = filePaths[0].trim();
            System.out.println("File path: " + firstImagePath); // Print file path for debugging

            // Create an Image object using the first file path
            Image image = new Image(new FileInputStream(firstImagePath));

            // Create a new ImageView
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(280); // Set width of ImageView
            imageView.setPreserveRatio(true); // Preserve aspect ratio

            // Create a StackPane to center the ImageView
            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(imageView);
            stackPane.setAlignment(Pos.TOP_CENTER);

            // Display the image
            // Assuming you have a container to hold the StackPane, like a VBox or an HBox
            container.getChildren().add(stackPane); // Replace 'container' with your actual container

        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private EspacePartenaire getSelectedEspace() {
        ObservableList<EspacePartenaire> espaces = getEspace();
        if (!espaces.isEmpty()) {
            return espaces.get(0);
        }
        return null;
    }

    public void AfficherEspace(EspacePartenaire espacePartenaire) {
        // Use the passed espacePartenaire directly instead of calling getSelectedEspace()
        int id_espace = espacePartenaire.getId_espace();

        ObservableList<EspacePartenaire> espaces = getEspace();

        Optional<EspacePartenaire> optionalEspace = espaces.stream()
                .filter(espace -> espace.getId_espace() == id_espace)
                .findFirst();

        if (optionalEspace.isPresent()) {
            selectedEspace = optionalEspace.get(); // Set the selectedEspace
            EspacePartenaire selectedEspace = optionalEspace.get(); // Set the selectedEspace

            NomAf.setText(selectedEspace.getNom());
            LocAf.setText(selectedEspace.getLocalisation());
            DescAf.setText(selectedEspace.getDescription());
            TypeAf.setText(selectedEspace.getType());

            // Get the corresponding Categorie object
            Optional<Categorie> optionalCategorie = getCategorie().stream()
                    .filter(categorie -> categorie.getId_categorie() == selectedEspace.getId_categorie())
                    .findFirst();

            if (optionalCategorie.isPresent()) {
                Categorie categorie = optionalCategorie.get();
                List<String> attributes = new ArrayList<>();
                if (categorie.isEspaceCouvert()) {
                    attributes.add("L'espace est couvert");
                }
                if (categorie.isEspaceEnfants()) {
                    attributes.add("Il y a un espace d'enfants");
                }
                if (categorie.isEspaceFumeur()) {
                    attributes.add("Espace fumeur");
                }
                if (categorie.isServiceRestauration()) {
                    attributes.add("Il y a un service de restauration");
                }
                CatAf.setText(String.join(", ", attributes));
            }

            // Display the first image
            displayImage(selectedEspace.getPhotos().get(0));
            System.out.println(selectedEspace.getPhotos());
            // Set the click event for the first image to show the rest of the images
        }
    }

    public ObservableList<EspacePartenaire> getEspace() {
        ObservableList<EspacePartenaire> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM espacepartenaire";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                EspacePartenaire espacePartenaire = new EspacePartenaire();
                espacePartenaire.setId_categorie(rs.getInt("id_categorie"));

                espacePartenaire.setId_espace(rs.getInt("id_espace"));
                espacePartenaire.setNom(rs.getString("nom"));
                espacePartenaire.setLocalisation(rs.getString("localisation"));
                espacePartenaire.setType(rs.getString("type"));
                espacePartenaire.setDescription(rs.getString("description"));
                espacePartenaire.setPhotos(Collections.singletonList(rs.getString("photos")));

                data.add(espacePartenaire);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ObservableList<Categorie> getCategorie() {
        ObservableList<Categorie> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM Categorie";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId_categorie(rs.getInt("id_categorie"));
                categorie.setEspaceCouvert(rs.getBoolean("espaceCouvert"));
                categorie.setEspaceEnfants(rs.getBoolean("espaceEnfants"));
                categorie.setEspaceFumeur(rs.getBoolean("espaceFumeur"));
                categorie.setServiceRestauration(rs.getBoolean("serviceRestauration"));
                data.add(categorie);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}
