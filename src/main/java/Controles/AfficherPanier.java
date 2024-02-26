package Controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import entities.Articles;
import entities.Panier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.myconnection;

import java.awt.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPanier implements Initializable {

    @FXML
    private TableView<Panier> table;

    @FXML
    private TableColumn<Panier, String> colNom;

    @FXML
    private TableColumn<Panier, String> colDescription;

    @FXML
    private TableColumn<Panier, Integer> colPrix;

    @FXML
    private TableColumn<Panier, String> colPhoto;
    @FXML
    private Button supprimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPanier();
    }

    public ObservableList<Panier> getPanier() {
        ObservableList<Panier> panierList = FXCollections.observableArrayList();
        String requete = "SELECT Articles.nom AS article_nom, Articles.description AS article_description, " +
                "Articles.prix AS article_prix, Articles.Image AS article_Image " +
                "FROM Panier " +
                "INNER JOIN Articles ON Panier.id_article = Articles.id"; // Jointure pour récupérer les détails des articles associés à chaque panier
        try {
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Panier panier = new Panier();
                //panier.setId_panier(rs.getInt("id_panier"));

                Articles article = new Articles();
                article.setNom(rs.getString("article_nom"));
                article.setDescription(rs.getString("article_description"));
                article.setPrix(rs.getInt("article_prix"));
                article.setImage(rs.getString("article_Image"));

                panier.setArticles((List<Articles>) article);

                panierList.add(panier);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du panier : " + e.getMessage());
        }
        return panierList;
    }



    public void showPanier() {
        ObservableList<Panier> panier = getPanier();
        table.setItems(panier);
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("Image"));
    }



    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer l'ID du panier sélectionné dans la table
        int panierID = table.getSelectionModel().getSelectedItem().getId_panier();

        // Préparer la requête de suppression
        String query = "DELETE FROM Panier WHERE id_panier = ?";

        try {
            // Préparer la déclaration SQL
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, panierID);

            // Exécuter la requête de suppression
            int deletedRows = ps.executeUpdate();

            // Vérifier si la suppression a réussi
            if (deletedRows > 0) {
                System.out.println("Element du panier supprimé avec succès.");
                showPanier(); // Rafraîchir la table après la suppression
            } else {
                System.out.println("Aucun élément du panier supprimé.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'élément du panier : " + e.getMessage());
        }
    }


}
