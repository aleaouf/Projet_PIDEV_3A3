package Controles;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entities.Articles;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import utils.myconnection;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AfficherArticle implements Initializable {
    @FXML
    private TableColumn<Articles, Integer> colContact;

    @FXML
    private TableColumn<Articles, String> colDescription;

    @FXML
    private TableColumn<Articles, String> colID;
    @FXML
    private TableColumn<?, ?> colQuantite;

    @FXML
    private TableColumn<Articles, Integer> colId_panier;

    @FXML
    private TableColumn<Articles, String> colNom;

    @FXML
    private TableColumn<Articles, Integer> colPrix;
    @FXML
    private TextField Descriptionfield;

    @FXML
    private TextField IDfield;

    @FXML
    private TextField Nomfield;

    @FXML
    private TextField Prixfield;

    @FXML
    private TextField contactfield;

    @FXML
    private Button uploadImgBtn;


    @FXML
    private TextField idpanierfield;

    @FXML
    private Button ajouter;

    @FXML
    private Button modifier;

    @FXML
    private Button supprimer;

    @FXML
    private TextField quantitefield;

    @FXML
    private TableView<Articles> table;



    private List<String> imagePaths = new ArrayList<>();

    @FXML
    private void onUploadButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                imagePaths.add(file.getPath());
            }

        }
    }


    public ObservableList<Articles> getArticles() {
        ObservableList<Articles> articlesList = FXCollections.observableArrayList();
        String requete = "SELECT * FROM Articles";
        try {
            Statement st = myconnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Articles article = new Articles();


                article.setImage(rs.getString("image"));
                article.setNom(rs.getString("nom"));
                article.setDescription(rs.getString("description"));
                article.setPrix(rs.getInt("prix"));
                article.setContact(rs.getInt("contact"));
                article.setQuantite(rs.getInt("quantite"));


                articlesList.add(article);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return articlesList;
    }

    public void showArticles() {
        ObservableList<Articles> articles = getArticles();
        table.setItems(articles);
        colID.setCellValueFactory(new PropertyValueFactory<>("image"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("Quantite"));

    }






    @FXML
    void supprimer(ActionEvent event) {
        String query = "DELETE FROM Articles WHERE id = ?";
        try {
            int articleID = table.getSelectionModel().getSelectedItem().getId();
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, articleID);
            int deletedRows = ps.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Article supprimé avec succès.");
                showArticles();
            } else {
                System.out.println("Aucun article supprimé.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        String query = "UPDATE Articles SET nom = ?, description = ?, prix = ?, contact = ?, quantite = ? WHERE id = ?";
        try {
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(query);
            ps.setString(1, Nomfield.getText());
            ps.setString(2, Descriptionfield.getText());
            ps.setString(4, contactfield.getText());
            ps.setString(6, quantitefield.getText());

            String prixText = Prixfield.getText();
            if (!prixText.isEmpty()) {
                ps.setInt(3, Integer.parseInt(prixText));
            }

            ps.setInt(5, table.getSelectionModel().getSelectedItem().getId());

            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Article mis à jour avec succès.");
                showArticles();

            } else {
                System.out.println("Aucun article mis à jour.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showArticles();
    }

    public void getData(javafx.scene.input.MouseEvent mouseEvent) {
        Articles article = table.getSelectionModel().getSelectedItem();
        if (article != null) {
            IDfield.setText(String.valueOf(article.getId()));
            Nomfield.setText(article.getNom());
            Descriptionfield.setText(article.getDescription());
            Prixfield.setText(String.valueOf(article.getPrix()));
            contactfield.setText(String.valueOf(article.getContact()));
            quantitefield.setText(String.valueOf(article.getQuantite()));

        }
    }

    @FXML
    public void ajouter(ActionEvent actionEvent) {


        String query = "INSERT INTO Articles (nom, description, prix, contact,image,quantite) VALUES (?, ?, ?, ?,?,?)";
        try {
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(query);
            ps.setString(1, Nomfield.getText());
            ps.setString(2, Descriptionfield.getText());
            String prixText = Prixfield.getText();
            if (!prixText.isEmpty()) {
                ps.setInt(3, Integer.parseInt(prixText));
            } else {
                // Traitez le cas où le champ de prix est vide selon vos besoins
                // Par exemple, vous pouvez définir une valeur par défaut ou générer une exception
            }
            ps.setInt(4, Integer.parseInt(contactfield.getText()));

            ps.setString(5, this.imagePaths.toString());
            ps.setInt(6, Integer.parseInt(quantitefield.getText()));

            int insertedRows = ps.executeUpdate();
            if (insertedRows > 0) {
                System.out.println("Article ajouté avec succès.");
                showArticles();
                clearFields(); // Efface les champs après l'ajout réussi
            } else {
                System.out.println("Aucun article ajouté.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

        private void clearFields() {
            Nomfield.clear();
            Descriptionfield.clear();
            Prixfield.clear();
            contactfield.clear();
            quantitefield.clear();

        }




    }
