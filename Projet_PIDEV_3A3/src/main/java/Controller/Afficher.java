<<<<<<< Updated upstream
package Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import services.EspaceServices;
import utils.MyConnection;


public class Afficher {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<EspacePartenaire, Integer> idEspaceTb;
    @FXML
    private TableColumn<EspacePartenaire, String> categorieTb;
    @FXML
    private TableColumn<EspacePartenaire, String> descriptionTb;

    @FXML
    private TableColumn<EspacePartenaire, String> localisationTb;

    @FXML
    private TableColumn<EspacePartenaire, String> nomTb;

    @FXML
    private TableColumn<EspacePartenaire, String> photoTb;

    @FXML
    private TableColumn<EspacePartenaire, String> typeTb;

    @FXML
    private TableView<EspacePartenaire> tableEspace;
    @FXML
    private TableView<Categorie> tableCategorie;


    @FXML
    void Ajouter(ActionEvent event) {

        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Espace.fxml");
            AfficherEspace();


    }

    @FXML
    void Modifier(ActionEvent event) {
        // Retrieve the selected EspacePartenaire from the table
        EspacePartenaire espacePartenaire = tableEspace.getSelectionModel().getSelectedItem();
        if (espacePartenaire == null) {
            System.out.println("Aucun espace sélectionné pour la modification.");
            return;
        }
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();

        // Pass the selected EspacePartenaire object to the Modifier controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));

        Parent root;
        try {
            root = loader.load();
            Modifier modifierController = loader.getController();
            modifierController.setEspacePartenaireToUpdate(espacePartenaire);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Show the update.fxml form and wait for it to be closed

            // Refresh the Afficher.fxml view after updating
            AfficherEspace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void Supprimer(ActionEvent event) {
        EspacePartenaire espacePartenaire = tableEspace.getSelectionModel().getSelectedItem();

        if (espacePartenaire == null) {

            System.out.println("Aucune espace sélectionnée.");
            return;
        }
        String req = "DELETE FROM Categorie WHERE id_categorie = ?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, espacePartenaire.getId_categorie());
            ps.executeUpdate();
            System.out.println("Catégorie supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        EspaceServices es = new EspaceServices();
        es.deleteEntity(espacePartenaire);

        AfficherEspace();
    }

    @FXML
    void initialize() {

        AfficherEspace();



    }

    public void AfficherEspace() {
        ObservableList<EspacePartenaire> list = getEspace();

        categorieTb.setCellValueFactory(cellData -> {
            // Get the EspacePartenaire object associated with the row
            EspacePartenaire espacePartenaire = cellData.getValue();

            // Retrieve the id_categorie associated with the EspacePartenaire
            int id_categorie = espacePartenaire.getId_categorie();

            // Find the corresponding Categorie object from the fetched data
            Optional<Categorie> optionalCategorie = getCategorie().stream()
                    .filter(categorie -> categorie.getId_categorie() == id_categorie)
                    .findFirst();

            // If the Categorie object is found, construct and return the concatenated string of attributes
            if (optionalCategorie.isPresent()) {
                Categorie categorie = optionalCategorie.get();
                // Construct the concatenated string of attributes
                List<String> attributes = new ArrayList<>();
                if (categorie.isEspaceCouvert()) {
                    attributes.add("l'espace est couvert");
                }
                if (categorie.isEspaceEnfants()) {
                    attributes.add("il y a un espace d'enfants");
                }
                if (categorie.isEspaceFumeur()) {
                    attributes.add("espace fumeur");
                }
                if (categorie.isServiceRestauration()) {
                    attributes.add("il y a un service de restauration");
                }
                return new ReadOnlyStringWrapper(String.join(", ", attributes));
            } else {
                return new ReadOnlyStringWrapper(""); // Return empty string if no matching Categorie found
            }
        });
        tableEspace.setItems(list);
        nomTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("nom"));
        localisationTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("localisation"));
        photoTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("photos"));
        descriptionTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("description"));
        typeTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("type"));




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


=======
package Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.*;

import entities.Categorie;
import entities.EspacePartenaire;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import services.EspaceServices;
import utils.MyConnection;


