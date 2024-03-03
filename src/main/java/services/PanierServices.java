package services;

import entities.Articles;
import entities.Panier;
import interfaces.Iservices;
import utils.myconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PanierServices implements Iservices<Panier> {

    @Override
    public void addEntity(Panier panier) {
        String requete = "insert INTO Panier (id_user,nbr_article,id_article) Values (?,?,?)";
        try {
            PreparedStatement st= myconnection.getInstance().getCnx().prepareStatement(requete);
            st.setInt(1,panier.getId_usr());
            st.setInt(2,panier.getNbr_articles());
            st.setInt(3,panier.getId_article());
            try {
                st.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEntity(Panier panier) {
        String requete = "UPDATE Panier SET id_panier=?, id_user=?, nbr_article=?,id_article=?  WHERE id_panier=?";
        try {
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1, panier.getId_panier());
            ps.setInt(2, panier.getId_usr());
            ps.setInt(3, panier.getNbr_articles());
            ps.setInt(4, panier.getId_article());
            ps.setInt(5, panier.getId_panier());
             // Supposons que votre classe Articles ait une méthode getId() pour obtenir l'identifiant de l'article à mettre à jour
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEntity(Panier panier) {
        String requete = "DELETE FROM Panier WHERE id_panier=?";
        try {
            PreparedStatement ps = myconnection.getInstance().getCnx().prepareStatement(requete);
            ps.setInt(1, panier.getId_panier()); // Supposons que votre classe Articles ait une méthode getId() pour obtenir l'identifiant de l'article à supprimer
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Panier> getAllData() {
        List<Panier> data = new ArrayList<>();
        String requete = "SELECT * FROM Panier";
        try {
            Statement st = myconnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                int id_panier = rs.getInt("id_panier");
                int id_usr = rs.getInt("id_user");
                int nbr_articles = rs.getInt("nbr_article");
                int id_article = rs.getInt("id_article");

                // Créer un objet Articles à partir des données récupérées
                Panier panier = new Panier(id_panier,id_usr,nbr_articles,id_article);
                // Ajouter l'article à la liste
                data.add(panier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
    public List<Panier> getPanierByUser(int idUser){
        List<Panier> paniers = new ArrayList<>();
        String requete = "SELECT * FROM PANIER WHERE id_user = ?";
        try{
            PreparedStatement stm = myconnection.getInstance().getCnx().prepareStatement(requete);
            stm.setInt(1,idUser);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                Panier panier = new Panier(rs.getInt("id_panier"),rs.getInt("id_user")
                ,rs.getInt("nbr_article"),rs.getInt("id_article"));
                paniers.add(panier);
            }
            stm.close();
            return paniers;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return paniers;
    }


        public int calculerSommePrixArticlesDansPanier(int idUtilisateur) {
            int sommePrix = 0;

            // Récupérer tous les articles dans le panier de l'utilisateur spécifié
            List<Panier> panierUtilisateur = getPanierByUser(idUtilisateur);

            // Parcourir chaque élément du panier
            for (Panier panier : panierUtilisateur) {
                // Récupérer l'article correspondant à l'élément du panier
                Articles article = getArticleById(panier.getId_article());

                // Ajouter le prix de l'article à la somme totale
                sommePrix += article.getPrix();
            }

            return sommePrix;
        }

        // Méthode pour récupérer un article par son ID
        public Articles getArticleById(int idArticle) {
            String requete = "SELECT * FROM Articles WHERE id = ?";
            try {
                PreparedStatement stm = myconnection.getInstance().getCnx().prepareStatement(requete);
                stm.setInt(1, idArticle);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    // Créer un objet Articles à partir des données récupérées
                    return new Articles(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getInt("prix"),
                            rs.getInt("contact"),
                            rs.getString("image"),
                            rs.getInt("quantite")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null; // Retourner null si l'article n'est pas trouvé
        }
    }




