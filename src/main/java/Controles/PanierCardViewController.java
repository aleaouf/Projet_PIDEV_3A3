package Controles;

import entities.Articles;
import entities.Panier;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import services.ArticlesServices;
import services.PanierServices;

public class PanierCardViewController {
    @FXML
    private Text contact;

    @FXML
    private Circle imgContainer;

    @FXML
    private Text nomProduit;

    @FXML
    private Text prixArticle;
    private Panier panier;
    private Marketplace marketplace;
    private Articles article;

    public void setData(Panier p, Marketplace marketplace){
        ArticlesServices articlesServices = new ArticlesServices();
        article = articlesServices.getByID(p.getId_article());
        nomProduit.setText(article.getNom());
        prixArticle.setText(article.getPrix()+" TND");
        contact.setText(String.valueOf(article.getContact()));
        String img = article.getImage().replace("[","");
        String imgPath = img.replace("]","");
        imgContainer.setFill(new ImagePattern(new Image("file:/"+imgPath)));
        this.panier = p;
        this.marketplace = marketplace;
    }
    @FXML
    public void deletePanierClicked(MouseEvent event){
        PanierServices servicePanier = new PanierServices();
        servicePanier.deleteEntity(panier);
        ArticlesServices articlesServices = new ArticlesServices();
        articlesServices.updatequantite1(article.getId());
        marketplace.panierBtnClicked(null);
    }
}
