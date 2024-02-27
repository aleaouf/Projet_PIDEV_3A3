<<<<<<< Updated upstream
package Controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.MyConnection;

public class SpaceView {
    @FXML
    private ImageView imageView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    void initialize() {
        AfficherEspace();
        imageView.setOnMouseClicked(event -> showAllImages(event));

    }

    @FXML
    public void showAllImages(MouseEvent mouseEvent) {
        // Get the selected EspacePartenaire object
        EspacePartenaire selectedEspace = getSelectedEspace();
        if (selectedEspace != null && selectedEspace.getId_espace() == 26) {
            // Create a new stage to display all images
            Stage stage = new Stage();

            // Create a VBox to hold the images
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            // Iterate through the list of image URLs and create an ImageView for each image
            for (String imageUrl : selectedEspace.getPhotos()) {
                try {
                    // Split the image URLs if they are separated by commas
                    String[] filePaths = imageUrl.split(",");
                    for (String filePath : filePaths) {
                        // Trim any leading or trailing whitespaces
                        filePath = filePath.trim();
                        // Create a File object from the image URL
                        File imageFile = new File(filePath);
                        if (!imageFile.exists()) {
                            System.err.println("File does not exist: " + filePath);
                            continue; // Skip this iteration if the file does not exist
                        }
                        // Create an Image object using the file path
                        Image image = new Image(imageFile.toURI().toString());
                        // Create an ImageView with the Image object
                        ImageView imageView = new ImageView(image);
                        // Set the fit width and height to control the size of the image
                        imageView.setFitWidth(200);
                        imageView.setFitHeight(200);
                        // Add the ImageView to the VBox
                        vbox.getChildren().add(imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Create a scroll pane to scroll through the images if there are too many
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
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

            // Set the image to the class-level imageView
            imageView.setImage(image);
            imageView.setFitWidth(200); // Set width of ImageView
            imageView.setPreserveRatio(true); // Preserve aspect ratio
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

    public void AfficherEspace() {
        ObservableList<EspacePartenaire> espaces = getEspace();

        // Filter the list to include only the object with id_espace = 25
        Optional<EspacePartenaire> optionalEspace = espaces.stream()
                .filter(espace -> espace.getId_espace() == 26)
                .findFirst();

        // Check if the object with id_espace = 25 exists
        if (optionalEspace.isPresent()) {
            EspacePartenaire selectedEspace = optionalEspace.get();

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
    public ObservableList<EspacePartenaire> getEspaceById(int id) {
        ObservableList<EspacePartenaire> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM espacepartenaire WHERE id_espace = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
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


}
=======
package Controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.MyConnection;

public class SpaceView {
    @FXML
    private ImageView imageView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    void initialize() {
        AfficherEspace();
        imageView.setOnMouseClicked(event -> showAllImages(event));

    }

    @FXML
    public void showAllImages(MouseEvent mouseEvent) {
        // Get the selected EspacePartenaire object
        EspacePartenaire selectedEspace = getSelectedEspace();
        if (selectedEspace != null && selectedEspace.getId_espace() == 26) {
            // Create a new stage to display all images
            Stage stage = new Stage();

            // Create a VBox to hold the images
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(10);

            // Iterate through the list of image URLs and create an ImageView for each image
            for (String imageUrl : selectedEspace.getPhotos()) {
                try {
                    // Split the image URLs if they are separated by commas
                    String[] filePaths = imageUrl.split(",");
                    for (String filePath : filePaths) {
                        // Trim any leading or trailing whitespaces
                        filePath = filePath.trim();
                        // Create a File object from the image URL
                        File imageFile = new File(filePath);
                        if (!imageFile.exists()) {
                            System.err.println("File does not exist: " + filePath);
                            continue; // Skip this iteration if the file does not exist
                        }
                        // Create an Image object using the file path
                        Image image = new Image(imageFile.toURI().toString());
                        // Create an ImageView with the Image object
                        ImageView imageView = new ImageView(image);
                        // Set the fit width and height to control the size of the image
                        imageView.setFitWidth(200);
                        imageView.setFitHeight(200);
                        // Add the ImageView to the VBox
                        vbox.getChildren().add(imageView);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Create a scroll pane to scroll through the images if there are too many
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
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

            // Set the image to the class-level imageView
            imageView.setImage(image);
            imageView.setFitWidth(200); // Set width of ImageView
            imageView.setPreserveRatio(true); // Preserve aspect ratio
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

    public void AfficherEspace() {
        ObservableList<EspacePartenaire> espaces = getEspace();

        // Filter the list to include only the object with id_espace = 25
        Optional<EspacePartenaire> optionalEspace = espaces.stream()
                .filter(espace -> espace.getId_espace() == 26)
                .findFirst();

        // Check if the object with id_espace = 25 exists
        if (optionalEspace.isPresent()) {
            EspacePartenaire selectedEspace = optionalEspace.get();

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
    public ObservableList<EspacePartenaire> getEspaceById(int id) {
        ObservableList<EspacePartenaire> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM espacepartenaire WHERE id_espace = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
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


}
>>>>>>> Stashed changes