public class Afficher {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private TableColumn<EspacePartenaire, Integer> idEspaceTb;
    @FXML
    private TableColumn<EspacePartenaire, String> categorieTb;
    @FXML
    private TableColumn<EspacePartenaire, String> descriptionTb;

    @FXML
    private TableColumn<EspacePartenaire, String> localisationTb;

    @FXML
    private TableColumn<EspacePartenaire, String> nomTb;

    @FXML
    private TableColumn<EspacePartenaire, String> photoTb;

    @FXML
    private TableColumn<EspacePartenaire, String> typeTb;

    @FXML
    private TableView<EspacePartenaire> tableEspace;
    @FXML
    private TableView<Categorie> tableCategorie;


    @FXML
    void Ajouter(ActionEvent event) {

        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Espace.fxml");
            AfficherEspace();


    }

    @FXML
    void Modifier(ActionEvent event) {
        // Retrieve the selected EspacePartenaire from the table
        EspacePartenaire espacePartenaire = tableEspace.getSelectionModel().getSelectedItem();
        if (espacePartenaire == null) {
            System.out.println("Aucun espace sélectionné pour la modification.");
            return;
        }
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();

        // Pass the selected EspacePartenaire object to the Modifier controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier.fxml"));

        Parent root;
        try {
            root = loader.load();
            Modifier modifierController = loader.getController();
            modifierController.setEspacePartenaireToUpdate(espacePartenaire);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Show the update.fxml form and wait for it to be closed

            // Refresh the Afficher.fxml view after updating
            AfficherEspace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void Supprimer(ActionEvent event) {
        EspacePartenaire espacePartenaire = tableEspace.getSelectionModel().getSelectedItem();

        if (espacePartenaire == null) {

            System.out.println("Aucune espace sélectionnée.");
            return;
        }
        String req = "DELETE FROM Categorie WHERE id_categorie = ?";
        try {
            PreparedStatement ps = MyConnection.getInstance().getCnx().prepareStatement(req);
            ps.setInt(1, espacePartenaire.getId_categorie());
            ps.executeUpdate();
            System.out.println("Catégorie supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        EspaceServices es = new EspaceServices();
        es.deleteEntity(espacePartenaire);

        AfficherEspace();
    }

    @FXML
    void initialize() {

        AfficherEspace();



    }

    public void AfficherEspace() {
        ObservableList<EspacePartenaire> list = getEspace();

        categorieTb.setCellValueFactory(cellData -> {
            // Get the EspacePartenaire object associated with the row
            EspacePartenaire espacePartenaire = cellData.getValue();

            // Retrieve the id_categorie associated with the EspacePartenaire
            int id_categorie = espacePartenaire.getId_categorie();

            // Find the corresponding Categorie object from the fetched data
            Optional<Categorie> optionalCategorie = getCategorie().stream()
                    .filter(categorie -> categorie.getId_categorie() == id_categorie)
                    .findFirst();

            // If the Categorie object is found, construct and return the concatenated string of attributes
            if (optionalCategorie.isPresent()) {
                Categorie categorie = optionalCategorie.get();
                // Construct the concatenated string of attributes
                List<String> attributes = new ArrayList<>();
                if (categorie.isEspaceCouvert()) {
                    attributes.add("l'espace est couvert");
                }
                if (categorie.isEspaceEnfants()) {
                    attributes.add("il y a un espace d'enfants");
                }
                if (categorie.isEspaceFumeur()) {
                    attributes.add("espace fumeur");
                }
                if (categorie.isServiceRestauration()) {
                    attributes.add("il y a un service de restauration");
                }
                return new ReadOnlyStringWrapper(String.join(", ", attributes));
            } else {
                return new ReadOnlyStringWrapper(""); // Return empty string if no matching Categorie found
            }
        });
        tableEspace.setItems(list);
        nomTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("nom"));
        localisationTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("localisation"));
        photoTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("photos"));
        descriptionTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("description"));
        typeTb.setCellValueFactory(new PropertyValueFactory<EspacePartenaire, String>("type"));




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


>>>>>>> Stashed changes
}