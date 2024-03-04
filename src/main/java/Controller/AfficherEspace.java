package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.EspaceServices;
import utils.MyConnection;
import entities.Categorie;
import entities.EspacePartenaire;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
public class AfficherEspace {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<EspacePartenaire> listViewEspace;

    @FXML
    private Button buttonConsulter;

    @FXML
    private Button buttonAjouter;

    @FXML
    private Button buttonModifier;

    @FXML
    private Button buttonSupprimer;

    @FXML
    void Ajouter(ActionEvent event) {
        StageManager stageManager = StageManager.getInstance();
        stageManager.closeCurrentStage();
        stageManager.switchScene("/Espace.fxml");
        AfficherEspace();
    }

    @FXML
    void Consulter(ActionEvent event) {
        EspacePartenaire espacePartenaire = listViewEspace.getSelectionModel().getSelectedItem();
        if (espacePartenaire == null) {
            System.out.println("Aucun espace sélectionné pour la consultation.");
            return;
        }

        // Pass the selected EspacePartenaire object to the Modifier controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SpaceView.fxml"));

        Parent root;
        try {
            root = loader.load();
            SpaceView spaceView = loader.getController();
            spaceView.AfficherEspace(espacePartenaire);

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
    void Modifier(ActionEvent event) {
        EspacePartenaire espacePartenaire = listViewEspace.getSelectionModel().getSelectedItem();

        try {
            if (espacePartenaire == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Aucun espace sélectionné pour la modification.");
                alert.show();}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }


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

            // Get the selected items from the ListView
        EspacePartenaire espacePartenaire = listViewEspace.getSelectionModel().getSelectedItem();
        try {
            if (espacePartenaire == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Aucune espace sélectionnée.");
            alert.show();}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
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
        try {
            EspaceServices es = new EspaceServices();
            es.deleteEntity(espacePartenaire);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("l'esapce a ete supprime");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }


        AfficherEspace();
        }

    @FXML
    void initialize() {


        listViewEspace.getStyleClass().add("list-cell");

        listViewEspace.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        AfficherEspace(); // This method should populate the ListView with data

    }

    public void AfficherEspace() {
        ObservableList<EspacePartenaire> espaces = getEspace();
        listViewEspace.setItems(espaces);
        listViewEspace.setCellFactory(param -> new ListCell<EspacePartenaire>() {
            @Override
            protected void updateItem(EspacePartenaire item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Display nom and localisation
                    StringBuilder text = new StringBuilder(item.getNom() + " - " + item.getLocalisation());

                    // Retrieve the associated Categorie
                    Optional<Categorie> optionalCategorie = getCategorie().stream()
                            .filter(categorie -> categorie.getId_categorie() == item.getId_categorie())
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
                        // Append Categorie attributes to the text
                        text.append(" - ").append(String.join(", ", attributes));
                    }

                    // Set the text
                    setText(text.toString());
                }
            }
        });
    }




    public ObservableList<EspacePartenaire> getEspace() {
        ObservableList<EspacePartenaire> data = FXCollections.observableArrayList();
        String requete = "SELECT * FROM espacepartenaire where id_user=25";
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
