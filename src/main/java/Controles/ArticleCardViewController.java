package Controles;

import entities.Articles;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ArticleCardViewController {
    @FXML
    private Text contact;
    @FXML
    private Circle imgContainer;
    @FXML
    private Text nomProduit;
    @FXML
    private Text prixArticle;
    private Articles articles;
    private Marketplace marketplace;
    public void setData(Articles articles,Marketplace marketplace){
        String img = articles.getImage().replace("[","");
        String imgPath = img.replace("]","");
        System.out.println(imgPath);
        imgContainer.setFill(new ImagePattern(new Image("file:/"+imgPath))); // pour charger l image dans le cercle
        nomProduit.setText(articles.getNom());
        prixArticle.setText(articles.getPrix()+" TND");
        contact.setText(String.valueOf(articles.getContact()));
        this.articles = articles;
        this.marketplace = marketplace;
    }
    @FXML
    public void detailClicked(MouseEvent event){
        marketplace.showDetails(articles);
    }
}
