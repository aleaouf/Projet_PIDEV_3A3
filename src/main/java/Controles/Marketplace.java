package Controles;
import entities.Articles;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import services.ArticlesServices;
import utils.myconnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import entities.Panier;
import services.PanierServices;
import javafx.scene.control.Alert;
public class Marketplace implements Initializable {
    @FXML
    private VBox articlesVBox;
    @FXML
    private Text descriptionDetail;
    @FXML
    private Text nomProduit;
    @FXML
    private Circle imgDetailContainer;
    @FXML
    private AnchorPane detailsContainer;
    @FXML
    private AnchorPane panierContainer;
    @FXML
    private VBox panierVBox;
    private List<Articles> articles;
    private Articles currentArticle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panierContainer.setVisible(false);
        detailsContainer.setVisible(false);
        ArticlesServices articlesServices = new ArticlesServices();
        articles = articlesServices.getAllData();
        showArticles(articles);
    }
    public void showArticles(List<Articles> list){
        articlesVBox.getChildren().clear();
        for (Articles a : list){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/articleCardView.fxml"));
                Parent root = fxmlLoader.load();
                ArticleCardViewController controller = fxmlLoader.getController();
              controller.setData(a,this);
                articlesVBox.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void showDetails(Articles a){
        nomProduit.setText(a.getNom());
        descriptionDetail.setText(a.getDescription());
        String img = a.getImage().replace("[","");
        String imagePath = img.replace("]","");
        imgDetailContainer.setFill(new ImagePattern(new Image("file:/"+imagePath)));
        detailsContainer.setVisible(true);
        currentArticle = a;
    }
    @FXML
    public void goBack(MouseEvent event){

        panierContainer.setVisible(false);
        detailsContainer.setVisible(false);
    }
    @FXML
    public void ajoutPanier(MouseEvent event){
        Panier panier = new Panier(1,1,currentArticle.getId());
        PanierServices panierServices = new PanierServices();
        panierServices.addEntity(panier);
        ArticlesServices articlesServices = new ArticlesServices();
        articlesServices.updateQuantite(currentArticle.getId());
    }
    @FXML
    public void recherche(KeyEvent search){
        TextInputControl textInputControl = (TextInputControl) search.getSource();
        String searchInput = textInputControl.getText();
        if (!searchInput.isEmpty()){
            showArticles(articles.stream().filter( a -> a.getNom().contains(searchInput)).collect(Collectors.toList()));
        } else
            showArticles(articles);
    }
    @FXML
    public void panierBtnClicked(MouseEvent event){
        panierVBox.getChildren().clear();
        PanierServices panierServices = new PanierServices();
        List<Panier> paniers = panierServices.getPanierByUser(1);
        for(Panier p : paniers){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/panierCardView.fxml"));
                Parent root = fxmlLoader.load();
                PanierCardViewController controller = fxmlLoader.getController();
                controller.setData(p,this);
                panierVBox.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        panierContainer.setVisible(true);
    }
}
